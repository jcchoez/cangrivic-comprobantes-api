package com.facturas.cangrivic.web.controller;

import com.facturas.cangrivic.dto.cliente.ClienteResponseDTO;
import com.facturas.cangrivic.dto.producto.ProductoRequestDTO;
import com.facturas.cangrivic.dto.producto.ProductoResponseDTO;
import com.facturas.cangrivic.exception.EmpresaNotFoundException;
import com.facturas.cangrivic.exception.ProductoNotFoundException;
import com.facturas.cangrivic.persistence.entity.ClienteEntity;
import com.facturas.cangrivic.persistence.entity.ProductoEntity;
import com.facturas.cangrivic.service.ProductoService;
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
@RequestMapping("/api/producto")
public class ProductoController {
    private final ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }



    @GetMapping("/enabled")
    public ResponseEntity<Page<ProductoEntity>> getAllEnabled(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "8") int elements,
                                                              @RequestParam(defaultValue = "productoNombre") String sortBy,
                                                              @RequestParam(defaultValue = "ASC") String sortDirection) {
        return ResponseEntity.ok(this.productoService.getAllEnabled(page, elements, sortBy, sortDirection));
    }





    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<Map<String, Object>> getAllPorEmpresaId(
            @PathVariable int empresaId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int elements,
            @RequestParam(defaultValue = "productoNombre") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, elements, sort);
        Page<ProductoResponseDTO> productos = productoService.getAllPorEmpresaId(empresaId, pageable);

        // Estructura manual de la respuesta con paginación
        Map<String, Object> response = new HashMap<>();
        response.put("clientes", productos.getContent());
        response.put("currentPage", productos.getNumber());
        response.put("totalItems", productos.getTotalElements());
        response.put("totalPages", productos.getTotalPages());
        response.put("hasNext", productos.hasNext());
        response.put("hasPrevious", productos.hasPrevious());

        return ResponseEntity.ok(response);
    }







    @GetMapping("/{productoId}")
    public ResponseEntity<ProductoResponseDTO> obtnerProductoPorId(@PathVariable Integer productoId) {
        try {
            ProductoResponseDTO productoResponseDTO = productoService.obtenerProductoPorId(productoId);
            return new ResponseEntity<>(productoResponseDTO, HttpStatus.OK);
        }catch (RuntimeException e){
            //Devolver error 404 si no se encuentra el producto
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crearProducto(@RequestBody @Valid ProductoRequestDTO productoRequestDTO) {
        try {
            //llmar al servicio para crear el producto
            ProductoResponseDTO productoResponseDTO = productoService.crearProducto(productoRequestDTO);

            //Retorna el producto creado con el codigo de estado 201 (Create)
            return new ResponseEntity<>(productoResponseDTO,HttpStatus.CREATED);
        }catch (ProductoNotFoundException e) {
            // Manejar el caso cunado el producto no se encuetra
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            // Manjear otro  errores
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{productoId}")
    public ResponseEntity<ProductoResponseDTO> actualizarProducto(@PathVariable Integer productoId,
                                                                  @RequestBody @Valid ProductoRequestDTO productoRequestDTO) {
        try {
            //llmar al servicio par actualiazar el producto
            ProductoResponseDTO productoResponseDTO = productoService.actualizarProducto(productoId, productoRequestDTO);

            //Retorna el producto actualizado con el codido 200 ok
            return ResponseEntity.ok(productoResponseDTO);
        }catch (ProductoNotFoundException e) {

            // Manejar caso cuando el usuario no existe
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }catch (EmpresaNotFoundException e) {
            // Manejar caso cuando la empresa no existe
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            // Manejar otros errores inesperados
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{productoId}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Integer productoId) {
        try {
            // llamar al servicio para eliminar el usuario
            productoService.deleteProducto(productoId);

            // Retornar 204 No Content si se eliminó correctamente
            return ResponseEntity.noContent().build();
        }catch (ProductoNotFoundException e){
            // Manejar caso cuando el producto no existe
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e) {
            // Manejar otros errores inesperados
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
