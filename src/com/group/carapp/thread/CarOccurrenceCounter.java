package com.group.carapp.thread;

import com.group.carapp.model.Car;
import java.util.List;

/*
 * CarOccurrenceCounter считает количество вхождений заданного автомобиля в списке с использованием нескольких потоков. 
 * Список делится на несколько частей, каждая часть обрабатывается отдельным потоком. 
 * Каждый поток самостоятельно считает найденные элементы и записывает результат в отдельную ячейку массива. 
 * После завершения всех потоков результаты складываются, и метод возвращает общее количество.
 */

public class CarOccurrenceCounter {

    /*
    * Метод получает три параметра:
    * cars — список автомобилей;
    * target — автомобиль, который нужно найти;
    * threadCount — сколько потоков использовать.
    */
    public int count(
            List<Car> cars,
            Car target,
            int threadCount) {

        validateInput(cars, target, threadCount); //проверка на null

        if (cars.isEmpty()) { //если список пустой, искать нечего
            return 0;
        }
        
        //Определяет количество потоков
        //максимум создаётся столько потоков, сколько элементов в списке
        int actualThreadCount = Math.min(threadCount, cars.size()); 

        //Создаёт массив потоков
        Thread[] threads = new Thread[actualThreadCount];

        //Создаёт массив результатов
        //Каждый поток будет записывать сюда свой результат
        int[] results = new int[actualThreadCount];

        //Делит список на части
        int partSize = cars.size() / actualThreadCount;
        int remainder = cars.size() % actualThreadCount;

        //start показывает, с какого элемента списка начинает работать текущий поток
        int start = 0;

        for (int i = 0; i < actualThreadCount; i++) {
            int end = start + partSize; //end показывает, где заканчивается часть работы потока

            if (i < remainder) {
                end++;
            }

            final int threadIndex = i;
            final int from = start;
            final int to = end;

            //создаёт отдельный поток
            threads[i] = new Thread(() -> {
                int count = 0; //Создаётся счётчик найденных автомобилей

                for (int j = from; j < to; j++) { //поток проходит только по своей части списка
                    if (target.equals(cars.get(j))) { //cравнивается искомый автомобиль target с текущим автомобилем
                        count++;
                    }
                }

                results[threadIndex] = count; //каждый поток записывает свой результат в отдельную ячейку
            });

            threads[i].start(); //запуск потока

            start = end; //после создания одного потока программа запоминает, где закончилась его часть
        }

        //join() используется для того, чтобы главный поток дождался завершения всех рабочих потоков перед подсчётом общего результата
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) { //обработка ошибки при работе с потоками
                Thread.currentThread().interrupt();

                throw new IllegalStateException(
                        "Поток был прерван", e);
            }
        }

        //Складываются результаты
        int total = 0;

        for (int result : results) {
            total += result;
        }

        return total; //возвращает ответ
    }

    //Проверка входных данных
    private void validateInput(
            List<Car> cars,
            Car target,
            int threadCount) {

        if (cars == null) { //нельзя передавать null
            throw new IllegalArgumentException(
                    "Список автомобилей не может быть null");
        }

        if (target == null) { //нельзя искать null
            throw new IllegalArgumentException(
                    "Искомый автомобиль не может быть null");
        }

        if (threadCount <= 0) { //количество потоков должно быть больше 0
            throw new IllegalArgumentException(
                    "Количество потоков должно быть больше нуля");
        }

        for (Car car : cars) { //внутри списка тоже не должно быть null
            if (car == null) {
                throw new IllegalArgumentException(
                        "В списке есть пустой элемент");
            }
        }
    }
}
