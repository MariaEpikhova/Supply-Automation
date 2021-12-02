package com.framework.common.observable_list;

@FunctionalInterface
public interface ListChangeListener<T> {
    void onChange(ObservableList<T> changedList);
}
