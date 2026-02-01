package com.davidlima.literalura.service;

import com.davidlima.literalura.domain.Autor;
import com.davidlima.literalura.domain.Libro;
import com.davidlima.literalura.dto.DatosAutor;
import com.davidlima.literalura.dto.DatosLibro;
import org.springframework.stereotype.Component;

@Component
public class LibroMapper {

  public Libro toLibro(DatosLibro datosLibro) {
    Libro libro = new Libro();
    libro.setTitulo(datosLibro.titulo());
    libro.setNumeroDeDescargas(datosLibro.numeroDeDescargas() != null ? datosLibro.numeroDeDescargas() : 0);

    // Idioma
    libro.setIdioma(datosLibro.idiomas() != null && !datosLibro.idiomas().isEmpty()
            ? datosLibro.idiomas().get(0)
            : "desconocido");

    // Autor (solo el primero)
    if (datosLibro.autores() != null && !datosLibro.autores().isEmpty()) {
      DatosAutor datosAutor = datosLibro.autores().get(0);

      Autor autor = new Autor();
      autor.setNombre(datosAutor.nombre());
      autor.setFechaDeNacimiento(datosAutor.fechaNacimiento());
      autor.setFechaDeFallecimiento(datosAutor.fechaFallecimiento());

      libro.setAutor(autor);
    }

    return libro;
  }
}