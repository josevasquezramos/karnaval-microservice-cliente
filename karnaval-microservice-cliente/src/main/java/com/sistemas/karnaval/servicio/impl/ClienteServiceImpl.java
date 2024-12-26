package com.sistemas.karnaval.servicio.impl;

import com.sistemas.karnaval.entidad.Cliente;
import com.sistemas.karnaval.repositorio.ClienteRepository;
import com.sistemas.karnaval.servicio.ClienteService;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerUpdateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public Cliente crear(Cliente entidad) {
		try {
			// Crear cliente en Stripe
			CustomerCreateParams params = CustomerCreateParams.builder()
					.setName(entidad.getNombre() + " " + entidad.getApellido()).setEmail(entidad.getEmail())
					.setPhone(entidad.getTelefono()).build();

			Customer stripeCustomer = Customer.create(params);

			// Guardar cliente local con el ID de Stripe
			entidad.setStripeCustomerId(stripeCustomer.getId());
			return clienteRepository.save(entidad);

		} catch (StripeException e) {
			throw new RuntimeException("Error al crear cliente en Stripe: " + e.getMessage());
		}
	}

	@Override
	public Cliente actualizar(Long id, Cliente entidad) {
		Cliente clienteExistente = clienteRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

		try {
			// Actualizar cliente en Stripe
			Customer stripeCustomer = Customer.retrieve(clienteExistente.getStripeCustomerId());
			CustomerUpdateParams params = CustomerUpdateParams.builder()
					.setName(entidad.getNombre() + " " + entidad.getApellido()).setEmail(entidad.getEmail())
					.setPhone(entidad.getTelefono()).build();
			stripeCustomer.update(params);

			// Actualizar cliente local
			clienteExistente.setNombre(entidad.getNombre());
			clienteExistente.setApellido(entidad.getApellido());
			clienteExistente.setEmail(entidad.getEmail());
			clienteExistente.setTelefono(entidad.getTelefono());
			return clienteRepository.save(clienteExistente);

		} catch (StripeException e) {
			throw new RuntimeException("Error al actualizar cliente en Stripe: " + e.getMessage());
		}
	}

	@Override
	public void eliminar(Long id) {
		Cliente cliente = buscarPorId(id);
		try {
			// Eliminar cliente en Stripe
			Customer stripeCustomer = Customer.retrieve(cliente.getStripeCustomerId());
			stripeCustomer.delete();
		} catch (StripeException e) {
			throw new RuntimeException("Error al eliminar cliente en Stripe: " + e.getMessage());
		}
		clienteRepository.deleteById(id);
	}

	@Override
	public Cliente buscarPorId(Long id) {
		return clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
	}

	@Override
	public List<Cliente> listarTodos() {
		return clienteRepository.findAll();
	}
}
