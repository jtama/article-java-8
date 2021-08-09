package com.acme;

import java.util.stream.Stream;


public class Declarative {
    public void declarativeSample() {
        // tag::declarative[]
        String[] tableau = {"toto", "titi", "tata", "toto", ""};
        long count = Stream.of(tableau) // création d'un stream à partir du tableau
                    .filter(String::isBlank) // filtrage des éléments vide
                    .distinct() // suppression des doublons
                    .count(); // décompte des éléments restants
        // end::declarative[]
    }

}

