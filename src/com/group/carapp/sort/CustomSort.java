package com.group.carapp.sort;

import com.group.carapp.model.Car;
import java.util.List;

public class CustomSort implements SortStrategy {

    private static final int INSERTION_SORT_THRESHOLD = 10;

    @Override
    public void sort(List<Car> cars) {
        validateCars(cars);

        if (cars.size() < 2) {
            return;
        }

        mergeSort(cars, 0, cars.size() - 1);
    }

    @Override
    public String getName() {
        return "Custom Sort";
    }

    private void mergeSort(
            List<Car> cars,
            int left,
            int right) {

        if (left >= right) {
            return;
        }

        // Для небольших частей используем сортировку вставками
        if (right - left + 1 <= INSERTION_SORT_THRESHOLD) {
            insertionSort(cars, left, right);
            return;
        }

        int middle = left + (right - left) / 2;

        mergeSort(cars, left, middle);
        mergeSort(cars, middle + 1, right);

        merge(cars, left, middle, right);
    }

    private void insertionSort(
            List<Car> cars,
            int left,
            int right) {

        for (int i = left + 1; i <= right; i++) {

            Car current = cars.get(i);
            int j = i - 1;

            while (j >= left
                    && compareCars(cars.get(j), current) > 0) {

                cars.set(j + 1, cars.get(j));
                j--;
            }

            cars.set(j + 1, current);
        }
    }

    private void merge(
            List<Car> cars,
            int left,
            int middle,
            int right) {

        int leftSize = middle - left + 1;
        int rightSize = right - middle;

        Car[] leftArray = new Car[leftSize];
        Car[] rightArray = new Car[rightSize];

        for (int i = 0; i < leftSize; i++) {
            leftArray[i] = cars.get(left + i);
        }

        for (int i = 0; i < rightSize; i++) {
            rightArray[i] = cars.get(middle + 1 + i);
        }

        int i = 0;
        int j = 0;
        int k = left;

        while (i < leftSize && j < rightSize) {

            if (compareCars(
                    leftArray[i],
                    rightArray[j]) <= 0) {

                cars.set(k, leftArray[i]);
                i++;

            } else {

                cars.set(k, rightArray[j]);
                j++;
            }

            k++;
        }

        while (i < leftSize) {
            cars.set(k, leftArray[i]);
            i++;
            k++;
        }

        while (j < rightSize) {
            cars.set(k, rightArray[j]);
            j++;
            k++;
        }
    }

    /**
     * Сравнение автомобилей:
     * 1. По марке
     * 2. По цене
     * 3. По государственному номеру
     */
    private int compareCars(
            Car first,
            Car second) {

        int byBrand =
                first.getBrand()
                        .compareTo(second.getBrand());

        if (byBrand != 0) {
            return byBrand;
        }

        int byPrice =
                Integer.compare(
                        first.getPrice(),
                        second.getPrice());

        if (byPrice != 0) {
            return byPrice;
        }

        return first.getLicensePlate()
                .compareTo(second.getLicensePlate());
    }

    private void validateCars(List<Car> cars) {

        if (cars == null) {
            throw new IllegalArgumentException(
                    "Список автомобилей не может быть null"
            );
        }

        for (Car car : cars) {
            if (car == null) {
                throw new IllegalArgumentException(
                        "В списке есть пустой элемент"
                );
            }
        }
    }
}
