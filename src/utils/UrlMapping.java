package utils;

import java.lang.reflect.Method;

public class UrlMapping {
    private final Class<?> controllerClass;
    private final Method method;

    public UrlMapping(Class<?> controllerClass, Method method) {
        this.controllerClass = controllerClass;
        this.method = method;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getMethod() {
        return method;
    }
}
