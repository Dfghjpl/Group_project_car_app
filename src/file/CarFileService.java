
package file;

import model.Car;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

//Этот класс отвечает за работу с файлами автомобилей.
//Здесь реализованы чтение и запись данных в форматах TXT и JSON.
//При чтении автомобилей они создаются через Builder,
//поэтому данные из файлов также проходят валидацию.
//Также есть методы добавления новых данных в существующие файлы,

public class CarFileService {

    private static final String DELIMITER = ";";

    public List<Car> readFromTxt(String fileName) {
        List<Car> cars = new ArrayList<>();

        // Читаем файл построчно, потому что каждая строка
        // содержит данные одного автомобиля
        try (BufferedReader reader =
                     new BufferedReader(new FileReader(fileName))) {

            String line;

            while ((line = reader.readLine()) != null) {
                try {
                    Car car = parseTxtLine(line);
                    cars.add(car);
                } catch (IllegalArgumentException exception) {
                    // Ошибка в одной строке не должна останавливать
                    // загрузку остальных корректных автомобилей
                    System.out.println(
                            "Некорректная строка пропущена: " + line
                    );
                    System.out.println(
                            "Причина: " + exception.getMessage()
                    );
                }
            }

        } catch (IOException exception) {
            System.out.println(
                    "Ошибка при чтении файла: " + exception.getMessage()
            );
        }

        return cars;
    }

    private Car parseTxtLine(String line) {
        String[] values = line.split(DELIMITER);

        // В TXT-формате одна строка должна содержать
        // номер автомобиля, марку и цену
        if (values.length != 3) {
            throw new IllegalArgumentException(
                    "В строке должно быть три значения"
            );
        }

        String licensePlate = values[0].trim();
        String brand = values[1].trim();

        double price;

        try {
            price = Double.parseDouble(values[2].trim());
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(
                    "Цена должна быть числом"
            );
        }

        // Используем Builder, чтобы данные, полученные из файла,
        // прошли ту же проверку, что и при обычном создании Car
        return new Car.Builder()
                .setLicensePlate(licensePlate)
                .setBrand(brand)
                .setPrice(price)
                .build();
    }

    public void writeToTxt(String fileName, List<Car> cars) {
        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(fileName))) {

            for (Car car : cars) {
                writer.write(carToTxtLine(car));
                writer.newLine();
            }

        } catch (IOException exception) {
            System.out.println(
                    "Ошибка при записи файла: " + exception.getMessage()
            );
        }
    }

    private String carToTxtLine(Car car) {
        return car.getLicensePlate()
                + DELIMITER
                + car.getBrand()
                + DELIMITER
                + car.getPrice();
    }

    public void appendToTxt(String fileName, List<Car> cars) {
        // Второй параметр true у FileWriter включает режим добавления
        // Благодаря этому старые данные в файле не удаляются
        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(fileName, true))) {

            for (Car car : cars) {
                writer.write(carToTxtLine(car));
                writer.newLine();
            }

        } catch (IOException exception) {
            System.out.println(
                    "Ошибка при добавлении данных в файл: "
                            + exception.getMessage()
            );
        }
    }

    public List<Car> readFromJson(String fileName) {
        List<Car> cars = new ArrayList<>();

        try {
            String content = Files.readString(Path.of(fileName)).trim();

            // Ожидаем, что JSON-файл содержит массив автомобилей
            if (!content.startsWith("[") || !content.endsWith("]")) {
                throw new IllegalArgumentException(
                        "JSON должен содержать массив автомобилей"
                );
            }

            // Убираем внешние квадратные скобки,
            // чтобы дальше работать отдельно с объектами автомобилей
            content = content.substring(1, content.length() - 1).trim();

            if (content.isEmpty()) {
                return cars;
            }

            String[] objects = content.split("\\},\\s*\\{");

            for (String object : objects) {
                try {
                    String normalizedObject = object
                            .replace("{", "")
                            .replace("}", "")
                            .trim();

                    Car car = parseJsonObject(normalizedObject);
                    cars.add(car);

                } catch (IllegalArgumentException exception) {
                    // Как и для TXT, один неправильный объект
                    // не мешает загрузить остальные автомобили
                    System.out.println(
                            "Некорректный объект JSON пропущен"
                    );
                    System.out.println(
                            "Причина: " + exception.getMessage()
                    );
                }
            }

        } catch (IOException exception) {
            System.out.println(
                    "Ошибка при чтении JSON-файла: "
                            + exception.getMessage()
            );
        } catch (IllegalArgumentException exception) {
            System.out.println(
                    "Ошибка формата JSON: "
                            + exception.getMessage()
            );
        }

        return cars;
    }

    private Car parseJsonObject(String object) {
        String[] fields = object.split(",");

        if (fields.length != 3) {
            throw new IllegalArgumentException(
                    "В объекте JSON должно быть три поля"
            );
        }

        String licensePlate = null;
        String brand = null;
        Double price = null;

        // Поля читаем по их названиям, поэтому их порядок
        // внутри JSON-объекта для нас не имеет значения
        for (String field : fields) {
            String[] pair = field.split(":", 2);

            if (pair.length != 2) {
                throw new IllegalArgumentException(
                        "Некорректный формат поля JSON"
                );
            }

            String key = pair[0]
                    .trim()
                    .replace("\"", "");

            String value = pair[1]
                    .trim()
                    .replace("\"", "");

            switch (key) {
                case "licensePlate":
                    licensePlate = value;
                    break;

                case "brand":
                    brand = value;
                    break;

                case "price":
                    try {
                        price = Double.parseDouble(value);
                    } catch (NumberFormatException exception) {
                        throw new IllegalArgumentException(
                                "Цена должна быть числом"
                        );
                    }
                    break;

                default:
                    throw new IllegalArgumentException(
                            "Неизвестное поле: " + key
                    );
            }
        }

        if (price == null) {
            throw new IllegalArgumentException(
                    "Поле price отсутствует"
            );
        }

        // После разбора JSON снова используем Builder,
        // чтобы проверить полученные значения перед созданием Car
        return new Car.Builder()
                .setLicensePlate(licensePlate)
                .setBrand(brand)
                .setPrice(price)
                .build();
    }

    public void writeToJson(String fileName, List<Car> cars) {
        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(fileName))) {

            writer.write("[");
            writer.newLine();

            for (int i = 0; i < cars.size(); i++) {
                Car car = cars.get(i);

                writer.write("  {");
                writer.newLine();

                writer.write(
                        "    \"licensePlate\": \""
                                + car.getLicensePlate()
                                + "\","
                );
                writer.newLine();

                writer.write(
                        "    \"brand\": \""
                                + car.getBrand()
                                + "\","
                );
                writer.newLine();

                writer.write(
                        "    \"price\": "
                                + car.getPrice()
                );
                writer.newLine();

                writer.write("  }");


                if (i < cars.size() - 1) {
                    writer.write(",");
                }

                writer.newLine();
            }

            writer.write("]");

        } catch (IOException exception) {
            System.out.println(
                    "Ошибка при записи JSON-файла: "
                            + exception.getMessage()
            );
        }
    }

    public void appendToJson(String fileName, List<Car> cars) {
        // В JSON нельзя просто дописать объект в конец файла,
        // потому что тогда нарушится структура массива
        // Поэтому читаем старые данные, добавляем новые
        // и записываем получившийся список обратно
        List<Car> existingCars = readFromJson(fileName);

        existingCars.addAll(cars);

        writeToJson(fileName, existingCars);
    }
}