package com.group.carapp.menu;

import com.group.carapp.collection.CarStreamFiller;
import com.group.carapp.collection.CustomCarList;
import com.group.carapp.file.CarFileService;
import com.group.carapp.model.Car;
import com.group.carapp.sort.*;
import com.group.carapp.thread.CarOccurrenceCounter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Menu {
    private final Scanner scanner = new Scanner(System.in);
    private final CarFileService fileService = new CarFileService();
    private final CarOccurrenceCounter occurrenceCounter=new CarOccurrenceCounter();
    private final CarStreamFiller streamFiller = new CarStreamFiller();
    private final Random random=new Random();
    private final Map<Integer, SortStrategy> strategies=new LinkedHashMap<>();
    private List<Car> cars=new ArrayList<>();
    private  static final String[] BRANDS ={"Toyota", "BMW", "Audi", "Mercedes", "Honda",
            "Ford", "Kia", "Hyundai", "Mazda", "Nissan"};
    private static final String LETTERS = "ABEKMHOPCTYX";

    public Menu(){
        strategies.put(6, new SortLicensePlate());
        strategies.put(7,new SortBrand());
        strategies.put(8, new SortPrice());
        strategies.put(9,new SortEvenPrice());
        strategies.put(10, new SortAllFields());
    }
    private void printMenu() {
        System.out.println("\n=== МЕНЮ ===");
        System.out.println("1. Заполнить массив вручную");
        System.out.println("2. Заполнить массив из TXT-файла");
        System.out.println("3. Заполнить массив из JSON-файла");
        System.out.println("4. Заполнить массив рандомно (Stream)");
        System.out.println("5. Вывести текущий массив");
        System.out.println("6. Сортировка по госномеру");
        System.out.println("7. Сортировка по марке");
        System.out.println("8. Сортировка по цене");
        System.out.println("9. Сортировка чёт/нечет по цене (ДЗ1)");
        System.out.println("10. Сортировка по всем полям");
        System.out.println("11. Сохранить массив в файл (ДЗ2)");
        System.out.println("12. Подсчитать вхождения (ДЗ4)");
        System.out.println("13. Заполнить кастомную коллекцию (ДЗ3*)");
        System.out.println("0. Выход");
    }

    private int readIntSafely(String prompt){
        while (true){
            System.out.println(prompt);
            String input =scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            }catch (NumberFormatException e){
                System.out.println("Пожалуйста, введите целое число. ");
            }
        }
    }
    public void run(){
        boolean runing=true;
        while (runing){
            printMenu();
            int choice =readIntSafely("Ваш выбор: ");
            switch (choice){
                case 1 -> fillManually();
                case 2 -> loadFromTxt();
                case 3 -> loadFromJson();
                case 4 -> fillRandomly();
                case 5 -> printCars();
                case 6,7,8,9,10 -> applyStrategy(choice);
                case 11 -> saveToFile();
                case 12 -> countOccurrences();
                case 13 -> fillCustomCollection();
                case 0 -> {
                    runing = false;
                    System.out.println("Выход из программы.");
                }
                default -> System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
        scanner.close();
    }
    private void fillManually(){
        int count = readIntSafely("Сколько автомобилей ввести? ");
        if (count<=0){
            System.out.println("Количество должно быть положительным. ");
            return;
        }

        List<Car> newCars=new ArrayList<>();
        for (int i=0; i<count;i++){
            System.out.println("\n---- Автомобиль "+ (i+1)+ "---");
            Car car =readFromConsole();
            if (car !=null){
                newCars.add(car);
            }
        }

        if (!newCars.isEmpty()){
            cars=newCars;
            System.out.println("Добавлено автомобилей: "+newCars.size());
        }
    }

    private Car readFromConsole(){
        while (true){
            try {
                System.out.println("Госномер: ");
                String plate = scanner.nextLine().trim();

                System.out.println("Марка: ");
                String brand = scanner.nextLine().trim();

                int price =readIntSafely("Цена (целое число): ");

                return new Car.Builder()
                        .setLicensePlate(plate)
                        .setBrand(brand)
                        .setPrice(price)
                        .build();
            }catch (IllegalArgumentException e){
                System.out.println("Ошибка валидации: "+ e.getMessage());
                System.out.println("Попробуйте снова.");
            }
        }
    }

    private void loadFromTxt(){
        System.out.print("Введите путь к TXT-файлу: ");
        String path=scanner.nextLine().trim();
        List<Car> loaded=fileService.readFromTxt(path);
        if (loaded.isEmpty()){
            System.out.println("Файл пуст или содержит только неккоректные строки.");
        }else {
            cars=loaded;
            System.out.println("Загружено автомобилей: "+ loaded.size());
        }
    }

    private void loadFromJson(){
        System.out.print("Введите путь к JSON-файлу: ");
        String path=scanner.nextLine().trim();
        List<Car> loaded=fileService.readFromJson(path);
        if (loaded.isEmpty()){
            System.out.println("Файл пуст или содержит только неккоректные строки.");
        }else {
            cars=loaded;
            System.out.println("Загружено автомобилей: "+ loaded.size());
        }
    }
    private void fillRandomly() {
        int count = readIntSafely("Сколько автомобилей сгенерировать? ");
        if (count <= 0) {
            System.out.println("Количество должно быть положительным.");
            return;
        }

        cars = Stream.generate(this::generateRandomCar)
                .limit(count)
                .collect(Collectors.toCollection(ArrayList::new));

        System.out.println("Сгенерировано автомобилей: " + cars.size());
    }

    private Car generateRandomCar() {
        String plate = generatePlate();
        String brand = BRANDS[random.nextInt(BRANDS.length)];
        int price = 100_000 + random.nextInt(4_000_000);
        return new Car.Builder()
                .setLicensePlate(plate)
                .setBrand(brand)
                .setPrice(price)
                .build();
    }

    private String generatePlate() {
        StringBuilder sb = new StringBuilder();
        sb.append(LETTERS.charAt(random.nextInt(LETTERS.length())));
        sb.append(String.format("%03d", random.nextInt(1000)));
        sb.append(LETTERS.charAt(random.nextInt(LETTERS.length())));
        sb.append(LETTERS.charAt(random.nextInt(LETTERS.length())));
        sb.append(String.format("%02d", random.nextInt(100)));
        return sb.toString();
    }

    private void printCars() {
        if (cars.isEmpty()) {
            System.out.println("Массив пуст.");
            return;
        }
        System.out.println("\nТекущий массив (" + cars.size() + " элементов):");
        for (int i = 0; i < cars.size(); i++) {
            System.out.println((i + 1) + ". " + cars.get(i));
        }
    }

    private void applyStrategy(int choice) {
        if (cars.isEmpty()) {
            System.out.println("Массив пуст — сортировать нечего.");
            return;
        }
        SortStrategy strategy = strategies.get(choice);
        if (strategy == null) {
            System.out.println("Стратегия не найдена.");
            return;
        }
        strategy.sort(cars);
        System.out.println(strategy.getName() + " — применена.");
        printCars();
    }

    private void saveToFile() {
        if (cars.isEmpty()) {
            System.out.println("Массив пуст — сохранять нечего.");
            return;
        }
        System.out.println("Куда сохранить? 1 - TXT (дописать), 2 - JSON (дописать)");
        int choice = readIntSafely("Ваш выбор: ");
        System.out.print("Путь к файлу: ");
        String path = scanner.nextLine().trim();

        if (choice == 1) {
            fileService.appendToTxt(path, cars);
            System.out.println("Данные добавлены в TXT-файл: " + path);
        } else if (choice == 2) {
            fileService.appendToJson(path, cars);
            System.out.println("Данные добавлены в JSON-файл: " + path);
        } else {
            System.out.println("Неверный выбор. Сохранение отменено.");
        }
    }

    private void countOccurrences() {
        if (cars.isEmpty()) {
            System.out.println("Массив пуст.");
            return;
        }

        System.out.println("Введите данные искомого автомобиля:");
        Car target;
        try {
            System.out.print("Госномер: ");
            String plate = scanner.nextLine().trim();
            System.out.print("Марка: ");
            String brand = scanner.nextLine().trim();
            int price = readIntSafely("Цена: ");

            target = new Car.Builder()
                    .setLicensePlate(plate)
                    .setBrand(brand)
                    .setPrice(price)
                    .build();
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка ввода: " + e.getMessage());
            return;
        }

        int threadCount = readIntSafely("Сколько потоков использовать? ");
        int count = occurrenceCounter.count(cars, target, threadCount);
        System.out.println("Найдено вхождений: " + count);
    }
    private void fillCustomCollection() {
        if (cars.isEmpty()) {
            System.out.println("Массив пуст.");
            return;
        }
        CustomCarList customList = streamFiller.fillList(cars);
        System.out.println("Кастомная коллекция заполнена. Размер: " + customList.size());
        System.out.println(customList);
    }

}
