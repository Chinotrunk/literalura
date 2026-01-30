package com.davidlima.literalura.repository;

import com.davidlima.literalura.domain.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {

  @Query("SELECT l FROM Libro l WHERE LOWER(l.titulo) = LOWER(:titulo)")
  Optional<Libro> findByTituloIgnoreCase(String titulo);

  boolean existsByTituloIgnoreCase(String titulo);

  List<Libro> findByIdioma(String idioma);

  Long countByIdioma(String idioma);

  List<Libro> findTop10ByOrderByNumeroDeDescargasDesc();

  @Query("SELECT MAX(l.numeroDeDescargas) FROM Libro l")
  Integer findMaxDescargas();

  @Query("SELECT MIN(l.numeroDeDescargas) FROM Libro l")
  Integer findMinDescargas();

  @Query("SELECT AVG(l.numeroDeDescargas) FROM Libro l")
  Double findAvgDescargas();
}
