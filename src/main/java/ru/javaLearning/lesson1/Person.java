package ru.javaLearning.lesson1;

public class Person extends AbleToUseTransport {
    Person(String name) {
        this.name = name;
    }

    private final String name;
}