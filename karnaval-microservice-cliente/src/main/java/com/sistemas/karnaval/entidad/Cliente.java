package com.sistemas.karnaval.entidad;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "clientes")
@Data
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // ID único del cliente

	@Column(nullable = false, unique = true, length = 50)
	private String stripeCustomerId; // ID del cliente en Stripe

	@Column(nullable = false, length = 100)
	private String nombre; // Nombre del cliente

	@Column(nullable = false, length = 100)
	private String apellido; // Apellido del cliente

	@Column(nullable = false, length = 50, unique = true)
	private String email; // Correo electrónico del cliente

	@Column(nullable = true, length = 15)
	private String telefono; // Teléfono del cliente (opcional)
}
