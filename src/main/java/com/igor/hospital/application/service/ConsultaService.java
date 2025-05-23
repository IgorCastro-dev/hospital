package com.igor.hospital.application.service;

import com.igor.hospital.domain.entity.Consulta;
import com.igor.hospital.domain.entity.Usuario;
import com.igor.hospital.domain.repository.ConsultaRepository;
import com.igor.hospital.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Consulta> buscarPorPaciente(Integer pacienteId) {
        Usuario paciente = usuarioRepository.findByIdUsuario(pacienteId).get();
        return consultaRepository.findByIdPaciente(paciente);
    }
}
