package com.davidlima.literalura.service;

import com.davidlima.literalura.domain.Autor;
import com.davidlima.literalura.domain.Libro;
import com.davidlima.literalura.dto.DatosRespuestaAPI;
import com.davidlima.literalura.repository.AutorRepository;
import com.davidlima.literalura.repository.LibroRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LibroService {

  private final LibroRepository libroRepository;
  private final AutorRepository autorRepository;
  private final LibroMapper mapper;
  private final ConsumoAPI consumoAPI;
  private final ConvierteDatos conversor;

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

  private static final String URL_BASE =
          "https://gutendex.com/books/?search=";

  @Transactional
  public Libro buscarYGuardarLibro(String titulo) {

    // 1️⃣ Verificar duplicado
    Optional<Libro> libroExistente = libroRepository.findByTituloIgnoreCase(titulo);

    if (libroExistente.isPresent()) {
      System.out.println("\n⚠️ El libro ya está registrado\n");
      return libroExistente.get();
    }

    // 2️⃣ Consumir API
    String json = consumoAPI.obtenerDatos(URL_BASE + titulo.replace(" ", "+"));
    DatosRespuestaAPI respuesta = conversor.obtenerDatos(json, DatosRespuestaAPI.class);

    if (respuesta.results() == null || respuesta.results().isEmpty()) {
      System.out.println("\n❌ No se encontró el libro\n");
      return null;
    }

    // 3️⃣ Mapear
    Libro libro = mapper.toLibro(respuesta.results().get(0));

    // 4️⃣ Reutilizar autor si existe
    if (libro.getAutor() != null) {
      String nombreAutor = libro.getAutor().getNombre();
      Autor autorExistente = autorRepository.findByNombre(nombreAutor).orElse(null);

      if (autorExistente != null) {
        libro.setAutor(autorExistente);
      }
    }

    // 5️⃣ Guardar
    Libro guardado = libroRepository.save(libro);
    System.out.println("\n✅ Libro guardado exitosamente\n");

    return guardado;
  }
}