package com.group.carapp.collection;

import com.group.carapp.model.Car;
import java.util.ArrayList;
import java.util.List;

public class CarStreamFillerTest {

    public static void main(String[] args) {

        testCustomList();
        testCustomSet();
        testCustomQueue();

        System.out.println();
        System.out.println(
                "Все тесты CarStreamFiller пройдены."
        );
    }

    private static List<Car> createCars() {
        List<Car> cars = new ArrayList<>();

        cars.add(
                createCar("A111", "BMW", 100)
        );

        cars.add(
                createCar("B222", "Audi", 200)
        );

        cars.add(
                createCar("C333", "Lada", 300)
        );

        cars.add(
                createCar("D444", "Ford", 400)
        );

        return cars;
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

    private static void testCustomList() {
        List<Car> cars = createCars();

        CarStreamFiller filler =
                new CarStreamFiller();

        CustomCarList result =
                filler.fillList(cars);

        if (result.size() != 4) {
            throw new AssertionError(
                    "CustomCarList должен содержать 4 автомобиля"
            );
        }

        System.out.println("CustomCarList: OK");
        System.out.println(result);
    }

    private static void testCustomSet() {
        List<Car> cars = createCars();

        cars.add(
                createCar("A111", "BMW", 100)
        );

        CarStreamFiller filler =
                new CarStreamFiller();

        CustomCarSet result =
                filler.fillSet(cars);

        if (result.size() != 4) {
            throw new AssertionError(
                    "CustomCarSet должен удалить дубликат"
            );
        }

        System.out.println("CustomCarSet: OK");
        System.out.println(result);
    }

    private static void testCustomQueue() {
        List<Car> cars = createCars();

        CarStreamFiller filler =
                new CarStreamFiller();

        CustomCarQueue result =
                filler.fillQueue(cars);

        if (result.size() != 4) {
            throw new AssertionError(
                    "CustomCarQueue должен содержать 4 автомобиля"
            );
        }

        Car first = result.peek();

        if (!first.getLicensePlate().equals(
                cars.get(0).getLicensePlate())) {

            throw new AssertionError(
                    "Queue нарушает порядок FIFO"
            );
        }

        Car removed = result.poll();

        if (!removed.getLicensePlate().equals(
                cars.get(0).getLicensePlate())) {

            throw new AssertionError(
                    "poll() должен вернуть первый элемент"
            );
        }

        System.out.println("CustomCarQueue: OK");
        System.out.println(result);
    }
}
