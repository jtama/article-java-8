package com.acme;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamBasics {

    public void howToGenerate() throws IOException {
        streamFromVoid();
        streamFromArray();
        streamFromCollection();
        streamFromNumerics();
        streamFromStreams();
    }

    public void howToTransform() throws IOException {
        // tag::middle-methods-[]

        // end::middle-methods-[]

//peek

        distinct();
        sorted();
        filter();
        map();
        flatMap();
    }

    public void howToTerminate() throws IOException {
        // tag::end-methods-reduce[]
        class PairOfSocks {
            public PairOfSocks(String color, int size) {
                this.color = color;
                this.size = size;
            }

            private String color;
            private int size;
        }

        PairOfSocks[] socks = {
                new PairOfSocks("blanc", 38),
                new PairOfSocks("bordeaux", 42),
                new PairOfSocks("bleu", 39)
        };

        PairOfSocks neutralSock = new PairOfSocks("gris", 40);
        BinaryOperator<PairOfSocks> makePatchworkSocks = (someSocks, someOtherSocks) -> new PairOfSocks(someSocks.color + "," + someOtherSocks.color, someSocks.size);

        PairOfSocks patchworkSocks = Arrays.stream(socks).reduce(neutralSock, makePatchworkSocks); // >> donne une PairOfSocks("gris,blanc,bordeaux,bleu", 40)
        // end::end-methods-reduce[]

        // tag::end-methods-[]

        // end::end-methods-[]
    }

    private void distinct() {
        // tag::middle-methods-distinct[]
        Stream<Character> letters = Stream.of('a', 'b', 'j', 'z', 'b');
        Stream<Character> distinctLetters = letters.distinct(); // contains only 'a', 'b', 'j', 'z'
        // end::middle-methods-distinct[]
    }

    private void sorted() {
        // tag::middle-methods-sorted[]
        Stream<Character> letters = Stream.of('a', 'b', 'j', 'z', 'b');
        letters.sorted();
        // equivalent à
        letters.sorted((someLetter, someOtherLetter) -> someLetter.compareTo(someOtherLetter));
        // equivalent à
        letters.sorted(Comparator.naturalOrder());
        // end::middle-methods-sorted[]
    }

    private void filter() {
        // tag::middle-methods-filter[]
        Stream<String> names = Stream.of("Pierre-Toto", "Jean-Toto", "Tutu", "Toto");

        Predicate<String> containsToto = someString -> someString != null && someString.contains("Toto");
        Stream<String> namesContainingToto = names.filter(containsToto);
        // end::middle-methods-filter[]
    }

    private void map() {
        // tag::middle-methods-map[]
        class PairOfSocks {
            public PairOfSocks(String color, int size) {
                this.color = color;
                this.size = size;
            }

            private String color;
            private int size;
        }

        PairOfSocks[] socks = {
                new PairOfSocks("blanc", 38),
                new PairOfSocks("bordeaux", 42),
                new PairOfSocks("bleu", 39)
        };

        Stream<PairOfSocks> socksStream = Arrays.stream(socks);
        Stream<Integer> socksSizes = Arrays.stream(socks).map(pair -> pair.size);
        Stream<String> socksColors = Arrays.stream(socks).map(pair -> pair.color);
        Stream<PairOfSocks> biggerSocks = Arrays.stream(socks).map(pair -> new PairOfSocks(pair.color, pair.size + 1));
        // end::middle-methods-map[]
    }

    private void flatMap() {
        // tag::middle-methods-flatmap[]
        Stream<String> names = Stream.of("Pierre-Toto", "Jean-Toto", "Tutu", "Toto");
        Stream<String> letters = names.flatMap(name -> Arrays.stream(name.split(""))); // splits into letters

        // end::middle-methods-flatmap[]
    }

    private void streamFromVoid() {
        // tag::generate-from-void[]
        Stream<Object> empty = Stream.empty();
        // end::generate-from-void[]
    }

    private void streamFromArray() {
        // tag::generate-from-array-1[]
        String[] helloWorld = {"Hello", "stream", "world", "!"};
        Stream<String> helloStream = Arrays.stream(helloWorld);
        Stream<String> otherHelloStream = Stream.of(helloWorld);
        // end::generate-from-array-1[]
    }

    private void streamFromCollection() {
        // tag::generate-from-collection[]
        List<String> helloWorld = Arrays.asList("Hello", "stream", "world", "!");
        Stream<String> helloStream = helloWorld.stream();
        // end::generate-from-collection[]
    }

    private void streamFromNumerics() {
        // tag::generate-from-suite[]
        IntStream zeroToHundred = IntStream.range(0, 100);
        DoubleStream squaresOfTwo = DoubleStream.iterate(2, i -> i < 1000000, i -> i * 2);
        // end::generate-from-suite[]
    }

    private void streamFromStreams() throws IOException {
        // tag::generate-from-stream[]
        Stream<String> fewWords = Stream.<String>builder()
                .add("words")
                .add("to")
                .add("add")
                .build();
        Stream<String> filesLines = Files.lines(Path.of("/c/documents/file-sample.txt"));
        Stream<String> linesStartingWithAddedWords = Stream.concat(fewWords, filesLines);
        // end::generate-from-stream[]
    }
}
