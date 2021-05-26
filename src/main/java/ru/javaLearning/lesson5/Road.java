package ru.javaLearning.lesson5;

import java.util.concurrent.locks.Lock;

public class Road extends Stage {
    private static Boolean weHaveAWinner = false;
    private final Lock winnerLock;

    public Road(int length, Lock winnerLock) {
        this.winnerLock = winnerLock;
        this.length = length;
        this.description = "Дорога " + length + " метров";
    }

    @Override
    public void go(Car c) {
        try {
            System.out.println(c.getName() + " начал этап: " + description);
            Thread.sleep(length / c.getSpeed() * 1000L);
            if (winnerLock != null) {
                try {
                    winnerLock.lock();

                    printFinishStage(c.getName());

                    if (!weHaveAWinner) {
                        System.out.println(c.getName() + " - WIN");
                        weHaveAWinner = true;
                    }
                } finally {
                    winnerLock.unlock();
                }
            } else {
                printFinishStage(c.getName());
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void printFinishStage(String name) {
        System.out.println(name + " закончил этап: " + description);
    }
}
