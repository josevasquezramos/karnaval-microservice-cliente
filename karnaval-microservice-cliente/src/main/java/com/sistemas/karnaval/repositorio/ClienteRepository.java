package com.sistemas.karnaval.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistemas.karnaval.entidad.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
