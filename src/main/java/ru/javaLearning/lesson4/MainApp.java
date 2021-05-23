package ru.javaLearning.lesson4;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainApp {
    public static void main(String[] args) {
        method1();
        method2();
    }

    static final int SIZE = 100_000_000;

    private static void method1() {
        var arr = createArr();

        var time = measure(
                () -> {
                    for (int i = 0; i < SIZE; i++) arr[i] = calculate(arr, i);
                }
        );

        System.out.println(time);
    }

    private static void method2() {
        var arr = createArr();

        var time = measure(() -> method2Internal(arr, 1000));

        System.out.println(time);
    }

    private static void method2Internal(float[] arr, int threadsCount) {
        var service = Executors.newFixedThreadPool(threadsCount);

        for (int i = 0; i < SIZE; i++) {

            int fi = i;

            service.execute(() -> arr[fi] = calculate(arr, fi));
        }

        service.shutdown();

        try {
            service.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static float[] createArr() {
        var arr = new float[SIZE];

        Arrays.fill(arr, 1);

        return arr;
    }

    private static float calculate(float[] arr, int i) {
        return (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
    }

    private static long measure(Runnable runnable) {
        var time = System.currentTimeMillis();

        runnable.run();

        return System.currentTimeMillis() - time;
    }
}