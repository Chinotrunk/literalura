package com.davidlima.literalura.principal;

import com.davidlima.literalura.domain.Autor;
import com.davidlima.literalura.domain.Libro;
import com.davidlima.literalura.repository.AutorRepository;
import com.davidlima.literalura.repository.LibroRepository;
import com.davidlima.literalura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Component
public class Principal {

  private final Scanner teclado = new Scanner(System.in);

  @Autowired
  private LibroRepository libroRepository;

  @Autowired
  private AutorRepository autorRepository;

  @Autowired
  private LibroService libroService;

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
          case 6 -> mostrarEstadisticasPorIdioma();
          case 7 -> top10LibrosMasDescargados();
          case 8 -> mostrarEstadisticasGenerales();
          case 0 -> System.out.println("\n👋 ¡Hasta pronto! Cerrando aplicación...\n");
          default -> System.out.println("\n❌ Opción inválida. Intente nuevamente.\n");
        }
      } catch (NumberFormatException e) {
        System.out.println("\n❌ Por favor, ingrese un número válido.\n");
      } catch (Exception e) {
        System.out.println("\n❌ Error: " + e.getMessage() + "\n");
      }
    }
  }

  private void mostrarMenu() {
    String menu = """
                
                ╔════════════════════════════════════════════════════╗
                ║     📚 LITERALURA - CATÁLOGO DE LIBROS 📚         ║
                ╠════════════════════════════════════════════════════╣
                ║  1 - Buscar libro por título                      ║
                ║  2 - Listar libros registrados                    ║
                ║  3 - Listar autores registrados                   ║
                ║  4 - Listar autores vivos en un determinado año   ║
                ║  5 - Listar libros por idioma                     ║
                ║  6 - Estadísticas de libros por idioma            ║
                ║  7 - Top 10 libros más descargados                ║
                ║  8 - Estadísticas generales                       ║
                ║                                                    ║
                ║  0 - Salir                                         ║
                ╚════════════════════════════════════════════════════╝
                """;
    System.out.println(menu);
    System.out.print("Elija una opción: ");
  }

  private void buscarLibroPorTitulo() {
    System.out.print("\n📖 Ingrese el nombre del libro que desea buscar: ");
    String titulo = teclado.nextLine();

    if (titulo.isBlank()) {
      System.out.println("\n❌ El título no puede estar vacío\n");
      return;
    }

    Libro libro = libroService.buscarYGuardarLibro(titulo);

    if (libro != null) {
      System.out.println(libro);
    }
  }

  private void listarLibrosRegistrados() {
    List<Libro> libros = libroRepository.findAll();

    if (libros.isEmpty()) {
      System.out.println("\n📭 No hay libros registrados en la base de datos\n");
      return;
    }

    System.out.println("\n📚 LIBROS REGISTRADOS (" + libros.size() + ")\n");
    libros.forEach(System.out::println);
  }

  private void listarAutoresRegistrados() {
    List<Autor> autores = autorRepository.findAll();

    if (autores.isEmpty()) {
      System.out.println("\n📭 No hay autores registrados en la base de datos\n");
      return;
    }

    System.out.println("\n✍️  AUTORES REGISTRADOS (" + autores.size() + ")\n");
    autores.forEach(System.out::println);
  }

  private void listarAutoresVivosEnAno() {
    System.out.print("\n📅 Ingrese el año para buscar autores vivos: ");

    try {
      int ano = Integer.parseInt(teclado.nextLine());

      if (ano < 0 || ano > 2025) {
        System.out.println("\n❌ Por favor ingrese un año válido\n");
        return;
      }

      List<Autor> autores = autorRepository.findAutoresVivosEnAno(ano);

      if (autores.isEmpty()) {
        System.out.println("\n📭 No se encontraron autores vivos en el año " + ano + "\n");
        return;
      }

      System.out.println("\n✍️  AUTORES VIVOS EN " + ano + " (" + autores.size() + ")\n");
      autores.forEach(System.out::println);

    } catch (NumberFormatException e) {
      System.out.println("\n❌ Por favor ingrese un año válido\n");
    }
  }

  private void listarLibrosPorIdioma() {
    System.out.println("\n🌍 IDIOMAS DISPONIBLES:\n");
    idiomas.forEach((codigo, nombre) ->
            System.out.println("  " + codigo + " - " + nombre));

    System.out.print("\nIngrese el código del idioma: ");
    String idioma = teclado.nextLine().toLowerCase();

    if (!idiomas.containsKey(idioma)) {
      System.out.println("\n❌ Idioma no válido\n");
      return;
    }

    List<Libro> libros = libroRepository.findByIdioma(idioma);

    if (libros.isEmpty()) {
      System.out.println("\n📭 No hay libros registrados en " +
              idiomas.get(idioma) + "\n");
      return;
    }

    System.out.println("\n📚 LIBROS EN " + idiomas.get(idioma).toUpperCase() +
            " (" + libros.size() + ")\n");
    libros.forEach(System.out::println);
  }

  private void mostrarEstadisticasPorIdioma() {
    System.out.println("\n📊 ESTADÍSTICAS POR IDIOMA\n");

    idiomas.forEach((codigo, nombre) -> {
      Long cantidad = libroRepository.countByIdioma(codigo);
      System.out.printf("  %s (%s): %d libro(s)%n", nombre, codigo, cantidad);
    });

    System.out.println();
  }

  private void top10LibrosMasDescargados() {
    List<Libro> top10 = libroRepository.findTop10ByOrderByNumeroDeDescargasDesc();

    if (top10.isEmpty()) {
      System.out.println("\n📭 No hay libros registrados\n");
      return;
    }

    System.out.println("\n🏆 TOP 10 LIBROS MÁS DESCARGADOS\n");

    for (int i = 0; i < top10.size(); i++) {
      Libro libro = top10.get(i);
      System.out.printf("%d. %s - %s (%,d descargas)%n",
              i + 1,
              libro.getTitulo(),
              libro.getAutor() != null ? libro.getAutor().getNombre() : "Desconocido",
              libro.getNumeroDeDescargas()
      );
    }
    System.out.println();
  }

  private void mostrarEstadisticasGenerales() {
    long totalLibros = libroRepository.count();
    long totalAutores = autorRepository.count();

    if (totalLibros == 0) {
      System.out.println("\n📭 No hay estadísticas disponibles\n");
      return;
    }

    Integer maxDescargas = libroRepository.findMaxDescargas();
    Integer minDescargas = libroRepository.findMinDescargas();
    Double avgDescargas = libroRepository.findAvgDescargas();

    System.out.println("""
                
                ╔════════════════════════════════════════════════════╗
                ║          📊 ESTADÍSTICAS GENERALES 📊              ║
                ╠════════════════════════════════════════════════════╣
                """);

    System.out.printf("║  Total de libros: %-32d ║%n", totalLibros);
    System.out.printf("║  Total de autores: %-31d ║%n", totalAutores);
    System.out.printf("║  Máximo de descargas: %-27d ║%n", maxDescargas);
    System.out.printf("║  Mínimo de descargas: %-27d ║%n", minDescargas);
    System.out.printf("║  Promedio de descargas: %-24.2f ║%n", avgDescargas);

    System.out.println("╚════════════════════════════════════════════════════╝\n");
  }
}
