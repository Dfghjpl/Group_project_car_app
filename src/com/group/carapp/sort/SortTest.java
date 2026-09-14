package com.group.carapp.sort;

import java.util.ArrayList;
import java.util.List;

public class SortTest {

    public static void main(String[] args) {
        System.out.println("Проверка сортировок:");
        System.out.println();

        SortStrategy[] strategies = {
                new SortLicensePlate(),
                new SortBrand(),
                new SortPrice()
        };

        for (SortStrategy strategy : strategies) {
            System.out.println("Стратегия: " + strategy.getName());
            testSorting(strategy);
            testNullList(strategy);
            testNullElement(strategy);
            System.out.println();
        }

        SortStrategy evenStrategy = new SortEvenPrice();
        System.out.println("Стратегия: " + evenStrategy.getName());
        testSortEvenPrice();
        testNullList(evenStrategy);
        testNullElement(evenStrategy);
        System.out.println();

        System.out.println("Все сортировки работают корректно.");
    }

    private static void testSortEvenPrice() {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("A111", "BMW", 1000));
        cars.add(new Car("B222", "Audi", 1501));
        cars.add(new Car("C333", "Lada", 2000));
        cars.add(new Car("D444", "Ford", 1701));
        cars.add(new Car("E555", "Kia", 500));

        new SortEvenPrice().sort(cars);

        checkPrice(cars.get(0), 500);
        checkPrice(cars.get(1), 1501);
        checkPrice(cars.get(2), 1000);
        checkPrice(cars.get(3), 1701);
        checkPrice(cars.get(4), 2000);

        for (Car car : cars) {
            System.out.println("  " + car.getLicensePlate() + " " + car.getBrand() + " " + car.getPrice());
        }
    }

    private static void checkPrice(Car car, int expectedPrice) {
        if (car.getPrice() != expectedPrice) {
            throw new AssertionError("Ожидалась цена " + expectedPrice + ", получена " + car.getPrice());
        }
    }

    private static void testSorting(SortStrategy strategy) {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("C333", "Lada", 300));
        cars.add(new Car("A111", "BMW", 100));
        cars.add(new Car("B222", "Audi", 200));

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
            if (compare(cars.get(i), cars.get(i + 1), strategy) > 0) {
                return false;
            }
        }
        return true;
    }

    private static int compare(Car first, Car second, SortStrategy strategy) {
        if (strategy instanceof SortLicensePlate) {
            return first.getLicensePlate().compareTo(second.getLicensePlate());
        }
        if (strategy instanceof SortBrand) {
            return first.getBrand().compareTo(second.getBrand());
        }
        if (strategy instanceof SortPrice) {
            return Integer.compare(first.getPrice(), second.getPrice());
        }
        throw new IllegalArgumentException("Неизвестная стратегия");
    }
}