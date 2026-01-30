package com.davidlima.literalura.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Autor {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String nombre;

  private Integer fechaDeNacimiento;
  private Integer fechaDeFallecimiento;

  @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
  private List<Libro> libros = new ArrayList<>();
}
