package thread;

import sort.Car;
import java.util.List;

public class CarOccurrenceCounter {

    public int count(
            List<Car> cars,
            Car target,
            int threadCount) {

        validateInput(cars, target, threadCount);

        if (cars.isEmpty()) {
            return 0;
        }

        int actualThreadCount = Math.min(threadCount, cars.size());

        Thread[] threads = new Thread[actualThreadCount];
        int[] results = new int[actualThreadCount];

        int partSize = cars.size() / actualThreadCount;
        int remainder = cars.size() % actualThreadCount;

        int start = 0;

        for (int i = 0; i < actualThreadCount; i++) {
            int end = start + partSize;

            if (i < remainder) {
                end++;
            }

            final int threadIndex = i;
            final int from = start;
            final int to = end;

            threads[i] = new Thread(() -> {
                int count = 0;

                for (int j = from; j < to; j++) {
                    if (target.equals(cars.get(j))) {
                        count++;
                    }
                }

                results[threadIndex] = count;
            });

            threads[i].start();

            start = end;
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();

                throw new IllegalStateException(
                        "Поток был прерван", e);
            }
        }

        int total = 0;

        for (int result : results) {
            total += result;
        }

        return total;
    }

    private void validateInput(
            List<Car> cars,
            Car target,
            int threadCount) {

        if (cars == null) {
            throw new IllegalArgumentException(
                    "Список автомобилей не может быть null");
        }

        if (target == null) {
            throw new IllegalArgumentException(
                    "Искомый автомобиль не может быть null");
        }

        if (threadCount <= 0) {
            throw new IllegalArgumentException(
                    "Количество потоков должно быть больше нуля");
        }

        for (Car car : cars) {
            if (car == null) {
                throw new IllegalArgumentException(
                        "В списке есть пустой элемент");
            }
        }
    }
}
