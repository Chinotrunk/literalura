package com.davidlima.literalura.repository;

import com.davidlima.literalura.domain.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

  Optional<Autor> findByNombre(String nombre);

  @Query("SELECT a FROM Autor a WHERE a.fechaDeNacimiento <= :ano AND (a.fechaDeFallecimiento IS NULL OR a.fechaDeFallecimiento > :ano)")
  List<Autor> findAutoresVivosEnAno(@Param("ano") Integer ano);

  Boolean existsByNombre(String nombre);
}
