package com.letscode.starwars.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity(name = "Rebelde")
@Table(name = "Rebelde")
public class Rebelde {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "idade", nullable = false)
    private int idade;

    @Column(name = "genero", nullable = false)
    private String genero;

    @Column(name = "traidor", nullable = false)
    private boolean traidor;

    @Column(name = "reporters", nullable = true)
    private String reporters;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "localizacao_id", referencedColumnName = "id")
    private Localizacao localizacao;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "inventario_id", referencedColumnName = "id")
    private Inventario inventario;
}
