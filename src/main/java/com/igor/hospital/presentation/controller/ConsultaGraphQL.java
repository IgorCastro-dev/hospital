package com.igor.hospital.presentation.controller;

import com.igor.hospital.application.service.ConsultaService;
import com.igor.hospital.domain.entity.Consulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ConsultaGraphQL {

    @Autowired
    private ConsultaService consultaService;

    @QueryMapping
    public List<Consulta> consultasPorPaciente(@Argument Integer pacienteId) {
        return consultaService.buscarPorPaciente(pacienteId);
    }
}
