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

        for (int i = 1; i < evenCars.size(); i++) {
            Car current = evenCars.get(i);
            int j = i - 1;
            while (j >= 0 && evenCars.get(j).getPrice() > current.getPrice()) {
                evenCars.set(j + 1, evenCars.get(j));
                j--;
            }
            evenCars.set(j + 1, current);
        }

        for (int i = 0; i < evenIndexes.size(); i++) {
            cars.set(evenIndexes.get(i), evenCars.get(i));
        }
    }

    @Override
    public String getName() {
        return "Сортировка по чётной цене";
    }
}