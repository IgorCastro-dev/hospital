package com.igor.hospital.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.token.Token;

@AllArgsConstructor
@NoArgsConstructor
public class TokenDto implements Token {

    private String key;

    private Long keyCreationTime;

    private String usuario;
    private String role;

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public long getKeyCreationTime() {
        return this.keyCreationTime;
    }

    @Override
    public String getExtendedInformation() {
        return this.usuario + ";" + this.role;
    }
}
