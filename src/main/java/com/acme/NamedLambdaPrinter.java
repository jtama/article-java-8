package com.acme;

import java.util.List;
import java.util.function.Consumer;


public class NamedLambdaPrinter {
    public void printElements(List<String> myList, List<String> theirList) {
        // tag::before-named-lambda2[]
        // tag::before-named-lambda1[]
myList.forEach(element -> System.out.println("This is one element of my list: " + element));
        // end::before-named-lambda1[]
theirList.forEach(element -> System.out.println("This is one element of my list: " + element));
        // end::before-named-lambda2[]

        // tag::named-lambda[]
Consumer<String> elementPrinter = element -> System.out.println("This is one element of my list: " + element);

myList.forEach(elementPrinter);
theirList.forEach(elementPrinter);
        // end::named-lambda[]
    }
}

