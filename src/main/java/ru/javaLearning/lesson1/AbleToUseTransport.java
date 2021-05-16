package ru.javaLearning.lesson1;

public abstract class AbleToUseTransport {
    private Transport currentTransport;

    public void ride(Transport transport) {
        if (currentTransport != null) {
            if (currentTransport == transport) {
                System.out.println("Человек уже двигается конкретно на этом транспорте типа " + currentTransport);
            } else {
                System.out.println("Человек не может менять транспорт во время движения. Движение на транспорте типа " + currentTransport + " продолжается");
            }
        } else {
            currentTransport = transport;

            System.out.println("Человек начал передвигаться на транспорте типа " + currentTransport);
        }
    }

    public void stop() {
        if (currentTransport == null) {
            System.out.println("Человек остался стоять неподвижным");
        } else {
            System.out.println("Человек закончил передвигаться на транспорте типа " + currentTransport);

            currentTransport = null;
        }
    }
}