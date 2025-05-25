package com.igor.hospital.presentation.controller;

import com.igor.hospital.application.service.ConsultaService;
import com.igor.hospital.domain.entity.Consulta;
import com.igor.hospital.domain.entity.Role;
import com.igor.hospital.domain.entity.Usuario;
import com.igor.hospital.domain.repository.UsuarioRepository;
import com.igor.hospital.presentation.dto.ConsultaCreateDto;
import com.igor.hospital.presentation.dto.ConsultaUpdateDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ConsultaGraphQL {

    @Autowired
    private ConsultaService consultaService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PreAuthorize("hasRole('ROLE_MEDICO') or hasRole('ROLE_ENFERMEIRO') or hasRole('ROLE_PACIENTE')")
    @QueryMapping
    public List<Consulta> consultasPorPaciente(
            @Argument Integer pacienteId,
            @Argument Boolean somenteFuturas,
            Authentication authentication) {
        String email = (String) authentication.getPrincipal();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        if (usuario.getRole().equals(Role.ROLE_PACIENTE) && !usuario.getIdUsuario().equals(pacienteId)) {
            throw new RuntimeException("Pacientes só podem acessar suas próprias consultas.");
        }
        return consultaService.buscarPorPaciente(pacienteId,somenteFuturas);
    }

    @PreAuthorize("hasRole('ROLE_MEDICO') or hasRole('ROLE_ENFERMEIRO')")
    @MutationMapping
    public Consulta criarConsulta(
            @Argument @Valid ConsultaCreateDto consultaCreateDto
    ) {
        return consultaService.criarConsulta(consultaCreateDto);
    }

    @PreAuthorize("hasRole('ROLE_MEDICO') or hasRole('ROLE_ENFERMEIRO')")
    @MutationMapping
    public Consulta atualizaConsulta(
            @Argument Integer consultaId,
            @Argument @Valid ConsultaUpdateDto consultaUpdateDto
    ) {
        return consultaService.atualizaConsulta(consultaId,consultaUpdateDto);
    }
}
