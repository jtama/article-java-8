package com.acme;

import java.util.List;
import java.util.function.Consumer;

// tag::named-lambda[]
public class NamedLambdaPrinter {
    public void printElements(List<String> myList, List<String> theirList) {
        Consumer<String> elementPrinter = element -> System.out.println("This is one element of my list: " + element);

        myList.forEach(elementPrinter);
        theirList.forEach(elementPrinter);
    }
}
// end::named-lambda[]
