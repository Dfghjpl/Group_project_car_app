package sort;

import java.util.ArrayList;
import java.util.List;

public class CustomSortTest {

    public static void main(String[] args) {
        testSortingByAllFields();
        testSameBrandDifferentPrice();
        testSameBrandAndPriceDifferentLicensePlate();
        testAlreadySorted();
        testNullList();
        testNullElement();
        testEmptyList();
        testSingleElement();

        System.out.println();
        System.out.println("Все тесты CustomSort пройдены.");
    }

    private static void testSortingByAllFields() {
        List<Car> cars = new ArrayList<>();

        cars.add(new Car("C333", "Volvo", 500));
        cars.add(new Car("A111", "BMW", 700));
        cars.add(new Car("B222", "Audi", 300));
        cars.add(new Car("D444", "BMW", 400));
        cars.add(new Car("F666", "Audi", 200));
        cars.add(new Car("E555", "Kia", 600));

        CustomSort strategy = new CustomSort();
        strategy.sort(cars);

        checkCar(cars.get(0), "F666", "Audi", 200);
        checkCar(cars.get(1), "B222", "Audi", 300);
        checkCar(cars.get(2), "D444", "BMW", 400);
        checkCar(cars.get(3), "A111", "BMW", 700);
        checkCar(cars.get(4), "E555", "Kia", 600);
        checkCar(cars.get(5), "C333", "Volvo", 500);

        System.out.println("testSortingByAllFields: OK");

        printCars(cars);
    }

    private static void testSameBrandDifferentPrice() {
        List<Car> cars = new ArrayList<>();

        cars.add(new Car("A111", "BMW", 900));
        cars.add(new Car("B222", "BMW", 300));
        cars.add(new Car("C333", "BMW", 600));
        cars.add(new Car("D444", "BMW", 100));

        CustomSort strategy = new CustomSort();
        strategy.sort(cars);

        checkPrice(cars.get(0), 100);
        checkPrice(cars.get(1), 300);
        checkPrice(cars.get(2), 600);
        checkPrice(cars.get(3), 900);

        System.out.println("testSameBrandDifferentPrice: OK");
    }

    private static void testSameBrandAndPriceDifferentLicensePlate() {
        List<Car> cars = new ArrayList<>();

        cars.add(new Car("C333", "BMW", 500));
        cars.add(new Car("A111", "BMW", 500));
        cars.add(new Car("B222", "BMW", 500));
        cars.add(new Car("D444", "BMW", 500));

        CustomSort strategy = new CustomSort();
        strategy.sort(cars);

        checkLicensePlate(cars.get(0), "A111");
        checkLicensePlate(cars.get(1), "B222");
        checkLicensePlate(cars.get(2), "C333");
        checkLicensePlate(cars.get(3), "D444");

        System.out.println(
                "testSameBrandAndPriceDifferentLicensePlate: OK");
    }

    private static void testAlreadySorted() {
        List<Car> cars = new ArrayList<>();

        cars.add(new Car("A111", "Audi", 100));
        cars.add(new Car("B222", "BMW", 200));
        cars.add(new Car("C333", "Kia", 300));
        cars.add(new Car("D444", "Volvo", 400));

        CustomSort strategy = new CustomSort();
        strategy.sort(cars);

        checkCar(cars.get(0), "A111", "Audi", 100);
        checkCar(cars.get(1), "B222", "BMW", 200);
        checkCar(cars.get(2), "C333", "Kia", 300);
        checkCar(cars.get(3), "D444", "Volvo", 400);

        System.out.println("testAlreadySorted: OK");
    }

    private static void testNullList() {
        CustomSort strategy = new CustomSort();

        try {
            strategy.sort(null);

            throw new AssertionError(
                    "Не обработан null вместо списка");

        } catch (IllegalArgumentException e) {
            System.out.println("testNullList: OK");
        }
    }

    private static void testNullElement() {
        CustomSort strategy = new CustomSort();

        List<Car> cars = new ArrayList<>();

        cars.add(new Car("A111", "Audi", 100));
        cars.add(null);
        cars.add(new Car("B222", "BMW", 200));

        try {
            strategy.sort(cars);

            throw new AssertionError(
                    "Не обработан null-элемент");

        } catch (IllegalArgumentException e) {
            System.out.println("testNullElement: OK");
        }
    }

    private static void testEmptyList() {
        CustomSort strategy = new CustomSort();

        List<Car> cars = new ArrayList<>();

        strategy.sort(cars);

        if (!cars.isEmpty()) {
            throw new AssertionError(
                    "Пустой список был изменён");
        }

        System.out.println("testEmptyList: OK");
    }

    private static void testSingleElement() {
        CustomSort strategy = new CustomSort();

        List<Car> cars = new ArrayList<>();

        Car car = new Car("A111", "BMW", 500);
        cars.add(car);

        strategy.sort(cars);

        if (cars.size() != 1 || cars.get(0) != car) {
            throw new AssertionError(
                    "Список из одного элемента был изменён");
        }

        System.out.println("testSingleElement: OK");
    }

    private static void checkCar(
            Car actual,
            String expectedLicensePlate,
            String expectedBrand,
            int expectedPrice) {

        if (!actual.getLicensePlate().equals(expectedLicensePlate)
                || !actual.getBrand().equals(expectedBrand)
                || actual.getPrice() != expectedPrice) {

            throw new AssertionError(
                    "Неверный порядок автомобилей. Ожидался: "
                            + expectedLicensePlate + " "
                            + expectedBrand + " "
                            + expectedPrice
                            + ", получен: "
                            + actual.getLicensePlate() + " "
                            + actual.getBrand() + " "
                            + actual.getPrice());
        }
    }

    private static void checkPrice(Car car, int expectedPrice) {
        if (car.getPrice() != expectedPrice) {
            throw new AssertionError(
                    "Неверный порядок цен. Ожидалось: "
                            + expectedPrice
                            + ", получено: "
                            + car.getPrice());
        }
    }

    private static void checkLicensePlate(
            Car car,
            String expectedLicensePlate) {

        if (!car.getLicensePlate().equals(expectedLicensePlate)) {
            throw new AssertionError(
                    "Неверный порядок госномеров. Ожидалось: "
                            + expectedLicensePlate
                            + ", получено: "
                            + car.getLicensePlate());
        }
    }

    private static void printCars(List<Car> cars) {
        for (Car car : cars) {
            System.out.println(
                    car.getLicensePlate()
                            + " "
                            + car.getBrand()
                            + " "
                            + car.getPrice());
        }
    }
}