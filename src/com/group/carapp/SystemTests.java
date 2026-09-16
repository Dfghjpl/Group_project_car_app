package com.group.carapp;

import com.group.carapp.collection.CarStreamFiller;
import com.group.carapp.collection.CustomCarList;
import com.group.carapp.file.CarFileService;
import com.group.carapp.model.Car;
import com.group.carapp.sort.*;

import java.util.ArrayList;
import java.util.List;

import com.group.carapp.thread.CarOccurrenceCounter;

public class SystemTests {

    private static int passed=0;
    private static int falied=0;

    public static void main(String[] args) {
        runTest("Полный цикл", SystemTests::testFullPipeline);
        runTest("Все сортировки", SystemTests::testAllSortingsWork);
        runTest("ДЗ1: чёт/нечет (пузырёк)", SystemTests::testEvenOddBubble);
        runTest("ДЗ1: чёт/нечет (вставка)", SystemTests::testEvenOddInsertion);
        runTest("ДЗ1: чёт/нечет (выбор)", SystemTests::testEvenOddSelection);
        runTest("ДЗ2: сохранение с добавлением", SystemTests::testSaveAndReload);
        runTest("ДЗ3*: кастомная коллекция", SystemTests::testCustomCollection);
        runTest("ДЗ4: подсчёт вхождений", SystemTests::testOccurrenceCounter);

        System.out.println();
        System.out.println("=== ИТОГ: " + passed + " пройдено, " + falied + " упало ===");
    }
    private static void runTest(String name, Runnable test){
        try {
            test.run();
            System.out.println("[Пройден] "+name);
            passed++;
        }catch (AssertionError error){
            System.out.println("[Потерпел неудачу] "+name+error.getMessage());
            falied++;
        }catch (Exception e){
            System.out.println("[Ошибка] "+name+e.getMessage());
            falied++;
        }
    }
    private static Car build(String plate, String brand, int price) {
        return new Car.Builder()
                .setLicensePlate(plate)
                .setBrand(brand)
                .setPrice(price)
                .build();
    }
    private static void testFullPipeline(){
        List<Car> cars= new ArrayList<>();
        cars.add(build("A111","BMW", 23000));
        cars.add(build("B222","Audi", 56000));
        cars.add(build("C333","KIA", 89000));

        new SortBrand().sort(cars);

        CarFileService fileService=new CarFileService();
        fileService.writeToTxt("system_test.txt",cars);
        List<Car> loaded = fileService.readFromTxt("system_test.txt");

        if (loaded.size() != cars.size()){
            throw new AssertionError("Загружено не то количество машин");
        }
        for (int i =0; i< cars.size();i++ ){
            if(!cars.get(i).getLicensePlate().equals(loaded.get(i).getLicensePlate())){
                throw new AssertionError("Порядок не сохранился");

            }
        }
    }
    private static void testAllSortingsWork(){
        SortStrategy[] strategies={
                new SortLicensePlate(),
                new SortBrand(),
                new SortPrice(),
                new SortAllFields(),
                new SortEvenPriceBubble(),
                new SortEvenPriceInsertion(),
                new SortEvenPriceSelection()
        };

        for (SortStrategy strategy:strategies){
            List<Car> cars = new ArrayList<>();
            cars.add(build("C333","Volvo",97000));
            cars.add(build("B222","Haval",68000));
            cars.add(build("A111","Lexus",26000));

            strategy.sort(cars);

            if(cars.size()!=3){
                throw new AssertionError(strategy.getName()+ " изменил размер списка");
            }
        }

    }
    private static void testSaveAndReload(){
        CarFileService fileService=new CarFileService();

        List<Car> first = new ArrayList<>();
        first.add(build("X111","Test1", 123456789));
        fileService.writeToTxt("append_test.txt", first);

        List<Car> second = new ArrayList<>();
        second.add(build("Y222","Test2", 987654321));
        fileService.appendToTxt("append_test.txt", second);

        List<Car> result = fileService.readFromTxt("append_test.txt");
        if (result.size()!=2){
            throw new AssertionError("ДЗ2: данные не добавились в конец файла ");
        }

    }
    private static void testEvenOddBubble() {
        List<Car> cars = createEvenOddTestData();
        new SortEvenPriceBubble().sort(cars);
        checkEvenOddResult(cars);
    }

    private static void testEvenOddInsertion() {
        List<Car> cars = createEvenOddTestData();
        new SortEvenPriceInsertion().sort(cars);
        checkEvenOddResult(cars);
    }

    private static void testEvenOddSelection() {
        List<Car> cars = createEvenOddTestData();
        new SortEvenPriceSelection().sort(cars);
        checkEvenOddResult(cars);
    }

    // Общие данные для всех трёх тестов
    private static List<Car> createEvenOddTestData() {
        List<Car> cars = new ArrayList<>();
        cars.add(build("A111", "BMW", 1000));
        cars.add(build("B222", "Audi", 1501));
        cars.add(build("C333", "Kia", 500));
        cars.add(build("D444", "Ford", 1701));
        cars.add(build("E555", "Mazda", 2000));
        return cars;
    }

    // Общая проверка результата
    private static void checkEvenOddResult(List<Car> cars) {
        if (cars.get(1).getPrice() != 1501) {
            throw new AssertionError("ДЗ1: нечётный элемент сдвинулся (индекс 1)");
        }
        if (cars.get(3).getPrice() != 1701) {
            throw new AssertionError("ДЗ1: нечётный элемент сдвинулся (индекс 3)");
        }
        if (cars.get(0).getPrice() != 500 || cars.get(2).getPrice() != 1000) {
            throw new AssertionError("ДЗ1: чётные не отсортированы");
        }
    }private static void testOccurrenceCounter() {
        Car target = build("A111", "BMW", 500);

        List<Car> cars = new ArrayList<>();
        cars.add(target);
        cars.add(build("B222", "Audi", 300));
        cars.add(target);
        cars.add(build("C333", "Kia", 400));
        cars.add(target);

        int result = new CarOccurrenceCounter().count(cars, target, 3);

        if (result != 3) {
            throw new AssertionError("ДЗ4: ожидалось 3, получено " + result);
        }
    }
    private static void testCustomCollection() {
        List<Car> cars = new ArrayList<>();
        cars.add(build("A111", "BMW", 100));
        cars.add(build("B222", "Audi", 200));

        CustomCarList customList = new CarStreamFiller().fillList(cars);

        if (customList.size() != 2) {
            throw new AssertionError("ДЗ3*: кастомная коллекция заполнена неверно");
        }
    }

}
