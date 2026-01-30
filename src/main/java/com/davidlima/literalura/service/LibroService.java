package com.davidlima.literalura.service;

import com.davidlima.literalura.domain.Autor;
import com.davidlima.literalura.domain.Libro;
import com.davidlima.literalura.dto.DatosAutor;
import com.davidlima.literalura.dto.DatosLibro;
import com.davidlima.literalura.dto.DatosRespuestaAPI;
import com.davidlima.literalura.repository.AutorRepository;
import com.davidlima.literalura.repository.LibroRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LibroService {

  private LibroRepository libroRepository;

  private AutorRepository autorRepository;

  private LibroMapper mapper;

  private ConsumoAPI consumoAPI;

  private ConvierteDatos conversor;

  public LibroService(
          LibroRepository libroRepository,
          AutorRepository autorRepository,
          LibroMapper mapper,
          ConsumoAPI consumoAPI,
          ConvierteDatos conversor
  ) {
    this.libroRepository = libroRepository;
    this.autorRepository = autorRepository;
    this.mapper = mapper;
    this.consumoAPI = consumoAPI;
    this.conversor = conversor;
  }

  private static final String URL_BASE = "https://gutendex.com/books/?search=";

  @Transactional
  public Libro buscarYGuardarLibro(String titulo) {

    Optional<Libro> libroExistente =
            libroRepository.findByTituloIgnoreCase(titulo);

    if (libroExistente.isPresent()) {
      System.out.println("\n⚠️  El libro ya está registrado en la base de datos\n");
      return libroExistente.get();
    }

    String url = URL_BASE + titulo.replace(" ", "+");
    String json = consumoAPI.obtenerDatos(url);
    DatosRespuestaAPI respuesta =
            conversor.obtenerDatos(json, DatosRespuestaAPI.class);

    if (respuesta.results() == null || respuesta.results().isEmpty()) {
      System.out.println("\n❌ No se encontró ningún libro con ese título\n");
      return null;
    }

    DatosLibro datosLibro = respuesta.results().get(0);

    // Autor
    DatosAutor datosAutor = datosLibro.autores().get(0);
    Autor autor = mapper.toAutor(datosAutor);

    Optional<Autor> autorExistente =
            autorRepository.findByNombre(autor.getNombre());

    if (autorExistente.isPresent()) {
      autor = autorExistente.get();
    }

    // Libro
    Libro libro = mapper.toLibro(datosLibro, autor);

    Libro libroGuardado = libroRepository.save(libro);
    System.out.println("\n✅ Libro guardado exitosamente\n");

    return libroGuardado;
  }
}
