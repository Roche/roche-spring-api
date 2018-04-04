package com.roche.web.utils;

/**
 * @author Mateusz Filipowicz (mateusz.filipowicz@roche.com)
 */
public class Tuple<T, U> {

    private final T one;
    private final U two;

    public Tuple(T one, U two) {
        this.one = one;
        this.two = two;
    }

    public T getOne() {
        return one;
    }

    public U getTwo() {
        return two;
    }
}

