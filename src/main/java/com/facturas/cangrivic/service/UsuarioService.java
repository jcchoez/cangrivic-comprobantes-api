package com.facturas.cangrivic.service;

import com.facturas.cangrivic.dto.usuario.UsuarioRequestDTO;
import com.facturas.cangrivic.dto.usuario.UsuarioResponseDTO;
import com.facturas.cangrivic.exception.EmpresaNotFoundException;
import com.facturas.cangrivic.exception.RolNotFoundException;
import com.facturas.cangrivic.exception.UsuarioNotFoundException;
import com.facturas.cangrivic.persistence.entity.EmpresaEntity;
import com.facturas.cangrivic.persistence.entity.RolEntity;
import com.facturas.cangrivic.persistence.entity.UsuarioEntity;
import com.facturas.cangrivic.persistence.repository.EmpresaRepository;
import com.facturas.cangrivic.persistence.repository.RolUsuarioRepository;
import com.facturas.cangrivic.persistence.repository.UsuarioRepository;
import com.facturas.cangrivic.service.mapper.UsuarioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final EmpresaRepository empresaRepository;
    private final RolUsuarioRepository rolUsuarioRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, EmpresaRepository empresaRepository, RolUsuarioRepository rolUsuarioRepository,
                          PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.empresaRepository = empresaRepository;
        this.rolUsuarioRepository = rolUsuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioResponseDTO get(Integer usuarioId) {
        UsuarioEntity usuarioEntity = this.usuarioRepository.findById(usuarioId).orElse(null);
        if (usuarioEntity == null) {
            return null;
        }
        return UsuarioMapper.toUsuarioResponseDTO(usuarioEntity);
    }

    // Obtener un usuario por su ID
    public UsuarioResponseDTO obtenerUsuarioPorId(Integer usuarioId) {
        // Usamos Optional para obtener el usuario
        Optional<UsuarioEntity> usuarioOptional = usuarioRepository.findById(usuarioId);

        // Si el usuario no está presente, lanzamos una excepción personalizada
        UsuarioEntity usuarioEntity = usuarioOptional.orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado"));

        // Mapear el UsuarioEntity a UsuarioResponseDTO
        return UsuarioMapper.toUsuarioResponseDTO(usuarioEntity);
    }

    /*public UsuarioEntity save(UsuarioRequestDTO usuarioRequestDTO) {
        return this.usuarioRepository.save(usuarioRequestDTO);
    }*/


    public UsuarioResponseDTO login(String username, String rawPassword) {
        UsuarioEntity usuario = usuarioRepository.findByUsuarioUsername(username)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado"));

        if (!passwordEncoder.matches(rawPassword, usuario.getUsuarioPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return UsuarioMapper.toUsuarioResponseDTO(usuario);
    }



    // Método para crear un nuevo usuario
    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO usuarioRequestDTO) {
        // Buscar la empresa por el ID
        EmpresaEntity empresaEntity = empresaRepository.findById(usuarioRequestDTO.getEmpresaId())
                .orElseThrow(() -> new EmpresaNotFoundException("Empresa no encontrada"));

        // Obtener el rol asociado al usuario
        RolEntity rolEntity = rolUsuarioRepository.findById(usuarioRequestDTO.getRolId())
                .orElseThrow(() -> new RolNotFoundException("Rol no encontrado"));

        String passwordBCrypt = passwordEncoder.encode(usuarioRequestDTO.getUsuarioPassword());



        // Convertir el DTO a la entidad, y asignar la empresa
        UsuarioEntity usuarioEntity = UsuarioMapper.toUsuarioEntity(usuarioRequestDTO);
        usuarioEntity.setEmpresa(empresaEntity);  // Establecer la relación de la empresa al usuario
        usuarioEntity.setRol(rolEntity);  // Asignar el rol al usuario
        usuarioEntity.setUsuarioPassword(passwordBCrypt);
        // Guardar el usuario en la base de datos
        UsuarioEntity savedUsuarioEntity = usuarioRepository.save(usuarioEntity);

        // Mapear la entidad guardada a DTO de respuesta
        return UsuarioMapper.toUsuarioResponseDTO(savedUsuarioEntity);
    }


    public UsuarioResponseDTO actualizarUsuario(Integer usuarioId, UsuarioRequestDTO usuarioRequestDTO) {
        // Buscar el usuario en la base de datos
        UsuarioEntity usuarioEntity = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado"));

        // Si la empresa cambia, buscar la nueva empresa
        if (usuarioRequestDTO.getEmpresaId() != null) {
            EmpresaEntity empresaEntity = empresaRepository.findById(usuarioRequestDTO.getEmpresaId())
                    .orElseThrow(() -> new EmpresaNotFoundException("Empresa no encontrada"));
            usuarioEntity.setEmpresa(empresaEntity);
        }

        // Si el rol cambia, buscar el nuevo rol
        if (usuarioRequestDTO.getRolId() != null) {
            RolEntity rolEntity = rolUsuarioRepository.findById(usuarioRequestDTO.getRolId())
                    .orElseThrow(() -> new RolNotFoundException("Rol no encontrado"));
            usuarioEntity.setRol(rolEntity);
        }

        // Actualizar los datos del usuario si vienen en la petición
        if (usuarioRequestDTO.getUsuarioNombre() != null) {
            usuarioEntity.setUsuarioNombre(usuarioRequestDTO.getUsuarioNombre());
        }
        if (usuarioRequestDTO.getUsuarioEmail() != null) {
            usuarioEntity.setUsuarioEmail(usuarioRequestDTO.getUsuarioEmail());
        }
        if (usuarioRequestDTO.getUsuarioTelefono() != null) {
            usuarioEntity.setUsuarioTelefono(usuarioRequestDTO.getUsuarioTelefono());
        }

        if (usuarioRequestDTO.getUsuarioUsername() != null) {
            usuarioEntity.setUsuarioUsername(usuarioRequestDTO.getUsuarioUsername());
        }
        if (usuarioRequestDTO.getUsuarioPassword() != null) {
            usuarioEntity.setUsuarioPassword(usuarioRequestDTO.getUsuarioPassword());
        }

        if (usuarioRequestDTO.getUsuarioDisabled() != null) {
            usuarioEntity.setUsuarioDisabled(usuarioRequestDTO.getUsuarioDisabled());
        }
        if (usuarioRequestDTO.getUsuarioId() != null) {
            usuarioEntity.setUsuarioLocked(usuarioRequestDTO.getUsuarioLocked());
        }

        // Guardar los cambios en la base de datos
        UsuarioEntity updatedUsuarioEntity = usuarioRepository.save(usuarioEntity);

        // Mapear la entidad actualizada a DTO de respuesta y retornarla
        return UsuarioMapper.toUsuarioResponseDTO(updatedUsuarioEntity);
    }



    public void deleteUsuario(Integer usuarioId) {
        // Buscar el usuario en la base de datos
        UsuarioEntity usuarioEntity = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado"));

        // Eliminar el usuario
        usuarioRepository.delete(usuarioEntity);
    }

    public boolean exists(int usuarioId) {
        return this.usuarioRepository.existsById(usuarioId);
    }




}
