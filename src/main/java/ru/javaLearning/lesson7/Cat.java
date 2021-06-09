package ru.javaLearning.lesson7;

@AppTable(title = "cats")
public class Cat {
    @AppColumn
    public int id;

    @AppColumn
    public String name;

    @AppColumn
    public int tailsNumber;

    public Cat(String name) {
        this.name = name;
    }
}
