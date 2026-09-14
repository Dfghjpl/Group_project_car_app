package com.group.carapp.sort;

import java.util.ArrayList;
import java.util.List;

public class SortAllFields implements SortStrategy {

    @Override
    public void sort(List<Car> cars) {
        if (cars == null) {
            throw new IllegalArgumentException("Список автомобилей не может быть пустой");
        }
        if (cars.size() < 2) {
            return;
        }
        for (Car car : cars) {
            if (car == null) {
                throw new IllegalArgumentException("В списке есть пустой элемент");
            }
        }

        List<Car> sorted = mergeSort(cars);

        for (int i = 0; i < cars.size(); i++) {
            cars.set(i, sorted.get(i));
        }
    }

    private List<Car> mergeSort(List<Car> cars) {
        if (cars.size() < 2) {
            return cars;
        }

        int mid = cars.size() / 2;
        List<Car> left = new ArrayList<>(cars.subList(0, mid));
        List<Car> right = new ArrayList<>(cars.subList(mid, cars.size()));

        left = mergeSort(left);
        right = mergeSort(right);

        return merge(left, right);
    }

    private List<Car> merge(List<Car> left, List<Car> right) {
        List<Car> result = new ArrayList<>();
        int i = 0;
        int j = 0;

        while (i < left.size() && j < right.size()) {
            if (compareCars(left.get(i), right.get(j)) <= 0) {
                result.add(left.get(i));
                i++;
            } else {
                result.add(right.get(j));
                j++;
            }
        }

        while (i < left.size()) {
            result.add(left.get(i));
            i++;
        }
        while (j < right.size()) {
            result.add(right.get(j));
            j++;
        }

        return result;
    }

    private int compareCars(Car first, Car second) {
        int byBrand = first.getBrand().compareTo(second.getBrand());
        if (byBrand != 0) {
            return byBrand;
        }
        int byPrice = Integer.compare(first.getPrice(), second.getPrice());
        if (byPrice != 0) {
            return byPrice;
        }
        return first.getLicensePlate().compareTo(second.getLicensePlate());
    }

    @Override
    public String getName() {
        return "Сортировка по всем полям";
    }
}