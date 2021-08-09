package com.acme;

import java.util.List;
// tag::named-lambda-equivalence[]
public class MethodReferencePrinter {
    public void printElements(List<String> myList, List<String> theirList) {
        myList.forEach(this::printElement);
        theirList.forEach(this::printElement);
    }

    private void printElement(String element) {
        System.out.println("This is one element of my list: " + element);
    }
}
// end::named-lambda-equivalence[]
