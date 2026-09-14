package collection;

import sort.Car;

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
                new Car("A111", "BMW", 100)
        );

        cars.add(
                new Car("B222", "Audi", 200)
        );

        cars.add(
                new Car("C333", "Lada", 300)
        );

        cars.add(
                new Car("D444", "Ford", 400)
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
                new Car("A111", "BMW", 100)
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