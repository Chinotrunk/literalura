package com.davidlima.literalura.service;

import com.davidlima.literalura.domain.Autor;
import com.davidlima.literalura.domain.Libro;
import com.davidlima.literalura.dto.DatosAutor;
import com.davidlima.literalura.dto.DatosLibro;
import org.springframework.stereotype.Component;

@Component
public class LibroMapper {

  public Autor toAutor(DatosAutor dto) {
    Autor autor = new Autor();
    autor.setNombre(dto.nombre());
    autor.setFechaDeNacimiento(dto.fechaNacimiento());
    autor.setFechaDeFallecimiento(dto.fechaFallecimiento());
    return autor;
  }

  public Libro toLibro(DatosLibro dto, Autor autor) {
    Libro libro = new Libro();
    libro.setTitulo(dto.titulo());
    libro.setIdioma(
            dto.idiomas() != null && !dto.idiomas().isEmpty()
                    ? dto.idiomas().get(0)
                    : "Desconocido"
    );
    libro.setNumeroDeDescargas(
            dto.numeroDescargas() != null ? dto.numeroDescargas() : 0
    );
    libro.setAutor(autor);
    return libro;
  }
}
