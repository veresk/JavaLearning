package ru.javaLerning.lesson1;

public class MainApp {
    public static void main(String[] args) {
        var person = new Person("Вася");

        var skateboard = new Skateboard();
        var bike = new Bike();
        var car = new Car();

        person.ride(skateboard);

        person.stop();

        person.stop();

        person.ride(bike);

        person.ride(car);

        person.stop();

        person.ride(car);

        person.ride(car);

        person.stop();
    }
}