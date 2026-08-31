package com.facturas.cangrivic.web.controller;

import com.facturas.cangrivic.dto.usuario.UsuarioLoginDTO;
import com.facturas.cangrivic.dto.usuario.UsuarioRequestDTO;
import com.facturas.cangrivic.dto.usuario.UsuarioResponseDTO;
import com.facturas.cangrivic.exception.EmpresaNotFoundException;
import com.facturas.cangrivic.exception.RolNotFoundException;
import com.facturas.cangrivic.exception.UsuarioNotFoundException;
import com.facturas.cangrivic.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService /*UsuarioClienteService usuarioClienteService*/) {
        this.usuarioService = usuarioService;
       // this.usuarioClienteService = usuarioClienteService;

    }




    // ✅ NUEVO ENDPOINT DE LOGIN
    @PostMapping("/login")
    public ResponseEntity<UsuarioResponseDTO> login(@Valid @RequestBody UsuarioLoginDTO loginDTO) {
        try {
            UsuarioResponseDTO usuario = usuarioService.login(loginDTO.getUsuarioUsername(), loginDTO.getUsuarioPassword());
            return ResponseEntity.ok(usuario);
        } catch (UsuarioNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


/*
    @GetMapping("/{usuarioId}/clientes")
    public ResponseEntity<Page<ClienteEntity>> obtenerClientesPorUsuarioId(
            @PathVariable Integer usuarioId,
            Pageable pageable) {
        Page<ClienteEntity> clientes = usuarioClienteService.obtenerClientesPorUsuarioId(usuarioId, pageable);
        return ResponseEntity.ok(clientes);
    }*/


/*
    @GetMapping("/{usuarioId}/clientes")
    public Page<ClienteEntity> getClientesByUsuarioId(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "8") int elements,
                                                      @RequestParam(defaultValue = "usuario_id") String sortBy,
                                                      @RequestParam(defaultValue = "ASC") String sortDirection,
                                                      @PathVariable Integer usuarioId) {
        System.out.println("locura");
        return usuarioClienteService.getClientesByUsuarioId(usuarioId, page, elements, sortBy, sortDirection);
    }*/



    @GetMapping("/{usuarioId}")
    public ResponseEntity<UsuarioResponseDTO> obtenerUsuario(@PathVariable Integer usuarioId) {
        try {
            UsuarioResponseDTO usuarioResponseDTO = usuarioService.obtenerUsuarioPorId(usuarioId);
            return new ResponseEntity<>(usuarioResponseDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            // Devolver error 404 si no se encuentra el usuario
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint para crear un nuevo usuario
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crearUsuario(@RequestBody @Valid UsuarioRequestDTO usuarioRequestDTO) {
        try {
            // Llamar al servicio para crear el usuario
            UsuarioResponseDTO usuarioResponseDTO = usuarioService.crearUsuario(usuarioRequestDTO);

            // Retornar el usuario creado con el código de estado 201 (Created)
            return new ResponseEntity<>(usuarioResponseDTO, HttpStatus.CREATED);
        } catch (EmpresaNotFoundException e) {
            // Manejar el caso cuando la empresa no se encuentra
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Manejar otros errores
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @PutMapping("/{usuarioId}")
    public ResponseEntity<UsuarioResponseDTO> actualizarUsuario(
            @PathVariable Integer usuarioId,
            @RequestBody @Valid UsuarioRequestDTO usuarioRequestDTO) {
        try {
            // Llamar al servicio para actualizar el usuario
            UsuarioResponseDTO usuarioActualizado = usuarioService.actualizarUsuario(usuarioId, usuarioRequestDTO);

            // Retornar el usuario actualizado con código 200 OK
            return ResponseEntity.ok(usuarioActualizado);
        } catch (UsuarioNotFoundException e) {
            // Manejar caso cuando el usuario no existe
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (EmpresaNotFoundException e) {
            // Manejar caso cuando la empresa no existe
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (RolNotFoundException e) {
            // Manejar caso cuando el rol no existe
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            // Manejar otros errores inesperados
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Integer usuarioId) {
        try {
            // Llamar al servicio para eliminar el usuario
            usuarioService.deleteUsuario(usuarioId);

            // Retornar 204 No Content si se eliminó correctamente
            return ResponseEntity.noContent().build();
        } catch (UsuarioNotFoundException e) {
            // Manejar caso cuando el usuario no existe
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // Manejar otros errores inesperados
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
