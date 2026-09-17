package com.group.carapp.collection;

import com.group.carapp.model.Car;
/*
 * CustomCarSet — это реализация множества автомобилей.
 * Внутри используется массив Car[] и переменная size.
 * 
 * Главная особенность Set — он не позволяет добавлять одинаковые элементы.
 */
public class CustomCarSet {

    private Car[] elements; //массив в котором хранятся автомобили
    private int size; //количество автомобилей

    public CustomCarSet() {
        elements = new Car[10]; //массив Car - 10 ячеек
        size = 0; //пустое множество
    }

    public boolean add(Car car) { //добавляет автомобиль в множество
        if (car == null) { //проверка, что автомобиль не равен null
            throw new IllegalArgumentException(
                    "Автомобиль не может быть null"
            );
        }

        if (contains(car)) { //проверка на дубликат
            return false;
        }

        ensureCapacity(); //проверка, что в массиве осталось место

        elements[size] = car; //помещает автомобиль в первую свободную ячейку
        size++; //увеличивает множество

        return true; //возвращает true
    }

    public boolean contains(Car car) {
        if (car == null) {
            return false; //возвращает false
        }

        //Программа проходит по всем автомобилям
        //Каждый найденный автомобиль записывается в Car current
        for (int i = 0; i < size; i++) {
            Car current = elements[i];

            if (current.getLicensePlate()
                    .equals(car.getLicensePlate())
                    && current.getBrand()
                    .equals(car.getBrand())
                    && current.getPrice() == car.getPrice()) {

                return true;
            }
        }

        return false;
    }

    public int size() { //возвращает количество автомобилей
        return size;
    }

    public boolean isEmpty() { //проверяет, пустое ли множество
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

    //метод нужен, чтобы показать множество
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
