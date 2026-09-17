package com.group.carapp.collection;

import com.group.carapp.model.Car;
/*
 * CustomCarQueue — это реализация очереди автомобилей.
 * Внутри используется массив Car[], а переменная size хранит количество элементов.
 * 
 * Очередь работает по принципу FIFO — первый добавленный автомобиль первым выходит.
 */
public class CustomCarQueue {

    private Car[] elements; //массив в котором хранятся автомобили
    private int size; //количество автомобилей

    public CustomCarQueue() {
        elements = new Car[10]; //массив Car - 10 ячеек
        size = 0; //пустая очередь
    }

    public void add(Car car) { //добавляет автомобиль в конец очереди
        if (car == null) { //проверка, что автомобиль не равен null
            throw new IllegalArgumentException(
                    "Автомобиль не может быть null"
            );
        }

        ensureCapacity(); //проверка, что в массиве осталось место

        elements[size] = car; //помещает автомобиль в первую свободную ячейку
        size++;
    }

    public Car peek() { //смотрит, кто первый в очереди, но не удаляет его
        if (isEmpty()) { //проверка пустой очереди
            throw new IllegalStateException(
                    "Очередь пуста"
            );
        }

        return elements[0];
    }

    public Car poll() { //забирает первого из очереди и удаляет его
        if (isEmpty()) { //проверка пустой очереди
            throw new IllegalStateException(
                    "Очередь пуста"
            );
        }

        Car first = elements[0];

        for (int i = 1; i < size; i++) { //сдвигает все автомобили на одну позицию влево
            elements[i - 1] = elements[i];
        }

        elements[size - 1] = null;
        size--; //уменьшает очередь

        return first;
    }

    public int size() { //возвращает количество автомобилей
        return size;
    }

    public boolean isEmpty() { //проверяет, пустая ли очередь
        return size == 0;
    }

    private void ensureCapacity() { //проверяет осталось ли место в массиве
        if (size < elements.length) {
            return;
        }

        Car[] newElements = //если массив заполнен, создает массив в 2 раза больше старого
                new Car[elements.length * 2];

        for (int i = 0; i < elements.length; i++) {
            newElements[i] = elements[i];
        }

        elements = newElements; //копирует элементы из старого массива в новый
    }

    //метод нужен, чтобы показать очередь
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
