package com.igor.hospital.application.service;

import com.igor.hospital.domain.entity.Usuario;
import com.igor.hospital.presentation.dto.LoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.token.Token;
import org.springframework.security.core.token.TokenService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public Token autenticate(LoginDto loginDTO) {
        try{
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword());
            var auth = authenticationManager.authenticate(authenticationToken);
            Usuario usuario = (Usuario) auth.getPrincipal();
            return tokenService.allocateToken(usuario.getUsername());
        }catch (BadCredentialsException e){
            throw new RuntimeException("Credenciais inválidas.");
        }

    }
}
