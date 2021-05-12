package ru.javaLerning.lesson1;

public class Person extends AbleToUseTransport {
    Person(String name) {
        this.name = name;
    }

    private final String name;

    public void ride(Transport transport) {
        if (currentTransport != null) {
            if (currentTransport == transport) {
                System.out.println(name + " уже двигается конкретно на этом транспорте типа " + currentTransport);
            } else {
                System.out.println(name + " не может менять транспорт во время движения. Движение на транспорте типа " + currentTransport + " продолжается");
            }
        } else {
            currentTransport = transport;

            System.out.println(name + " начал передвигаться на транспорте типа " + currentTransport);
        }
    }

    public void stop() {
        if (currentTransport == null) {
            System.out.println(name + " остался стоять неподвижным");
        } else {
            System.out.println(name + " закончил передвигаться на транспорте типа " + currentTransport);

            currentTransport = null;
        }
    }
}
