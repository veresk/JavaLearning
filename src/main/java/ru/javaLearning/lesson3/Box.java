package ru.javaLearning.lesson3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Box<T extends Fruit> {
    private ArrayList<T> items;

    public void putFruits(T... items) {
        putFruits(Arrays.asList(items));
    }

    private void putFruits(List<T> items) {
        if (this.items == null) this.items = new ArrayList<>();

        this.items.addAll(items);
    }

    public double getWeight() {
        if (items == null) return 0;

        var size = items.size();

        if (size == 0) return 0;

        return items.size() * items.get(0).getWeight();
    }

    public Boolean compare(Box<? extends Fruit> otherBox) {
        return Math.abs(this.getWeight() - otherBox.getWeight()) < 0.001;
    }

    public ArrayList<T> takeAwayAll() {
        var result = new ArrayList<>(items);

        items.clear();

        return result;
    }

    public void pourFromAnother(Box<T> box) throws Exception {
        if (this == box) throw new Exception("Нельзя пересыпать коробку саму в себя");

        putFruits(box.takeAwayAll());
    }
}