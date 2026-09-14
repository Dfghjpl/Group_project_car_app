package collection;

import sort.Car;
import java.util.List;

public class CarStreamFiller {

    public CustomCarList fillList(List<Car> cars) {
        validateCars(cars);

        CustomCarList result = new CustomCarList();

        cars.stream()
                .forEach(result::add);

        return result;
    }

    public CustomCarSet fillSet(List<Car> cars) {
        validateCars(cars);

        CustomCarSet result = new CustomCarSet();

        cars.stream()
                .forEach(result::add);

        return result;
    }

    public CustomCarQueue fillQueue(List<Car> cars) {
        validateCars(cars);

        CustomCarQueue result = new CustomCarQueue();

        cars.stream()
                .forEach(result::add);

        return result;
    }

    private void validateCars(List<Car> cars) {
        if (cars == null) {
            throw new IllegalArgumentException(
                    "Список автомобилей не может быть null"
            );
        }

        for (Car car : cars) {
            if (car == null) {
                throw new IllegalArgumentException(
                        "В списке есть пустой элемент"
                );
            }
        }
    }
}
