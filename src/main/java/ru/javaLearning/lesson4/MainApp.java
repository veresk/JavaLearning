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
    static final int THREADS_COUNT = 3;

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

        var time = measure(() -> method2Internal(arr, THREADS_COUNT));

        System.out.println(time);
    }

    private static void method2Internal(float[] arr, int threadsCount) {
        var service = Executors.newFixedThreadPool(threadsCount);

        for (int i = 0; i < threadsCount; i++) {
            int partSize = SIZE / threadsCount + 1;

            var skip = i * partSize;

            int take = partSize;

            if (i == threadsCount - 1) take = SIZE - skip;

            int finalTake = take;

            service.execute(() -> calculateForArrPart(arr, skip, finalTake));
        }

        service.shutdown();

        try {
            service.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void calculateForArrPart(float[] arr, int skip, int take) {
        for (int i = skip; i < skip + take; i++) arr[i] = calculate(arr, i);
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