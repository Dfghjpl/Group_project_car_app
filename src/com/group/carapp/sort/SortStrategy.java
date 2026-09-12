package com.group.carapp.sort;

import java.util.List;

public interface SortStrategy {
    void sort(List<Car> cars);
    String getName();
}