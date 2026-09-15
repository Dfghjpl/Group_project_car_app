package test;

import model.Car;

// Этот класс проверяет создание объектов Car через Builder
// Здесь проверяем корректные данные и основные ошибки валидации

public class CarTest {

    public static void main(String[] args) {
        testCorrectCar();
        testEmptyLicensePlate();
        testEmptyBrand();
        testNegativePrice();
    }

    // Проверяем, что автомобиль с корректными данными успешно создается
    private static void testCorrectCar() {
        try {
            Car car = new Car.Builder()
                    .setLicensePlate("A123BC")
                    .setBrand("Toyota")
                    .setPrice(25000)
                    .build();

            System.out.println(
                    "Позитивный тест пройден: " + car
            );
        } catch (Exception exception) {
            System.out.println(
                    "Позитивный тест не пройден"
            );
        }
    }

    // Проверяем, что автомобиль нельзя создать с пустым номером
    private static void testEmptyLicensePlate() {
        try {
            new Car.Builder()
                    .setLicensePlate("")
                    .setBrand("Toyota")
                    .setPrice(25000)
                    .build();

            System.out.println(
                    "Негативный тест не пройден"
            );

        } catch (IllegalArgumentException exception) {
            System.out.println(
                    "Негативный тест пройден: "
                            + exception.getMessage()
            );
        }
    }

    // Проверяем, что автомобиль нельзя создать с пустой маркой
    private static void testEmptyBrand() {
        try {
            new Car.Builder()
                    .setLicensePlate("A123BC")
                    .setBrand("")
                    .setPrice(25000)
                    .build();

            System.out.println(
                    "Негативный тест не пройден"
            );

        } catch (IllegalArgumentException exception) {
            System.out.println(
                    "Негативный тест пройден: "
                            + exception.getMessage()
            );
        }
    }

    // Проверяем, что автомобиль нельзя создать с отрицательной ценой
    private static void testNegativePrice() {
        try {
            new Car.Builder()
                    .setLicensePlate("A123BC")
                    .setBrand("Toyota")
                    .setPrice(-100)
                    .build();

            System.out.println(
                    "Негативный тест не пройден"
            );

        } catch (IllegalArgumentException exception) {
            System.out.println(
                    "Негативный тест пройден: "
                            + exception.getMessage()
            );
        }
    }
}