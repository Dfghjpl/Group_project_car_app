package com.group.carapp.sort;

import com.group.carapp.model.Car;

import java.util.ArrayList;
import java.util.List;

public class SortTest {

    public static void main(String[] args) {


        testSortLicensePlate();
        testSortBrand();
        testSortPrice();
        testSortAllFields();
        testSortEvenPriceInsertion();
        testSortEvenPriceSelection();
        testSortEvenPriceBubble();

        System.out.println("\nВсе тесты пройдены");
    }

    private static void testSortLicensePlate() {
        System.out.println("Сортировка по гос.номеру");

        List<Car> cars = new ArrayList<>();
        cars.add(makeCar("C333", "Lada", 300));
        cars.add(makeCar("A111", "BMW", 100));
        cars.add(makeCar("B222", "Audi", 200));

        new SortLicensePlate().sort(cars);

        check(cars.get(0).getLicensePlate().equals("A111"), "на первом месте A111");
        check(cars.get(1).getLicensePlate().equals("B222"), "на втором месте B222");
        check(cars.get(2).getLicensePlate().equals("C333"), "на третьем месте C333");

        printCars(cars);
        System.out.println("прошла успешно\n");
    }

    private static void testSortBrand() {
        System.out.println("Сортировка по марке");

        List<Car> cars = new ArrayList<>();
        cars.add(makeCar("C333", "Lada", 300));
        cars.add(makeCar("A111", "BMW", 100));
        cars.add(makeCar("B222", "Audi", 200));

        new SortBrand().sort(cars);

        check(cars.get(0).getBrand().equals("Audi"), "первая марка Audi");
        check(cars.get(1).getBrand().equals("BMW"), "вторая марка BMW");
        check(cars.get(2).getBrand().equals("Lada"), "третья марка Lada");

        printCars(cars);
        System.out.println("прошла успешно\n");
    }

    private static void testSortPrice() {
        System.out.println("Сортировка по цене");

        List<Car> cars = new ArrayList<>();
        cars.add(makeCar("C333", "Lada", 300));
        cars.add(makeCar("A111", "BMW", 100));
        cars.add(makeCar("B222", "Audi", 200));

        new SortPrice().sort(cars);

        check(cars.get(0).getPrice() == 100, "первая цена 100");
        check(cars.get(1).getPrice() == 200, "вторая цена 200");
        check(cars.get(2).getPrice() == 300, "третья цена 300");

        printCars(cars);
        System.out.println("прошла успешно\n");
    }

    private static void testSortAllFields() {
        System.out.println("Сортировка по всем полям");

        List<Car> cars = new ArrayList<>();
        cars.add(makeCar("C333", "BMW", 300));
        cars.add(makeCar("A111", "Audi", 100));
        cars.add(makeCar("B222", "Audi", 200));

        new SortAllFields().sort(cars);

        check(cars.get(0).getBrand().equals("Audi") && cars.get(0).getPrice() == 100, "Audi 100");
        check(cars.get(1).getBrand().equals("Audi") && cars.get(1).getPrice() == 200, "Audi 200");
        check(cars.get(2).getBrand().equals("BMW") && cars.get(2).getPrice() == 300, "BMW 300");

        printCars(cars);
        System.out.println("прошла успешно\n");
    }

    private static void testSortEvenPriceInsertion() {
        System.out.println("Сортировка по числовому полю (вставками)");

        List<Car> cars = new ArrayList<>();
        cars.add(makeCar("A111", "BMW", 1000));
        cars.add(makeCar("B222", "Audi", 1501));
        cars.add(makeCar("C333", "Lada", 2000));
        cars.add(makeCar("D444", "Ford", 1701));
        cars.add(makeCar("E555", "Kia", 500));

        new SortEvenPriceInsertion().sort(cars);

        check(cars.get(0).getPrice() == 500, "позиция 1: чётная 500");
        check(cars.get(1).getPrice() == 1501, "позиция 2: нечётная 1501 не сдвинулась");
        check(cars.get(2).getPrice() == 1000, "позиция 3: чётная 1000");
        check(cars.get(3).getPrice() == 1701, "позиция 4: нечётная 1701 не сдвинулась");
        check(cars.get(4).getPrice() == 2000, "позиция 5: чётная 2000");

        printCars(cars);
        System.out.println("прошла успешно\n");
    }

    private static void testSortEvenPriceSelection() {
        System.out.println("Сортировка по числовому полю (выбором)");

        List<Car> cars = new ArrayList<>();
        cars.add(makeCar("A111", "BMW", 1000));
        cars.add(makeCar("B222", "Audi", 1501));
        cars.add(makeCar("C333", "Lada", 2000));
        cars.add(makeCar("D444", "Ford", 1701));
        cars.add(makeCar("E555", "Kia", 500));

        new SortEvenPriceSelection().sort(cars);

        check(cars.get(0).getPrice() == 500, "позиция 1: чётная 500");
        check(cars.get(1).getPrice() == 1501, "позиция 2: нечётная 1501 не сдвинулась");
        check(cars.get(2).getPrice() == 1000, "позиция 3: чётная 1000");
        check(cars.get(3).getPrice() == 1701, "позиция 4: нечётная 1701 не сдвинулась");
        check(cars.get(4).getPrice() == 2000, "позиция 5: чётная 2000");

        printCars(cars);
        System.out.println("прошла успешно\n");
    }

    private static void testSortEvenPriceBubble() {
        System.out.println("Сортировка по числовому полю (пузырьком)");

        List<Car> cars = new ArrayList<>();
        cars.add(makeCar("A111", "BMW", 1000));
        cars.add(makeCar("B222", "Audi", 1501));
        cars.add(makeCar("C333", "Lada", 2000));
        cars.add(makeCar("D444", "Ford", 1701));
        cars.add(makeCar("E555", "Kia", 500));

        new SortEvenPriceBubble().sort(cars);

        check(cars.get(0).getPrice() == 500, "позиция 1: чётная 500");
        check(cars.get(1).getPrice() == 1501, "позиция 2: нечётная 1501 не сдвинулась");
        check(cars.get(2).getPrice() == 1000, "позиция 3: чётная 1000");
        check(cars.get(3).getPrice() == 1701, "позиция 4: нечётная 1701 не сдвинулась");
        check(cars.get(4).getPrice() == 2000, "позиция 5: чётная 2000");

        printCars(cars);
        System.out.println("прошла успешно\n");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("Проверка провалена: " + message);
        }
    }

    private static void printCars(List<Car> cars) {
        for (Car car : cars) {
            System.out.println("    " + car.getLicensePlate() + " " + car.getBrand() + " " + car.getPrice());
        }
    }

    private static Car makeCar(String plate, String brand, int price) {
        return new Car.Builder()
                .setLicensePlate(plate)
                .setBrand(brand)
                .setPrice(price)
                .build();
    }
}