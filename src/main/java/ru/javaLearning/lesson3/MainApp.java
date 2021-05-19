package ru.javaLearning.lesson3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

public class MainApp {
    public static void main(String[] args) {
        part1();

        part2();

        part3();

        part4();
    }

    private static void part1() {
        System.out.println("part1");

        var ints = new Integer[]{1, 2, 3};

        var strings = new String[]{"1", "2", "3"};

        swap(ints, 1, 2);

        System.out.println(Arrays.toString(ints));

        swap(strings, 2, 1);

        System.out.println(Arrays.toString(strings));

        System.out.println();
    }

    private static <T> void swap(T[] array, int index1, int index2) {
        var t = array[index1];

        array[index1] = array[index2];

        array[index2] = t;
    }

    private static void part2() {
        System.out.println("part2");

        var list = arrayAsArrayList(new Integer[]{1, 2, 3});

        System.out.println(list);

        System.out.println();
    }

    private static <T> ArrayList<T> arrayAsArrayList(T[] array) {
        return new ArrayList<>(Arrays.asList(array));
    }

    private static void part3() {
        System.out.println("part3");

        var applesBox = new Box<Apple>();

        applesBox.putFruits(new Apple(), new Apple());

        System.out.println("Вес коробки с яблоками: " + applesBox.getWeight());

        var orangesBox = new Box<Orange>();

        orangesBox.putFruits(new Orange(), new Orange());

        System.out.println("Вес коробки с апельсинами: " + orangesBox.getWeight());

        System.out.println("Массы коробок равны: " + applesBox.compare(orangesBox));

        var anotherAppleBox = new Box<Apple>();

        anotherAppleBox.putFruits(new Apple());

        System.out.println("Вес другой коробки с яблоками: " + anotherAppleBox.getWeight());

        try {
            applesBox.pourFromAnother(anotherAppleBox);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Массы коробок равны: " + applesBox.compare(orangesBox));

        System.out.println("Вес другой коробки с яблоками: " + anotherAppleBox.getWeight());

        System.out.println();
    }

    private static void part4() {
        System.out.println("part4");

        var strings = new String[18];

        for (int i = 0; i < 5; i++) strings[i] = "яблоко";

        for (int i = 0; i < 10; i++) strings[5 + i] = "апельсин";

        for (int i = 0; i < 3; i++) strings[15 + i] = "банан";

        var set = new HashSet<String>();

        Collections.addAll(set, strings);

        System.out.println(set);

        var stringsList = Arrays.asList(strings);

        for (String i : set) {
            System.out.println(i + ": " + Collections.frequency(stringsList, i));
        }
    }
}
