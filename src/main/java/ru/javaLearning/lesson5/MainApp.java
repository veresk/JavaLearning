package ru.javaLearning.lesson5;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainApp {
    public static final int CARS_COUNT = 4;

    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");

        Semaphore semaphore = new Semaphore(2);

        final Lock winnerLock = new ReentrantLock();

        Race race = new Race(new Road(60, null), new Tunnel(semaphore), new Road(40, winnerLock));

        Car[] cars = new Car[CARS_COUNT];

        final CyclicBarrier cyclicBarrier = new CyclicBarrier(CARS_COUNT);

        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10), cyclicBarrier);
        }

        var service = Executors.newFixedThreadPool(cars.length);

        for (int i = 0; i < cars.length; i++) {
            var fi = i;

            service.execute(() -> cars[fi].run());
        }

        service.shutdown();

        try {
            service.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}
