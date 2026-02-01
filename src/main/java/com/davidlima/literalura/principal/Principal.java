package com.davidlima.literalura.principal;

import com.davidlima.literalura.domain.Autor;
import com.davidlima.literalura.domain.Libro;
import com.davidlima.literalura.repository.AutorRepository;
import com.davidlima.literalura.repository.LibroRepository;
import com.davidlima.literalura.service.LibroService;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Component
public class Principal {

  private final Scanner teclado = new Scanner(System.in);

  private final LibroRepository libroRepository;
  private final AutorRepository autorRepository;
  private final LibroService libroService;

  public Principal(
          LibroRepository libroRepository,
          AutorRepository autorRepository,
          LibroService libroService
  ) {
    this.libroRepository = libroRepository;
    this.autorRepository = autorRepository;
    this.libroService = libroService;
  }

  private final Map<String, String> idiomas = Map.of(
          "es", "Español",
          "en", "Inglés",
          "fr", "Francés",
          "pt", "Portugués"
  );

  public void muestraElMenu() {
    int opcion = -1;

    while (opcion != 0) {
      mostrarMenu();

      try {
        opcion = Integer.parseInt(teclado.nextLine());

        switch (opcion) {
          case 1 -> buscarLibroPorTitulo();
          case 2 -> listarLibrosRegistrados();
          case 3 -> listarAutoresRegistrados();
          case 4 -> listarAutoresVivosEnAno();
          case 5 -> listarLibrosPorIdioma();
          case 0 -> System.out.println("\n👋 ¡Hasta pronto!\n");
          default -> System.out.println("\n❌ Opción inválida\n");
        }

      } catch (NumberFormatException e) {
        System.out.println("\n❌ Ingrese un número válido\n");
      }
    }
  }

  private void mostrarMenu() {
    System.out.println("""
                
                ╔════════════════════════════════════╗
                ║ 📚 LITERALURA - MENÚ PRINCIPAL 📚 ║
                ╠════════════════════════════════════╣
                ║ 1 - Buscar libro por título        ║
                ║ 2 - Listar libros registrados      ║
                ║ 3 - Listar autores registrados     ║
                ║ 4 - Autores vivos en un año         ║
                ║ 5 - Listar libros por idioma        ║
                ║ 0 - Salir                           ║
                ╚════════════════════════════════════╝
                """);
    System.out.print("Elija una opción: ");
  }

  /* =======================
     MÉTODOS DE IMPRESIÓN
     ======================= */

  private void mostrarLibro(Libro libro) {
    System.out.println("""
            📖 ----- LIBRO -----
            Título: %s
            Autor: %s
            Idioma: %s
            Descargas: %d
            -------------------
            """.formatted(
            libro.getTitulo(),
            libro.getAutor() != null ? libro.getAutor().getNombre() : "Desconocido",
            libro.getIdioma(),
            libro.getNumeroDeDescargas()
    ));
  }

  private void mostrarAutor(Autor autor) {

    String libros = "";
    if (autor.getLibros() != null && !autor.getLibros().isEmpty()) {
      libros = autor.getLibros().stream()
              .map(Libro::getTitulo)
              .reduce((a, b) -> a + ", " + b)
              .orElse("(Sin libros registrados)");
    } else {
      libros = "(Sin libros registrados)";
    }
    System.out.println("""
            ✍ AUTOR
            Nombre: %s
            Fecha de nacimiento: %s
            Fecha de fallecimiento: %s
            Libros: %s
            """.formatted(
            autor.getNombre(),
            autor.getFechaDeNacimiento() != null ? autor.getFechaDeNacimiento() : "Desconocido",
            autor.getFechaDeFallecimiento() != null ? autor.getFechaDeFallecimiento() : "Vivo",
            libros
    ));

    System.out.println("----------------------------");
  }

  /* =======================
     OPCIONES DEL MENÚ
     ======================= */

  private void buscarLibroPorTitulo() {
    System.out.print("\n📖 Ingrese el título del libro: ");
    String titulo = teclado.nextLine();

    if (titulo.isBlank()) {
      System.out.println("\n❌ El título no puede estar vacío\n");
      return;
    }

    Libro libro = libroService.buscarYGuardarLibro(titulo);

    if (libro != null) {
      mostrarLibro(libro);
    }
  }

  private void listarLibrosRegistrados() {
    List<Libro> libros = libroRepository.findAll();

    if (libros.isEmpty()) {
      System.out.println("\n📭 No hay libros registrados\n");
      return;
    }

    System.out.println("\n📚 LIBROS REGISTRADOS\n");
    libros.forEach(this::mostrarLibro);
  }

  private void listarAutoresRegistrados() {
    List<Autor> autores = autorRepository.findAll();

    if (autores.isEmpty()) {
      System.out.println("\n📭 No hay autores registrados\n");
      return;
    }

    System.out.println("\n✍ AUTORES REGISTRADOS\n");
    autores.forEach(this::mostrarAutor);
  }

  private void listarAutoresVivosEnAno() {
    System.out.print("\n📅 Ingrese el año: ");

    try {
      int ano = Integer.parseInt(teclado.nextLine());
      List<Autor> autores = autorRepository.findAutoresVivosEnAno(ano);

      if (autores.isEmpty()) {
        System.out.println("\n📭 No hay autores vivos en ese año\n");
        return;
      }

      System.out.println("\n✍ AUTORES VIVOS EN " + ano + "\n");
      autores.forEach(this::mostrarAutor);

    } catch (NumberFormatException e) {
      System.out.println("\n❌ Año inválido\n");
    }
  }

  private void listarLibrosPorIdioma() {
    System.out.println("\n🌍 IDIOMAS DISPONIBLES:");
    idiomas.forEach((k, v) -> System.out.println(k + " - " + v));

    System.out.print("\nIngrese el código del idioma: ");
    String idioma = teclado.nextLine().toLowerCase();

    if (!idiomas.containsKey(idioma)) {
      System.out.println("\n❌ Idioma no válido\n");
      return;
    }

    List<Libro> libros = libroRepository.findByIdioma(idioma);

    if (libros.isEmpty()) {
      System.out.println("\n📭 No hay libros en ese idioma\n");
      return;
    }

    System.out.println("\n📚 LIBROS EN " + idiomas.get(idioma).toUpperCase() + "\n");
    libros.forEach(this::mostrarLibro);
  }
}
