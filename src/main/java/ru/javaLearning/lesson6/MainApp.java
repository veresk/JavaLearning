package ru.javaLearning.lesson6;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainApp {
    public static void main(String[] args) {
        method1();
        method2();
        method3();
        method4();
        method5();
        method6();
        method7();
    }

    private static void method1() {
        var fruits = new ArrayList<>(Arrays.asList("apple", "banana", "orange", "banana", "apple", "orange", "apple"));

        var key = fruits
                .stream()
                .collect(Collectors.groupingBy(f -> f, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        System.out.println(key);
    }

    private static void method2() {
        var employees = CreateEmployees();

        var avg = employees
                .stream()
                .mapToInt(Employee::getSalary)
                .average()
                .orElse(0);

        System.out.println(avg);
    }

    private static void method3() {
        var employees = CreateEmployees();

        var n = 2;

        var nOldestEmployeesNames = employees
                .stream()
                .sorted(Comparator.comparing(Employee::getAge).reversed())
                .limit(n)
                .map(Employee::getName);

        System.out.println(n + " самых старших сотрудников зовут: " + nOldestEmployeesNames.collect(Collectors.joining(", ")) + ";");
    }

    private static void method4() {
        var hundredWordsString = CreateNWordsString(100);

        var result =
                Arrays.stream(hundredWordsString.split("\\s"))
                        .filter(s -> s.length() >= 5)
                        .collect(Collectors.joining(" "));

        System.out.println(result);
    }

    private static void method5() {
        var evenSum = IntStream.range(100, 201).filter(e -> e % 2 == 0).sum();
        var oddSum = IntStream.range(100, 201).filter(e -> e % 2 != 0).sum();

        System.out.println("even sum: " + evenSum);
        System.out.println("odd sum: " + oddSum);
    }

    private static void method6() {
        var list = new ArrayList<>(Arrays.asList(CreateRandomWord(), CreateRandomWord(), CreateRandomWord(), CreateRandomWord(), CreateRandomWord()));

        var sum = list.stream().mapToInt(String::length).sum();

        System.out.println("sum length: " + sum);
    }

    private static void method7() {
        var list = new ArrayList<>(Arrays.asList(CreateRandomWord(), CreateRandomWord(), CreateRandomWord(), CreateRandomWord(), CreateRandomWord()));

        var result = list
                .stream()
                .sorted(Comparator.comparing(s -> s))
                .limit(3);

        System.out.println(result.collect(Collectors.toList()));
    }

    private static ArrayList<Employee> CreateEmployees() {
        return new ArrayList<>(Arrays.asList(new Employee("Test2", 28, 28000), new Employee("Test1", 25, 25000), new Employee("Test3", 33, 33000)));
    }

    private static String CreateNWordsString(int length) {
        var sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append(CreateRandomWord());

            if (i != length - 1) sb.append(" ");
        }

        return sb.toString();
    }

    private static String CreateRandomWord() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'

        var random = new Random();

        var randomLength = random.nextInt(4) + 3;

        return random.ints(leftLimit, rightLimit + 1)
                .limit(randomLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
