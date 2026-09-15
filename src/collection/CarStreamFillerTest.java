package collection;

import java.util.ArrayList;
import java.util.List;
import model.Car;

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
                new Car.Builder()
                        .setLicensePlate("A111")
                        .setBrand("BMW")
                        .setPrice(100)
                        .build()
        );

        cars.add(
                new Car.Builder()
                        .setLicensePlate("B222")
                        .setBrand("Audi")
                        .setPrice(200)
                        .build()
        );

        cars.add(
                new Car.Builder()
                        .setLicensePlate("C333")
                        .setBrand("Lada")
                        .setPrice(300)
                        .build()
        );

        cars.add(
                new Car.Builder()
                        .setLicensePlate("D444")
                        .setBrand("Ford")
                        .setPrice(400)
                        .build()
        );

        return cars;
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

        System.out.println(
                "CustomCarList: OK"
        );

        System.out.println(result);
    }

    private static void testCustomSet() {

        List<Car> cars = createCars();

        // Добавляем дубликат.
        cars.add(
                new Car.Builder()
                        .setLicensePlate("A111")
                        .setBrand("BMW")
                        .setPrice(100)
                        .build()
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

        System.out.println(
                "CustomCarSet: OK"
        );

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

        if (!first.equals(cars.get(0))) {
            throw new AssertionError(
                    "Queue нарушает порядок FIFO"
            );
        }

        Car removed = result.poll();

        if (!removed.equals(cars.get(0))) {
            throw new AssertionError(
                    "poll() должен вернуть первый элемент"
            );
        }

        System.out.println(
                "CustomCarQueue: OK"
        );

        System.out.println(result);
    }
}
