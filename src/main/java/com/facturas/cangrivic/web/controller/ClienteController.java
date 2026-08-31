package com.facturas.cangrivic.web.controller;

import com.facturas.cangrivic.dto.cliente.ClienteRequestDTO;
import com.facturas.cangrivic.dto.cliente.ClienteResponseDTO;
import com.facturas.cangrivic.dto.usuario.UsuarioRequestDTO;
import com.facturas.cangrivic.dto.usuario.UsuarioResponseDTO;
import com.facturas.cangrivic.exception.ClienteNotFoundException;
import com.facturas.cangrivic.exception.EmpresaNotFoundException;
import com.facturas.cangrivic.exception.RolNotFoundException;
import com.facturas.cangrivic.exception.UsuarioNotFoundException;
import com.facturas.cangrivic.persistence.entity.ClienteEntity;
import com.facturas.cangrivic.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {
    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/enabled")
    public ResponseEntity<Page<ClienteEntity>> getAllEnabled(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "8") int elements,
                                                             @RequestParam(defaultValue = "clienteNombre") String sortBy,
                                                             @RequestParam(defaultValue = "ASC") String sortDirection) {
        return ResponseEntity.ok(this.clienteService.getAllEnabled(page, elements, sortBy, sortDirection));
    }



    /*
    si vale

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<Page<ClienteEntity>> getAllporEmpresaId(
                                                    @PathVariable int empresaId,
                                                    @RequestParam(defaultValue = "0") int page,  // Página por defecto = 0
                                                    @RequestParam(defaultValue = "10") int elements, // Elementos por página = 10
                                                    @RequestParam(defaultValue = "clienteNombre") String sortBy, // Ordenar por nombre por defecto
                                                    @RequestParam(defaultValue = "ASC") String sortDirection // Orden ascendente por defecto
    ) {
        return ResponseEntity.ok(clienteService.getAllporEmpresaId(empresaId, page, elements, sortBy, sortDirection));
    }*/

    // Endpoint para obtener clientes por empresa con paginación
    /*@GetMapping("/empresa/{empresaId}")
    public ResponseEntity<Page<ClienteResponseDTO>> getAllPorEmpresaId(
            @PathVariable int empresaId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int elements,
            @RequestParam(defaultValue = "clienteNombre") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection
    ) {
        Page<ClienteResponseDTO> clientes = clienteService.getAllPorEmpresaId(empresaId, page, elements, sortBy, sortDirection);
        return ResponseEntity.ok(clientes);
    }

*/

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<Map<String, Object>> getAllPorEmpresaId(
            @PathVariable int empresaId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int elements,
            @RequestParam(defaultValue = "clienteNombre") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, elements, sort);
        Page<ClienteResponseDTO> clientes = clienteService.getAllPorEmpresaId(empresaId, pageable);

        // Estructura manual de la respuesta con paginación
        Map<String, Object> response = new HashMap<>();
        response.put("clientes", clientes.getContent());
        response.put("currentPage", clientes.getNumber());
        response.put("totalItems", clientes.getTotalElements());
        response.put("totalPages", clientes.getTotalPages());
        response.put("hasNext", clientes.hasNext());
        response.put("hasPrevious", clientes.hasPrevious());

        return ResponseEntity.ok(response);
    }


/*
    @GetMapping("/{clienteId}")
    public ResponseEntity<ClienteEntity> get(@PathVariable int clienteId) {
        System.out.println("Zona horaria de la JVM: " + java.util.TimeZone.getDefault().getID());
        return ResponseEntity.ok(this.clienteService.get(clienteId));
    }*/

    @GetMapping("/{clienteId}")
    public ResponseEntity<ClienteResponseDTO> obtenerCliente(@PathVariable Integer clienteId) {
        try {
            ClienteResponseDTO clienteResponseDTO = clienteService.obtenerUsuarioPorId(clienteId);
            return new ResponseEntity<>(clienteResponseDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            // Devolver error 404 si no se encuentra el usuario
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }






    // Endpoint para crear un nuevo cliente
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> crearCliente(@RequestBody @Valid ClienteRequestDTO clienteRequestDTO) {
        // Simplemente delegamos al service
        ClienteResponseDTO clienteResponseDTO = clienteService.crearCliente(clienteRequestDTO);

        // Retornar el cliente creado con el código de estado 201 (Created)
        return new ResponseEntity<>(clienteResponseDTO, HttpStatus.CREATED);
    }





    @PutMapping("/{clienteId}")
    public ResponseEntity<ClienteResponseDTO> actualizarCliente(
            @PathVariable Integer clienteId,
            @RequestBody @Valid ClienteRequestDTO clienteRequestDTO) {
        try {
            // Llamar al servicio para actualizar el usuario
            ClienteResponseDTO clienteActualizado = clienteService.actualizarCliente(clienteId, clienteRequestDTO);

            // Retornar el usuario actualizado con código 200 OK
            return ResponseEntity.ok(clienteActualizado);
        } catch (ClienteNotFoundException e) {
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




    @DeleteMapping("/{clienteId}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Integer clienteId) {
        try {
            // Llamar al servicio para eliminar el usuario
            clienteService.deleteCliente(clienteId);

            // Retornar 204 No Content si se eliminó correctamente
            return ResponseEntity.noContent().build();
        } catch (ClienteNotFoundException e) {
            // Manejar caso cuando el usuario no existe
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // Manejar otros errores inesperados
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }




}
