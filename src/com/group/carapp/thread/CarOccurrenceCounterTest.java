package com.group.carapp.thread;

import com.group.carapp.model.Car;
import java.util.ArrayList;
import java.util.List;

public class CarOccurrenceCounterTest {

    public static void main(String[] args) {
        testCount();
        testEmptyList();
        testInvalidThreadCount();

        System.out.println();
        System.out.println(
                "Все тесты CarOccurrenceCounter пройдены."
        );
    }

    private static void testCount() {
        Car target = createCar("A111", "BMW", 100);

        List<Car> cars = new ArrayList<>();

        cars.add(target);
        cars.add(createCar("B222", "Audi", 200));
        cars.add(target);
        cars.add(createCar("C333", "Lada", 300));
        cars.add(target);
        cars.add(createCar("D444", "Ford", 400));

        CarOccurrenceCounter counter =
                new CarOccurrenceCounter();

        int result = counter.count(cars, target, 3);

        if (result != 3) {
            throw new AssertionError(
                    "Ожидалось 3 вхождения, получено: "
                            + result
            );
        }

        System.out.println("testCount: OK");
        System.out.println(
                "Количество вхождений: " + result
        );
    }

    private static void testEmptyList() {
        Car target = createCar("A111", "BMW", 100);

        List<Car> cars = new ArrayList<>();

        CarOccurrenceCounter counter =
                new CarOccurrenceCounter();

        int result = counter.count(cars, target, 3);

        if (result != 0) {
            throw new AssertionError(
                    "Для пустого списка ожидалось 0"
            );
        }

        System.out.println("testEmptyList: OK");
    }

    private static void testInvalidThreadCount() {
        Car target = createCar("A111", "BMW", 100);

        List<Car> cars = new ArrayList<>();
        cars.add(target);

        CarOccurrenceCounter counter =
                new CarOccurrenceCounter();

        try {
            counter.count(cars, target, 0);

            throw new AssertionError(
                    "Не обработано некорректное количество потоков"
            );

        } catch (IllegalArgumentException e) {
            System.out.println(
                    "testInvalidThreadCount: OK"
            );
        }
    }

    private static Car createCar(
            String licensePlate,
            String brand,
            int price) {

        return new Car.Builder()
                .setLicensePlate(licensePlate)
                .setBrand(brand)
                .setPrice(price)
                .build();
    }
}
