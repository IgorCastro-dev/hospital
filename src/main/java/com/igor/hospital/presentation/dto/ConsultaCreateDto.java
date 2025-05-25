package com.igor.hospital.presentation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


import java.time.LocalDateTime;

@Data
public class ConsultaCreateDto {
    @NotNull(message = "O id do paciente é obrigatório")
    private Integer idPaciente;

    @NotNull(message = "O id do medico é obrigatório")
    private Integer idMedico;

    @NotNull(message = "A data e hora é obrigatório")
    private LocalDateTime dataHora;
}
