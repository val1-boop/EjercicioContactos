package com.utez.EquipoValeriaLuis.Contactos.repository;

import com.utez.EquipoValeriaLuis.Contactos.model.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Long> {
}
