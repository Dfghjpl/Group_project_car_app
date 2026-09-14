package collection;

import sort.Car;

public class CustomCarQueue {

    private Car[] elements;
    private int size;

    public CustomCarQueue() {
        elements = new Car[10];
        size = 0;
    }

    public void add(Car car) {
        if (car == null) {
            throw new IllegalArgumentException(
                    "Автомобиль не может быть null"
            );
        }

        ensureCapacity();

        elements[size] = car;
        size++;
    }

    public Car peek() {
        if (isEmpty()) {
            throw new IllegalStateException(
                    "Очередь пуста"
            );
        }

        return elements[0];
    }

    public Car poll() {
        if (isEmpty()) {
            throw new IllegalStateException(
                    "Очередь пуста"
            );
        }

        Car first = elements[0];

        for (int i = 1; i < size; i++) {
            elements[i - 1] = elements[i];
        }

        elements[size - 1] = null;
        size--;

        return first;
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