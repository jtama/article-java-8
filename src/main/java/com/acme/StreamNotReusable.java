package com.acme;

import java.util.stream.Stream;


public class StreamNotReusable {
    public void declarativeSample() {
        // tag::no-reusability[]
String[] tableau = {"toto", "titi", "tata", "toto", ""};
Stream<String> stream = Stream.of(tableau);

long count = stream
            .filter(item -> item.isBlank())
            .distinct()
            .count();

long totosNumber = stream
        .filter(str -> "toto".equalsIgnoreCase(str))
        .count(); <1>
        // end::no-reusability[]
    }

}

