package com.group.carapp.sort;

import java.util.List;

public class SortBrand implements SortStrategy {

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

        for (int i = 0; i < cars.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < cars.size(); j++) {
                if (cars.get(j).getBrand().compareTo(cars.get(minIndex).getBrand()) < 0) {
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                Car temp = cars.get(i);
                cars.set(i, cars.get(minIndex));
                cars.set(minIndex, temp);
            }
        }
    }

    @Override
    public String getName() {
        return "Сортировка по марке";
    }
}