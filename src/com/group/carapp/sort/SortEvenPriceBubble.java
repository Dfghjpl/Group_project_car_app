package com.group.carapp.sort;

import com.group.carapp.model.Car;

import java.util.ArrayList;
import java.util.List;

public class SortEvenPriceBubble implements SortStrategy {

    @Override
    public void sort(List<Car> cars) {
        if (cars == null) {
            throw new IllegalArgumentException("Список автомобилей не может быть пустым");
        }
        if (cars.size() < 2) {
            return;
        }
        for (Car car : cars) {
            if (car == null) {
                throw new IllegalArgumentException("В списке есть пустой элемент");
            }
        }

        List<Integer> evenIndexes = new ArrayList<>();
        List<Car> evenCars = new ArrayList<>();

        for (int i = 0; i < cars.size(); i++) {
            if (cars.get(i).getPrice() % 2 == 0) {
                evenIndexes.add(i);
                evenCars.add(cars.get(i));
            }
        }

        bubbleSort(evenCars);

        for (int i = 0; i < evenIndexes.size(); i++) {
            cars.set(evenIndexes.get(i), evenCars.get(i));
        }
    }

    private void bubbleSort(List<Car> cars) {
        for (int i = 0; i < cars.size() - 1; i++) {
            for (int j = 0; j < cars.size() - 1 - i; j++) {
                if (cars.get(j).getPrice() > cars.get(j + 1).getPrice()) {
                    Car temp = cars.get(j);
                    cars.set(j, cars.get(j + 1));
                    cars.set(j + 1, temp);
                }
            }
        }
    }

    @Override
    public String getName() {
        return "Сортировка по числовому полю (пузырьком)";
    }
}