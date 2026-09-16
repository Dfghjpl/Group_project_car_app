package com.group.carapp.sort;

import com.group.carapp.model.Car;

import java.util.ArrayList;
import java.util.List;

public class SortEvenPriceSelection implements SortStrategy {

    @Override
    public void sort(List<Car> cars) {
        if (cars == null || cars.isEmpty()) {
            return;
        }

        List<Integer> positions = new ArrayList<>();
        List<Car> toSort = new ArrayList<>();

        for (int i = 0; i < cars.size(); i++) {
            Car car = cars.get(i);
            if (car == null) {
                throw new IllegalArgumentException("Автомобиль не может быть пустым");
            }
            if (car.getPrice() % 2 == 0) {
                positions.add(i);
                toSort.add(car);
            }
        }

        for (int i = 0; i < toSort.size() - 1; i++) {
            int min = i;
            for (int j = i + 1; j < toSort.size(); j++) {
                if (toSort.get(j).getPrice() < toSort.get(min).getPrice()) {
                    min = j;
                }
            }
            Car temp = toSort.get(i);
            toSort.set(i, toSort.get(min));
            toSort.set(min, temp);
        }

        for (int i = 0; i < positions.size(); i++) {
            cars.set(positions.get(i), toSort.get(i));
        }
    }

    @Override
    public String getName() {
        return "Сортировка по числовому полю (выбором)";
    }
}