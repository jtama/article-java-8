package com.acme;

import java.util.List;
public class MethodReferencePrinter {
    // tag::named-lambda-equivalence-first[]
public void printElements(List<String> myList, List<String> theirList) {
    myList.forEach(item -> this.printElement(item));
    theirList.forEach(item -> this.printElement(item));
}

private void printElement(String element) {
        System.out.println("This is one element of my list: " + element);
    }
    // end::named-lambda-equivalence-first[]

    // tag::named-lambda-equivalence-second[]
public void printElements(List<String> myList, List<String> theirList) {
    myList.forEach(this::printElement);
    theirList.forEach(this::printElement);
}

private void printElement(String element) {
    System.out.println("This is one element of my list: " + element);
}
    // end::named-lambda-equivalence-second[]
}

