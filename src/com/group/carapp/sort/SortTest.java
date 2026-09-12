package com.group.carapp.sort;

import java.util.ArrayList;
import java.util.List;

public class SortTest {

    public static void main(String[] args) {
        System.out.println("Запуск тестов сортировки по гос номеру:");

        testSortLicensePlatePositive();
        testSortLicensePlateAlreadySorted();
        testSortLicensePlateNull();
        testSortLicensePlateEmpty();
        testSortLicensePlateNullElement();

        System.out.println("Все тесты пройдены успешно.");
    }

    private static void testSortLicensePlatePositive() {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("C333", "BMW", 300));
        cars.add(new Car("A111", "Audi", 100));
        cars.add(new Car("B222", "Lada", 200));

        new SortLicensePlate().sort(cars);

        checkOrder(cars, "A111", "B222", "C333");
        System.out.println("Сортировка неотсортированного списка: успешно");
    }

    private static void testSortLicensePlateAlreadySorted() {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("A111", "Audi", 100));
        cars.add(new Car("B222", "Lada", 200));
        cars.add(new Car("C333", "BMW", 300));

        new SortLicensePlate().sort(cars);

        checkOrder(cars, "A111", "B222", "C333");
        System.out.println("Сортировка уже отсортированного списка: успешно");
    }

    private static void testSortLicensePlateNull() {
        checkThrowsIllegalArgument(null, "Обработка null вместо списка");
    }

    private static void testSortLicensePlateEmpty() {
        List<Car> cars = new ArrayList<>();
        new SortLicensePlate().sort(cars);
        if (!cars.isEmpty()) {
            throw new AssertionError("Пустой список должен остаться пустым, но содержит " + cars.size() + " элементов");
        }
        System.out.println("Обработка пустого списка: успешно");
    }

    private static void testSortLicensePlateNullElement() {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("A111", "Audi", 100));
        cars.add(null);
        cars.add(new Car("B222", "BMW", 200));
        checkThrowsIllegalArgument(cars, "Обработка null-элемента внутри списка");
    }

    private static void checkOrder(List<Car> cars, String... expectedPlates) {
        if (cars.size() != expectedPlates.length) {
            throw new AssertionError("Ожидалось " + expectedPlates.length + " элементов, получено " + cars.size());
        }
        for (int i = 0; i < expectedPlates.length; i++) {
            String actual = cars.get(i).getLicensePlate();
            if (!actual.equals(expectedPlates[i])) {
                throw new AssertionError("На позиции " + i + " ожидался номер " + expectedPlates[i] + ", но получен " + actual);
            }
        }
    }

    private static void checkThrowsIllegalArgument(List<Car> cars, String label) {
        try {
            new SortLicensePlate().sort(cars);
            throw new AssertionError(label + ": ожидалось исключение IllegalArgumentException, но его не было");
        } catch (IllegalArgumentException e) {
            System.out.println(label + ": успешно");
        }
    }
}