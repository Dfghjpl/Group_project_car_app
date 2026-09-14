package model;

// Класс описывает автомобиль, который используется в программе
// Объект Car создается через Builder и проходит проверку данных

public class Car {
    private final String licensePlate;
    private final String brand;
    private final double price;

    // Конструктор закрыт, чтобы объект Car создавался только через Builder
    // Значения полей берем из Builder после того, как они прошли валидацию
    private Car(Builder builder) {
        this.licensePlate = builder.licensePlate;
        this.brand = builder.brand;
        this.price = builder.price;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getBrand() {
        return brand;
    }

    public double getPrice() {
        return price;
    }

    // Переопределяем toString, чтобы объект Car было удобно выводить в консоль
    @Override
    public String toString() {
        return "Car{" +
                "licensePlate='" + licensePlate + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                '}';
    }

    // Builder позволяет создавать объект Car поэтапно
    // и перед созданием проверить все переданные значения
    public static class Builder {

        private String licensePlate;
        private String brand;
        private double price;

        // Возвращаем сам Builder, чтобы можно было использовать цепочку вызовов
        public Builder setLicensePlate(String licensePlate) {
            this.licensePlate = licensePlate;
            return this;
        }

        public Builder setBrand(String brand) {
            this.brand = brand;
            return this;
        }

        public Builder setPrice(double price) {
            this.price = price;
            return this;
        }

        // Перед созданием Car проверяем все введенные значения
        // Если хотя бы одно поле некорректно, объект создан не будет
        public Car build() {
            validateLicensePlate();
            validateBrand();
            validatePrice();

            return new Car(this);
        }

        // Проверяем, что номер был передан и не состоит только из пробелов
        private void validateLicensePlate() {
            if (licensePlate == null || licensePlate.isBlank()) {
                throw new IllegalArgumentException(
                        "Номер автомобиля не может быть пустым"
                );
            }
        }

        // Марка обязательна для создания автомобиля
        private void validateBrand() {
            if (brand == null || brand.isBlank()) {
                throw new IllegalArgumentException(
                        "Марка автомобиля не может быть пустой"
                );
            }
        }

        // Цена может быть равна нулю, но не может быть отрицательной
        private void validatePrice() {
            if (price < 0) {
                throw new IllegalArgumentException(
                        "Цена автомобиля не может быть отрицательной"
                );
            }
        }
    }
}