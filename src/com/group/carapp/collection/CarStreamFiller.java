package com.group.carapp.collection;

import com.group.carapp.model.Car;
import java.util.List;

/*
 * CarStreamFiller берёт список автомобилей List<Car> 
 * и перекладывает эти автомобили в разные коллекции — CustomCarList, CustomCarSet или CustomCarQueue
 */

public class CarStreamFiller { //заполняет разные коллекции автомобилями

    public CustomCarList fillList(List<Car> cars) {
        validateCars(cars); //проверка на null

        CustomCarList result = new CustomCarList(); //создаётся пустой список

        cars.stream() //stream() позволяет обрабатывать элементы коллекции последовательно
                .forEach(result::add); //берет каждую машину из cars и добавляет её в result

        return result; //возвращает заполненный CustomCarList
    }

    public CustomCarSet fillSet(List<Car> cars) { //Set — не хранит дубликаты
        validateCars(cars); //проверка на null

        CustomCarSet result = new CustomCarSet(); //создается пустой Set

        cars.stream() //stream() позволяет обрабатывать элементы коллекции последовательно
                .forEach(result::add); //берет каждую машину из cars и добавляет её в result

        return result; //возвращает заполненный CustomCarSet
    }

    public CustomCarQueue fillQueue(List<Car> cars) { //Queue - очередь
        validateCars(cars); //проверка на null

        CustomCarQueue result = new CustomCarQueue(); //создается пустая очередь

        cars.stream() //stream() позволяет обрабатывать элементы коллекции последовательно
                .forEach(result::add); //берет каждую машину из cars и добавляет её в result

        return result; //возвращает заполненный CustomCarQueue
    }

    //Проверки на null
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
