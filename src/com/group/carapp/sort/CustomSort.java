package com.group.carapp.sort;

import com.group.carapp.model.Car;
import java.util.List;
/*
 * Сортировка автомобилей выполняется по трём полям:
 * 1. Марка — по алфавиту
 * 2. Если марки одинаковые — цена от меньшей к большей
 * 3. Если марка и цена одинаковые — госномер по алфавиту
 * 
 * Алгоритм комбинированный:
 * Большой список → Merge Sort (Рекурсивная сортировка)
 * Маленькие части → Insertion Sort (Сортировка вставками)
 */
public class CustomSort implements SortStrategy { //является одной из стратегий сортировки (SortStrategy.java)

    //Если часть списка содержит 10 или меньше автомобилей, используется сортировка вставками.
    private static final int INSERTION_SORT_THRESHOLD = 10; 

    @Override
    public void sort(List<Car> cars) { //метод который запускает всю сортровку
        validateCars(cars); //проверка списка на null

        if (cars.size() < 2) { //Если автомобилей 0 или 1, сортировать нечего.
            return;
        }

        mergeSort(cars, 0, cars.size() - 1); //запуск сортировки
    }

    @Override
    public String getName() { //Возвращает название стратегии сортировки
        return "Custom Sort";
    }

    /*
    * Метод получает:
    * 1. Список автомобилей;
    * 2. Левую границу;
    * 3. Правую границу.
    * Делит большой список на маленькие части → сортирует части → соединяет их обратно.
    */
    private void mergeSort(
            List<Car> cars,
            int left,
            int right) {

        if (left >= right) { //Проверка границ. Если в части списка остался один элемент, его уже не нужно сортировать.
            return;
        }

        // Для небольших частей используется сортировка вставками
        if (right - left + 1 <= INSERTION_SORT_THRESHOLD) {
            insertionSort(cars, left, right);
            return;
        }

        int middle = left + (right - left) / 2; //деление списка пополам

        /*
        * Рекурсивная сортировка:
        * 1. Первая строка сортирует левую половину;
        * 2. Вторая — правую.
        */
        mergeSort(cars, left, middle);
        mergeSort(cars, middle + 1, right);

        merge(cars, left, middle, right); //соединение частей
    }

    private void insertionSort( //сортировка вставками
            List<Car> cars,
            int left,
            int right) {

        for (int i = left + 1; i <= right; i++) {

            Car current = cars.get(i);
            int j = i - 1;

            while (j >= left
                    && compareCars(cars.get(j), current) > 0) {

                cars.set(j + 1, cars.get(j));
                j--;
            }

            cars.set(j + 1, current);
        }
    }

    private void merge(
            List<Car> cars,
            int left,
            int middle,
            int right) {

        int leftSize = middle - left + 1;
        int rightSize = right - middle;
        
        /*
        * Создаются два временных массива:
        * левая часть → leftArray
        * правая часть → rightArray
        * Потом данные из исходного списка копируются в них
        * Нужно, чтобы во время слияния можно было сравнивать элементы двух отсортированных частей.
        */
        Car[] leftArray = new Car[leftSize];
        Car[] rightArray = new Car[rightSize];

        for (int i = 0; i < leftSize; i++) {
            leftArray[i] = cars.get(left + i);
        }

        for (int i = 0; i < rightSize; i++) {
            rightArray[i] = cars.get(middle + 1 + i);
        }

        int i = 0;
        int j = 0;
        int k = left;

        while (i < leftSize && j < rightSize) {

            if (compareCars(
                    leftArray[i],
                    rightArray[j]) <= 0) {

                cars.set(k, leftArray[i]);
                i++;

            } else {

                cars.set(k, rightArray[j]);
                j++;
            }

            k++;
        }

        while (i < leftSize) {
            cars.set(k, leftArray[i]);
            i++;
            k++;
        }

        while (j < rightSize) {
            cars.set(k, rightArray[j]);
            j++;
            k++;
        }
    }

    /**
     * Сравнение автомобилей:
     * 1. По марке
     * 2. По цене
     * 3. По государственному номеру
     */
    private int compareCars(
            Car first,
            Car second) {

        int byBrand =
                first.getBrand()
                        .compareTo(second.getBrand());

        if (byBrand != 0) {
            return byBrand;
        }

        int byPrice =
                Integer.compare(
                        first.getPrice(),
                        second.getPrice());

        if (byPrice != 0) {
            return byPrice;
        }

        return first.getLicensePlate()
                .compareTo(second.getLicensePlate());
    }

    private void validateCars(List<Car> cars) { //проверка на null

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
