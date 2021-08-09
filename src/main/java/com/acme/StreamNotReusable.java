package com.acme;

import java.util.stream.Stream;


public class StreamNotReusable {
    public void declarativeSample() {
        // tag::no-reusability[]
        String[] tableau = {"toto", "titi", "tata", "toto", ""};
        Stream<String> stream = Stream.of(tableau);

        long count = stream
                    .filter(String::isBlank)
                    .distinct()
                    .count();

        long totosNumber = stream // stream déjà épuisé !
                .filter(str -> "toto".equalsIgnoreCase(str))
                .count();
        // end::no-reusability[]
    }

}

