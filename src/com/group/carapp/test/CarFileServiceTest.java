package com.group.carapp.test;

import com.group.carapp.file.CarFileService;
import com.group.carapp.model.Car;
import java.util.ArrayList;
import java.util.List;

// Этот класс проверяет работу CarFileService
// Здесь отдельно проверяем TXT, JSON, добавление данных и обработку ошибок

public class CarFileServiceTest {

    public static void main(String[] args) {
        testWriteToTxt();
        testReadFromTxt();
        testAppendToTxt();
        testReadInvalidTxt();
        testWriteAndReadJson();
        testAppendToJson();
        testReadInvalidJson();
    }

    // Проверяем обычную запись списка автомобилей в TXT-файл
    private static void testWriteToTxt() {
        CarFileService fileService = new CarFileService();

        List<Car> cars = new ArrayList<>();

        cars.add(new Car.Builder()
                .setLicensePlate("A123BC")
                .setBrand("Toyota")
                .setPrice(25000)
                .build());

        cars.add(new Car.Builder()
                .setLicensePlate("B456CD")
                .setBrand("BMW")
                .setPrice(35000)
                .build());

        fileService.writeToTxt("cars.txt", cars);

        System.out.println("Запись в TXT выполнена");
    }

    // Проверяем, что записанные автомобили можно прочитать обратно из TXT
    private static void testReadFromTxt() {
        CarFileService fileService = new CarFileService();

        List<Car> cars = fileService.readFromTxt("cars.txt");

        System.out.println("Автомобили из файла:");

        for (Car car : cars) {
            System.out.println(car);
        }
    }

    // Проверяем режим добавления данных
    // После добавления старые автомобили должны остаться в файле
    private static void testAppendToTxt() {
        CarFileService fileService = new CarFileService();

        List<Car> cars = new ArrayList<>();

        cars.add(new Car.Builder()
                .setLicensePlate("C789EF")
                .setBrand("Audi")
                .setPrice(40000)
                .build());

        fileService.appendToTxt("cars.txt", cars);

        System.out.println("Добавление в TXT выполнено");

        List<Car> result = fileService.readFromTxt("cars.txt");

        System.out.println("Содержимое файла после добавления:");

        for (Car car : result) {
            System.out.println(car);
        }
    }

    // Проверяем файл, в котором есть правильные и неправильные строки
    // Некорректные строки должны пропускаться, а корректные загружаться
    private static void testReadInvalidTxt() {
        CarFileService fileService = new CarFileService();

        List<Car> cars = fileService.readFromTxt("cars-invalid.txt");

        System.out.println("Корректные автомобили из файла с ошибками:");

        for (Car car : cars) {
            System.out.println(car);
        }
    }

    // Проверяем запись автомобилей в JSON и чтение этих же данных обратно
    private static void testWriteAndReadJson() {
        CarFileService fileService = new CarFileService();

        List<Car> cars = new ArrayList<>();

        cars.add(new Car.Builder()
                .setLicensePlate("A123BC")
                .setBrand("Toyota")
                .setPrice(25000)
                .build());

        cars.add(new Car.Builder()
                .setLicensePlate("B456CD")
                .setBrand("BMW")
                .setPrice(35000)
                .build());

        fileService.writeToJson("cars.json", cars);

        System.out.println("Запись в JSON выполнена");

        List<Car> result = fileService.readFromJson("cars.json");

        System.out.println("Автомобили из JSON:");

        for (Car car : result) {
            System.out.println(car);
        }
    }

    // Проверяем добавление нового автомобиля в существующий JSON-файл
    private static void testAppendToJson() {
        CarFileService fileService = new CarFileService();

        List<Car> cars = new ArrayList<>();

        cars.add(new Car.Builder()
                .setLicensePlate("C789EF")
                .setBrand("Audi")
                .setPrice(40000)
                .build());

        fileService.appendToJson("cars.json", cars);

        System.out.println("Добавление в JSON выполнено");

        List<Car> result = fileService.readFromJson("cars.json");

        System.out.println("Содержимое JSON после добавления:");

        for (Car car : result) {
            System.out.println(car);
        }
    }

    // Проверяем JSON с корректными и некорректными объектами
    // Неправильные автомобили должны быть пропущены
    private static void testReadInvalidJson() {
        CarFileService fileService = new CarFileService();

        List<Car> cars = fileService.readFromJson("cars-invalid.json");

        System.out.println("Корректные автомобили из JSON с ошибками:");

        for (Car car : cars) {
            System.out.println(car);
        }
    }
}