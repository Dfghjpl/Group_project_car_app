package com.group.carapp.sort;

import java.util.ArrayList;
import java.util.List;

public class SortTest {

    public static void main(String[] args) {
        System.out.println("Проверка сортировок:");
        System.out.println();

        SortStrategy[] strategies = {new SortLicensePlate(), new SortBrand()};

        for (SortStrategy strategy : strategies) {
            System.out.println("Стратегия: " + strategy.getName());
            testSorting(strategy);
            testNullList(strategy);
            testNullElement(strategy);
            System.out.println();
        }

        System.out.println("Все сортировки работают корректно.");
    }

    private static void testSorting(SortStrategy strategy) {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("C333", "Lada", 100));
        cars.add(new Car("A111", "BMW", 200));
        cars.add(new Car("B222", "Audi", 300));

        strategy.sort(cars);

        if (!isSorted(cars, strategy)) {
            throw new AssertionError(strategy.getName() + ": список остался неотсортированным");
        }
        for (Car car : cars) {
            System.out.println("  " + car.getLicensePlate() + " " + car.getBrand() + " " + car.getPrice());
        }
    }

    private static void testNullList(SortStrategy strategy) {
        try {
            strategy.sort(null);
            throw new AssertionError(strategy.getName() + ": не обработан null вместо списка");
        } catch (IllegalArgumentException e) {
            System.out.println("  " + e.getMessage());
        }
    }

    private static void testNullElement(SortStrategy strategy) {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("A111", "Audi", 100));
        cars.add(null);
        cars.add(new Car("B222", "BMW", 200));
        try {
            strategy.sort(cars);
            throw new AssertionError(strategy.getName() + ": не обработан пустой элемент в списке");
        } catch (IllegalArgumentException e) {
            System.out.println("  " + e.getMessage());
        }
    }

    private static boolean isSorted(List<Car> cars, SortStrategy strategy) {
        for (int i = 0; i < cars.size() - 1; i++) {
            String first = fieldOf(cars.get(i), strategy);
            String second = fieldOf(cars.get(i + 1), strategy);
            if (first.compareTo(second) > 0) {
                return false;
            }
        }
        return true;
    }

    private static String fieldOf(Car car, SortStrategy strategy) {
        if (strategy instanceof SortLicensePlate) {
            return car.getLicensePlate();
        }
        if (strategy instanceof SortBrand) {
            return car.getBrand();
        }
        throw new IllegalArgumentException("Неизвестная стратегия");
    }
}