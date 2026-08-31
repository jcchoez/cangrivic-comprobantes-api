package com.facturas.cangrivic.service;

import com.facturas.cangrivic.dto.usuario.UsuarioResponseDTO;
import com.facturas.cangrivic.exception.RolNotFoundException;
import com.facturas.cangrivic.exception.UsuarioNotFoundException;
import com.facturas.cangrivic.persistence.entity.RolEntity;
import com.facturas.cangrivic.persistence.entity.UsuarioEntity;
import com.facturas.cangrivic.persistence.repository.RolUsuarioRepository;
import com.facturas.cangrivic.persistence.repository.UsuarioRepository;
import com.facturas.cangrivic.service.mapper.UsuarioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserSecurityService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final RolUsuarioRepository rolUsuarioRepository;

    @Autowired
    public UserSecurityService(UsuarioRepository usuarioRepository, RolUsuarioRepository rolUsuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolUsuarioRepository = rolUsuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Usamos Optional para obtener el usuario
        /*Optional<UsuarioEntity> usuarioOptional = usuarioRepository.findByUsuarioUsername(username);

        // Si el usuario no está presente, lanzamos una excepción personalizada
        UsuarioEntity usuarioEntity = usuarioOptional.orElseThrow(() -> new UsuarioNotFoundException("Usuario "+ username + " no encontrado"));



        // Obtener el rol asociado al usuario
        RolEntity rolEntity = rolUsuarioRepository.findById(usuarioEntity.getRol().getRolId())
                .orElseThrow(() -> new RolNotFoundException("Rol no encontrado"));*/



        UsuarioEntity usuarioEntity = usuarioRepository.findByUsuarioUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario "+ username + " no encontrado"));

        RolEntity rolEntity = rolUsuarioRepository.findById(usuarioEntity.getRol().getRolId())
                .orElseThrow(() -> new RolNotFoundException("Rol no encontrado"));



        //Asignar el rol al usaurio consultado
        usuarioEntity.setRol(rolEntity);

        System.out.println(usuarioEntity.getUsuarioUsername());
        System.out.println(usuarioEntity.getUsuarioPassword());

        System.out.println(usuarioEntity);

        return User.builder()
                .username(usuarioEntity.getUsuarioUsername())
                .password(usuarioEntity.getUsuarioPassword())
                .roles(usuarioEntity.getRol().getRolNombre())
                .accountLocked(usuarioEntity.getUsuarioLocked()) // si el usuario esta bloqueado true
                .disabled(usuarioEntity.getUsuarioDisabled()) // El usuario está deshabilitado
                .build();

    }
}
