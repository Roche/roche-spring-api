package com.roche.web.api;

/**
 * @author Mateusz Filipowicz (mateusz.filipowicz@roche.com).
 */
public interface EmptyPathNamingProvider {

    String getNameForHandlerType(Class handler);

}
