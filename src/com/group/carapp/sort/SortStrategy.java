package com.group.carapp.sort;

import com.group.carapp.model.Car;
import java.util.List;

public interface SortStrategy {
    void sort(List<Car> cars);
    String getName();
}
