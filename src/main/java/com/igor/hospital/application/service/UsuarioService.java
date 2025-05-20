package com.igor.hospital.application.service;

import com.igor.hospital.domain.entity.Usuario;
import com.igor.hospital.domain.repository.UsuarioRepository;
import com.igor.hospital.infraestructure.mapper.UsuarioMapper;
import com.igor.hospital.presentation.dto.UsuarioDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email).orElseThrow(
                ()-> new RuntimeException("Email não encontrado")
        );
    }

    @Transactional
    public String salvarUsuario(UsuarioDto usuarioDto) {
        Usuario usuario = usuarioMapper.dtoToEntity(usuarioDto);
        String usuarioDtoEmail = usuarioDto.getEmail();
        boolean usuarioJaExiste = usuarioRepository
                .findByEmail(usuarioDtoEmail)
                .isPresent();
        if(usuarioJaExiste){
            throw new RuntimeException("Usuario ja existe");
        }
        usuario.setSenha(passwordEncoder.encode(usuarioDto.getSenha()));
        usuarioRepository.save(usuario);
        return "Usuario salvo";
    }
}
