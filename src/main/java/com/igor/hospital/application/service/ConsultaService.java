package com.igor.hospital.application.service;

import com.igor.hospital.domain.entity.Consulta;
import com.igor.hospital.domain.entity.Role;
import com.igor.hospital.domain.entity.StatusConsulta;
import com.igor.hospital.domain.entity.Usuario;
import com.igor.hospital.domain.repository.ConsultaRepository;
import com.igor.hospital.domain.repository.UsuarioRepository;
import com.igor.hospital.presentation.dto.ConsultaUpdateDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Consulta> buscarPorPaciente(Integer pacienteId) {
        Usuario paciente = usuarioRepository.findByIdUsuario(pacienteId).orElseThrow(
                () -> new RuntimeException("Paciente não encontrado")
        );
        return consultaRepository.findByIdPaciente(paciente);
    }

    @Transactional
    public Consulta criarConsulta(Integer pacienteId, Integer medicoId, String dataHoraStr) {
        Usuario paciente = usuarioRepository.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        Usuario medico = usuarioRepository.findById(medicoId)
                .orElseThrow(() -> new RuntimeException("Médico não encontrado"));


        if(medico.getRole() != Role.ROLE_MEDICO){
            throw new RuntimeException("O Usuario não é um medico");
        }

        if(paciente.getRole() != Role.ROLE_PACIENTE){
            throw new RuntimeException("O Usuario não é um paciente");
        }

        Consulta consulta = new Consulta();
        consulta.setIdPaciente(paciente);
        consulta.setIdMedico(medico);
        consulta.setDataHora(LocalDateTime.parse(dataHoraStr));
        consulta.setStatus(StatusConsulta.AGENDADA);

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
