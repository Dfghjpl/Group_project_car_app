package com.group.carapp.sort;

import java.util.List;

public class SortLicensePlate implements SortStrategy {

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

        for (int i = 1; i < cars.size(); i++) {
            Car current = cars.get(i);
            int j = i - 1;
            while (j >= 0 && cars.get(j).getLicensePlate()
                    .compareTo(current.getLicensePlate()) > 0) {
                cars.set(j + 1, cars.get(j));
                j--;
            }
            cars.set(j + 1, current);
        }
    }

    @Override
    public String getName() {
        return "Сортировка по гос номеру";
    }
}