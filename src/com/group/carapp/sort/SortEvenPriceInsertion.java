package com.group.carapp.sort;

import com.group.carapp.model.Car;

import java.util.ArrayList;
import java.util.List;

public class SortEvenPriceInsertion implements SortStrategy {

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

        insertionSort(evenCars);

        for (int i = 0; i < evenIndexes.size(); i++) {
            cars.set(evenIndexes.get(i), evenCars.get(i));
        }
    }

    private void insertionSort(List<Car> cars) {
        for (int i = 1; i < cars.size(); i++) {
            Car current = cars.get(i);
            int j = i - 1;
            while (j >= 0 && cars.get(j).getPrice() > current.getPrice()) {
                cars.set(j + 1, cars.get(j));
                j--;
            }
            cars.set(j + 1, current);
        }
    }

    @Override
    public String getName() {
        return "Сортировка по числовому полю (вставками)";
    }
}