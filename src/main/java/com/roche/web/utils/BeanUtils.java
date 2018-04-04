package com.roche.web.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import java.util.Optional;

/**
 * @author Mateusz Filipowicz (mateusz.filipowicz@roche.com).
 */
public class BeanUtils {

    private BeanUtils() {
    }

    public static <T> Optional<T> getBean(ApplicationContext context, Class<? extends T> clss) {
        T bean = null;
        try {
            bean = context.getBean(clss);
        } catch (BeansException e) {
            //discard
        }

        return Optional.ofNullable(bean);
    }
}
