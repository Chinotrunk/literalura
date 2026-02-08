# 📚 Literalura - Catálogo de Libros y Autores

Una aplicación de línea de comandos (CLI) desarrollada en Java con Spring Boot que permite buscar, almacenar y consultar información sobre libros y autores utilizando la API pública de Gutendex (Project Gutenberg).

## ✨ Características

- 🔍 **Búsqueda de libros por título**: Consulta en tiempo real a la API de Gutendex.
- 💾 **Persistencia de datos**: Almacena libros y autores en una base de datos PostgreSQL.
- 📖 **Gestión de autores y libros**: Relación uno a muchos entre autores y libros.
- 🌍 **Filtrado por idioma**: Lista libros en diferentes idiomas (español, inglés, francés, portugués).
- 📅 **Autores vivos por año**: Encuentra autores que estaban vivos en un año específico.
- 📊 **Estadísticas**: Obtén métricas sobre los libros almacenados.

## 🛠️ Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 4.0.1**
- **Spring Data JPA**
- **PostgreSQL**
- **Jackson 2.16** (para procesamiento JSON)
- **Maven** (gestión de dependencias)

## 📋 Prerrequisitos

- Java 17 o superior
- Maven 3.8 o superior
- PostgreSQL 12 o superior
- Conexión a Internet (para consumir la API de Gutendex)

## 🚀 Instalación

### 1. Clonar el repositorio

```bash
git clone https://github.com/tu-usuario/literalura.git
cd literalura
```

### 📖 Ejemplos de Uso
<img width="441" height="315" alt="image" src="https://github.com/user-attachments/assets/7ecd8a6b-2aee-4953-8b01-3867856c5431" />

## 1. Buscar un Libro
<img width="500" height="690" alt="image" src="https://github.com/user-attachments/assets/cf2a0cca-15c3-4965-959e-eb94b0390d41" />

<img width="397" height="260" alt="image" src="https://github.com/user-attachments/assets/444e4577-15f6-48f7-b206-1707603c7cb5" />

## 2. Listar Libros Registrados
<img width="436" height="305" alt="image" src="https://github.com/user-attachments/assets/2f220576-62c4-4cd0-ac41-c2d59327a07c" />

<img width="590" height="783" alt="image" src="https://github.com/user-attachments/assets/7f7e347e-78ed-4ebb-9672-06040cb4adb4" />

## 3. Listar autores registrados
<img width="442" height="307" alt="image" src="https://github.com/user-attachments/assets/ee34a313-7a49-40e7-89af-b77a03758c8e" />

<img width="614" height="759" alt="image" src="https://github.com/user-attachments/assets/fdfb9a95-029d-4fbd-b176-6824ebb22a5c" />

## 4. Autores vivos en un año
<img width="438" height="344" alt="image" src="https://github.com/user-attachments/assets/63db57ad-b456-4f58-bce6-c98820f8851b" />

<img width="618" height="212" alt="image" src="https://github.com/user-attachments/assets/bafb9e6a-e7e0-4155-b461-fbeff392aa16" />

## 5. Listar libros por idioma
<img width="446" height="504" alt="image" src="https://github.com/user-attachments/assets/557f9144-e7cf-456d-a65f-03587df6aab1" />

<img width="576" height="722" alt="image" src="https://github.com/user-attachments/assets/027c82c6-69a8-47d1-ac13-7478e61a4665" />

## Opciones del menú

- Buscar libro por título: Permite buscar un libro por su título. Si se encuentra, se muestra la información y se guarda en la base de datos.

- Listar libros registrados: Muestra todos los libros que han sido guardados en la base de datos.

- Listar autores registrados: Muestra todos los autores guardados en la base de datos junto con sus libros.

- Autores vivos en un año: Solicita un año y lista los autores que estaban vivos en ese año.

- Listar libros por idioma: Muestra los libros filtrados por el idioma seleccionado (español, inglés, francés o portugués).

### 📁 Estructura del Proyecto
```
src/main/java/com/davidlima/literalura/
├── domain/                     # Entidades JPA
│   ├── Autor.java
│   └── Libro.java
├── repository/                 # Repositorios de Spring Data JPA
│   ├── AutorRepository.java
│   └── LibroRepository.java
├── dto/                       # Objetos de transferencia de datos (DTOs)
│   ├── DatosAutor.java
│   ├── DatosLibro.java
│   └── DatosRespuestaAPI.java
├── service/                   # Lógica de negocio y servicios
│   ├── ConsumoAPI.java
│   ├── ConvierteDatos.java
│   ├── LibroMapper.java
│   └── LibroService.java
├── principal/                 # Interfaz de línea de comandos
│   └── Principal.java
└── LiteraluraApplication.java # Clase principal de Spring Boot
```
### 👥 Autor
David Lima - https://github.com/Chinotrunk

### 🙏 Agradecimientos
Alura Latam y Oracle por el programa ONE.

Gutendex por proporcionar una API gratuita de libros.

Spring Boot por hacer el desarrollo de aplicaciones Java más fácil.

