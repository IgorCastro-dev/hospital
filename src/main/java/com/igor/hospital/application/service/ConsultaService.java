package com.igor.hospital.application.service;

import com.igor.hospital.domain.entity.Consulta;
import com.igor.hospital.domain.entity.Role;
import com.igor.hospital.domain.entity.StatusConsulta;
import com.igor.hospital.domain.entity.Usuario;
import com.igor.hospital.domain.repository.ConsultaRepository;
import com.igor.hospital.domain.repository.UsuarioRepository;
import com.igor.hospital.presentation.dto.ConsultaCreateDto;
import com.igor.hospital.presentation.dto.ConsultaUpdateDto;
import com.igor.hospital.presentation.dto.NotificacaoDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultaService {

    @Value("${spring.rabbitmq.queue.notifica}")
    private String queueNotifica;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RabbitService rabbitService;

    public List<Consulta> buscarPorPaciente(Integer pacienteId, Boolean somenteFuturas) {
        Usuario paciente = usuarioRepository.findByIdUsuario(pacienteId).orElseThrow(
                () -> new RuntimeException("Paciente não encontrado")
        );
        List<Consulta> consultas = consultaRepository.findByIdPaciente(paciente);
        if (Boolean.TRUE.equals(somenteFuturas)) {
            consultas = consultas.stream()
                    .filter(c -> c.getDataHora().isAfter(LocalDateTime.now()))
                    .collect(Collectors.toList());
        }

        return consultas;
    }

    @Transactional
    public Consulta criarConsulta(ConsultaCreateDto consultaCreateDto) {
        Usuario paciente = usuarioRepository.findById(consultaCreateDto.getIdPaciente())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        Usuario medico = usuarioRepository.findById(consultaCreateDto.getIdMedico())
                .orElseThrow(() -> new RuntimeException("Médico não encontrado"));


        if(!medico.getRole().equals(Role.ROLE_MEDICO)){
            throw new RuntimeException("O Usuario não é um medico");
        }

        if(!paciente.getRole().equals(Role.ROLE_PACIENTE)){
            throw new RuntimeException("O Usuario não é um paciente");
        }

        Consulta consulta = new Consulta();
        consulta.setIdPaciente(paciente);
        consulta.setIdMedico(medico);
        consulta.setDataHora(consultaCreateDto.getDataHora());
        consulta.setStatus(StatusConsulta.AGENDADA);

        NotificacaoDto notificacao = new NotificacaoDto(
                consulta.getIdPaciente().getEmail(),
                "Consulta agendada",
                "Você tem uma consulta marcada para " + consulta.getDataHora()
        );

        rabbitService.enviaMensagem(queueNotifica,notificacao);

        return consultaRepository.save(consulta);
    }


    @Transactional
    public Consulta atualizaConsulta(Integer consultaId, ConsultaUpdateDto consultaUpdateDto) {
        Consulta consulta = consultaRepository.findById(consultaId).orElseThrow(
                () -> new RuntimeException("Consulta não encontrada")
        );
        consulta.setDataHora(consultaUpdateDto.getData());
        consulta.setStatus(consultaUpdateDto.getStatus());
        return consultaRepository.save(consulta);
    }
}
