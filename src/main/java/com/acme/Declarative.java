package com.acme;

import java.util.stream.Stream;


public class Declarative {
    public void declarativeSample() {
        // tag::declarative[]
String[] tableau = {"toto", "titi", "tata", "toto", ""};
long count = Stream.of(tableau) <1>
            .filter(item -> item.isBlank()) <2>
            .distinct() <3>
            .count(); <4>
        // end::declarative[]
    }

}

