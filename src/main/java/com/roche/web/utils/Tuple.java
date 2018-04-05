package com.roche.web.utils;

/**
 * Simple implementation of two-value tuple/pair object.
 */
public class Tuple<T, U> {

    private final T one;
    private final U two;

    public Tuple(T one, U two) {
        this.one = one;
        this.two = two;
    }

    /**
     * @return value of first object passed to constructor
     */
    public T getOne() {
        return one;
    }

    /**
     * @return value of second object passed to constructor
     */
    public U getTwo() {
        return two;
    }
}

