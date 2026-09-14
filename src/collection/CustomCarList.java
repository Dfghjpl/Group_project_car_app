package collection;

import sort.Car;

public class CustomCarList {

    private Car[] elements; //массив в котором хранятся автомобили
    private int size; //количество автомобилей

    public CustomCarList() {
        elements = new Car[10]; //массив Car - 10 ячеек
        size = 0; //список пустой
    }

    public void add(Car car) { //добавляет автомобиль в конец списка
        if (car == null) { //проверка, что автомобиль не равен null
            throw new IllegalArgumentException(
                    "Автомобиль не может быть null"
            );
        }

        ensureCapacity(); //проверка, что в массиве осталось место

        elements[size] = car; //помещает автомобиль в первую свободную ячейку
        size++;
    }

    public Car get(int index) { //возвращает автомобиль по указанному индексу
        checkIndex(index); //проверяет, является ли индекс допустимым

        return elements[index]; //возвращает автомобиль из массива по указанному индексу
    }

    public int size() { //возвращает количество автомобилей в спике
        return size;
    }

    public boolean isEmpty() { //проверяет является ли список пустым
        return size == 0;
    }

    private void ensureCapacity() { //проверяет осталось ли место в массиве
        if (size < elements.length) {
            return;
        }

        Car[] newElements = //если массив заполнен, создает массив в 2 раза больше старого
                new Car[elements.length * 2];

        for (int i = 0; i < elements.length; i++) {
            newElements[i] = elements[i];
        }

        elements = newElements; //копирует элементы из старого массива в новый
    }

    private void checkIndex(int index) { //проверяет корректность индекса
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Некорректный индекс: " + index
            );
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("["); //ставит квадратную скобку в начале строки

        for (int i = 0; i < size; i++) { //перебирает добавленные элементы
            result.append(elements[i]);

            if (i < size - 1) {
                result.append(", "); //добавляет разделитель между автомобилями
            }
        }

        result.append("]"); //после всех автомобилей добавляет квадратную скобку

        return result.toString(); //возвращает строку
    }
}