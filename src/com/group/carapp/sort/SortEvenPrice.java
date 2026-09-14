package com.group.carapp.sort;

import java.util.ArrayList;
import java.util.List;

public class SortEvenPrice implements SortStrategy {

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

        shellSort(evenCars);

        for (int i = 0; i < evenIndexes.size(); i++) {
            cars.set(evenIndexes.get(i), evenCars.get(i));
        }
    }

    private void shellSort(List<Car> cars) {
        int n = cars.size();
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                Car current = cars.get(i);
                int j = i;
                while (j >= gap && cars.get(j - gap).getPrice() > current.getPrice()) {
                    cars.set(j, cars.get(j - gap));
                    j -= gap;
                }
                cars.set(j, current);
            }
        }
    }

    @Override
    public String getName() {
        return "Сортировка по чётной цене";
    }
}