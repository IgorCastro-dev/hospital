package com.igor.hospital.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;


@Data
@AllArgsConstructor
public class NotificacaoDto implements Serializable {
    private String email;
    private String assunto;
    private String mensagem;

}