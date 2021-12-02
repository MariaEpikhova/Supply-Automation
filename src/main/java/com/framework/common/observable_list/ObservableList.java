package com.framework.common.observable_list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ObservableList<T> extends ArrayList<T> {
    private final List<ListChangeListener<T>> listeners = new ArrayList<>();

    public void addListener(ListChangeListener<T> listener) {
        listeners.add(listener);
    }

    @Override
    public boolean add(T e) {
        boolean addResult = super.add(e);
        listeners.forEach(listener -> listener.onChange(this));
        return addResult;
    }

    @Override
    public void clear() {
        super.clear();
        listeners.forEach(listener -> listener.onChange(this));
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean addResult = super.addAll(c);
        listeners.forEach(listener -> listener.onChange(this));

        return addResult;
    }

    @Override
    public T set(int index, T element) {
        T set = super.set(index, element);
        listeners.forEach(listener -> listener.onChange(this));

        return set;
    }

    @Override
    public boolean remove(Object o) {
        boolean removeResult = super.remove(o);
        listeners.forEach(listener -> listener.onChange(this));

        return removeResult;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean removeAllResiult = super.removeAll(c);
        listeners.forEach(listener -> listener.onChange(this));

        return removeAllResiult;
    }
}
