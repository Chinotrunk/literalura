package com.davidlima.literalura.service;

import com.davidlima.literalura.domain.Autor;
import com.davidlima.literalura.domain.Libro;
import com.davidlima.literalura.dto.DatosLibro;
import com.davidlima.literalura.dto.DatosRespuestaAPI;
import com.davidlima.literalura.repository.AutorRepository;
import com.davidlima.literalura.repository.LibroRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LibroService {

  @Autowired
  private LibroRepository libroRepository;

  @Autowired
  private AutorRepository autorRepository;

  private final ConsumoAPI consumoAPI = new ConsumoAPI();
  private final ConvierteDatos conversor = new ConvierteDatos();
  private static final String URL_BASE = "https://gutendex.com/books/?search=";

  @Transactional
  public Libro buscarYGuardarLibro(String titulo) {
    // Verificar si ya existe en la BD
    Optional<Libro> libroExistente =
            libroRepository.findByTituloIgnoreCase(titulo);

    if (libroExistente.isPresent()) {
      System.out.println("\n⚠️  El libro ya está registrado en la base de datos\n");
      return libroExistente.get();
    }

    // Buscar en la API
    String url = URL_BASE + titulo.replace(" ", "+");
    String json = consumoAPI.obtenerDatos(url);
    DatosRespuestaAPI respuesta = conversor.obtenerDatos(json,
            DatosRespuestaAPI.class);

    if (respuesta.results() == null || respuesta.results().isEmpty()) {
      System.out.println("\n❌ No se encontró ningún libro con ese título\n");
      return null;
    }

    // Tomar el primer resultado
    DatosLibro datosLibro = respuesta.results().get(0);
    Libro libro = new Libro();

    // Verificar si el autor ya existe
    if (libro.getAutor() != null) {
      Optional<Autor> autorExistente =
              autorRepository.findByNombre(libro.getAutor().getNombre());

      if (autorExistente.isPresent()) {
        libro.setAutor(autorExistente.get());
      }
    }

    // Guardar en BD
    Libro libroGuardado = libroRepository.save(libro);
    System.out.println("\n✅ Libro guardado exitosamente\n");

    return libroGuardado;
  }
}
