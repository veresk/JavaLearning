package ru.javaLearning.lesson2;

import java.util.*;
import java.util.function.IntFunction;

public class MainApp {
    public static void main(String[] args) {
        part1To3();

        part4();
        //* по 10000 обращений
        //                    10     1000     100000     10000000
        //ArrayList:          1      0        0          0
        //LinkedList:         1      6        587        116905

        part5();
        //                    100     10000     100000
        //ArrayList:          1       38        2040
        //LinkedList:         0       80        7763
    }

    private static void part1To3() {
        part1To3CallMethod(new String[5][4]);
        part1To3CallMethod(new String[4][5]);
        part1To3CallMethod(new String[4][4]);

        String[][] matrix = {{"1", "2", "3", "4"}, {"1", "2", "3", "4"}, {"1", "2", "3", "4"}, {"1", "2", "3", "4"}};
        part1To3CallMethod(matrix);
    }

    private static void part1To3CallMethod(String[][] matrix) {
        try {
            var result = part1To3Method(matrix);

            System.out.println("Сумма: " + result);
        } catch (MyArraySizeException | MyArrayDataException e) {
            e.printStackTrace();
        }
    }

    private static int part1To3Method(String[][] matrix) throws MyArraySizeException, MyArrayDataException {
        if (matrix.length != 4 || matrix[0].length != 4) throw new MyArraySizeException();

        int sum = 0;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                try {
                    sum += Integer.parseInt(matrix[i][j]);
                } catch (NumberFormatException e) {
                    throw new MyArrayDataException("В ячейке [" + i + "," + j + "] находятся неверные данные");
                }
            }
        }

        return sum;
    }

    private static void part4() {
        var steps = 4;
        var iterations = 10000;

        var length = 10;

        long[][] results = new long[steps][3];

        for (int i = 0; i < steps; i++) {
            results[i][0] = length;
            results[i][1] = part4MeasureArrayListGet(length, iterations);
            results[i][2] = part4MeasureLinkedListGet(length, iterations);

            length *= 100;
        }

        System.out.println("* по " + iterations + " обращений");
        printLine("", results, steps, 0);
        printLine("ArrayList:", results, steps, 1);
        printLine("LinkedList:", results, steps, 2);
    }

    private static void part5() {
        var steps = 3;

        int[] lengths = {100, 10000, 100000};

        long[][] results = new long[steps][3];

        for (int i = 0; i < lengths.length; i++) {
            results[i][0] = lengths[i];
            results[i][1] = part4MeasureArrayListDelete(lengths[i]);
            results[i][2] = part4MeasureLinkedListDelete(lengths[i]);
        }

        printLine("", results, steps, 0);
        printLine("ArrayList:", results, steps, 1);
        printLine("LinkedList:", results, steps, 2);
    }

    private static void printLine(String text, long[][] results, int steps, int lineIndex) {
        System.out.printf("%-20s", text);

        for (int i = 0; i < steps; i++)
            System.out.print(String.format("%-" + (results[i][0] + "").length() + "s", results[i][lineIndex]) + "     ");

        System.out.println();
    }

    private static long part4MeasureArrayListGet(int length, int iterations) {
        var array = CreateArrayList(length);

        return part4MeasureGet(length, iterations, array::get);
    }

    private static long part4MeasureLinkedListGet(int length, int iterations) {
        var list = CreateLinkedList(length);

        return part4MeasureGet(length, iterations, list::get);
    }

    private static long part4MeasureArrayListDelete(int length) {
        return part4MeasureDelete(length, CreateArrayList(length));
    }

    private static long part4MeasureLinkedListDelete(int length) {
        return part4MeasureDelete(length, CreateLinkedList(length));
    }

    private static long part4MeasureGet(int length, int iterations, IntFunction<Integer> get) {
        var time = System.currentTimeMillis();

        for (int i = 0; i < iterations; i++) get.apply(length / 2);

        return System.currentTimeMillis() - time;
    }

    private static long part4MeasureDelete(int length, Collection<Integer> collection) {
        var time = System.currentTimeMillis();

        for (int i = 0; i < length / 2; i++) collection.remove((length - i) / 2);

        return System.currentTimeMillis() - time;
    }

    private static ArrayList<Integer> CreateArrayList(int length) {
        return new ArrayList<>(Collections.nCopies(length, 0));
    }

    private static LinkedList<Integer> CreateLinkedList(int length) {
        return new LinkedList<>(Collections.nCopies(length, 0));
    }
}