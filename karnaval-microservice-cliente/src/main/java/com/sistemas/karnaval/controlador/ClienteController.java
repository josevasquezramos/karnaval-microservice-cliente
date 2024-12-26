package com.sistemas.karnaval.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sistemas.karnaval.entidad.Cliente;
import com.sistemas.karnaval.servicio.ClienteService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	/**
	 * Endpoint para listar todos los clientes.
	 * 
	 * @return Lista de clientes.
	 */
	@GetMapping
	public ResponseEntity<List<Cliente>> listarClientes() {
		return ResponseEntity.ok(clienteService.listarTodos());
	}

	/**
	 * Endpoint para obtener un cliente por su ID.
	 * 
	 * @param id ID del cliente.
	 * @return Detalles del cliente.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> obtenerClientePorId(@PathVariable Long id) {
		return ResponseEntity.ok(clienteService.buscarPorId(id));
	}

	/**
	 * Endpoint para crear un nuevo cliente.
	 * 
	 * @param cliente Datos del cliente.
	 * @return Cliente creado.
	 */
	@PostMapping
	public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente) {
		return ResponseEntity.ok(clienteService.crear(cliente));
	}

	/**
	 * Endpoint para actualizar un cliente existente.
	 * 
	 * @param id      ID del cliente a actualizar.
	 * @param cliente Datos del cliente a actualizar.
	 * @return Cliente actualizado.
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Cliente> actualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
		return ResponseEntity.ok(clienteService.actualizar(id, cliente));
	}

	/**
	 * Endpoint para eliminar un cliente.
	 * 
	 * @param id ID del cliente a eliminar.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
		clienteService.eliminar(id);
		return ResponseEntity.noContent().build();
	}
}