package collection;

import sort.Car;

public class CustomCarSet {

    private Car[] elements;
    private int size;

    public CustomCarSet() {
        elements = new Car[10];
        size = 0;
    }

    public boolean add(Car car) {
        if (car == null) {
            throw new IllegalArgumentException(
                    "Автомобиль не может быть null"
            );
        }

        if (contains(car)) {
            return false;
        }

        ensureCapacity();

        elements[size] = car;
        size++;

        return true;
    }

    public boolean contains(Car car) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(car)) {
                return true;
            }
        }

        return false;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void ensureCapacity() {
        if (size < elements.length) {
            return;
        }

        Car[] newElements =
                new Car[elements.length * 2];

        for (int i = 0; i < elements.length; i++) {
            newElements[i] = elements[i];
        }

        elements = newElements;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");

        for (int i = 0; i < size; i++) {
            result.append(elements[i]);

            if (i < size - 1) {
                result.append(", ");
            }
        }

        result.append("]");

        return result.toString();
    }
}