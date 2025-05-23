package com.igor.hospital.domain.repository;

import com.igor.hospital.domain.entity.Consulta;
import com.igor.hospital.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Integer> {
    List<Consulta> findByIdPaciente(Usuario pacienteId);
}
