package com.igor.hospital.presentation.dto;


import com.igor.hospital.domain.entity.StatusConsulta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ConsultaUpdateDto {
    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres")
    private LocalDateTime data;

    @NotBlank(message = "O status é obrigatório")
    private StatusConsulta status;

}
