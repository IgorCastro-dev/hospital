package com.igor.hospital.domain.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_MEDICO,
    ROLE_ENFERMEIRO,
    ROLE_PACIENTE;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
