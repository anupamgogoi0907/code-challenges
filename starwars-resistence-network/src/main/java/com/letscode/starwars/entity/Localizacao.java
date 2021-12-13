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
@Entity(name = "Localizacao")
@Table(name = "Localizacao")
public class Localizacao {

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(name = "latitude", nullable = false)
    private String latitude;


    @Column(name = "longitude", nullable = false)
    private String longitude;


    @Column(name = "nome", nullable = false)
    private String nome;

}
