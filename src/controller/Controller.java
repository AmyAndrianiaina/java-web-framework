package controller;

import annotation.ControllerRoute;
import annotation.MethodRoute;
import utils.UrlMapper;
import utils.UrlMapping;

import java.lang.reflect.Method;

public class Controller {

    private final Class<?> controllerClass;

    public Controller(Class<?> controllerClass) {
        this.controllerClass = controllerClass;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public void registerRoutes(UrlMapper urlMapper) {
        String baseUrl = controllerClass.getAnnotation(ControllerRoute.class).url();
        Method[] methods = controllerClass.getDeclaredMethods();
        
        for (Method method : methods) {
            MethodRoute methodRoute = method.getAnnotation(MethodRoute.class);
            if (methodRoute != null) {
                String url = baseUrl + methodRoute.url();
                urlMapper.addMapping(url, new UrlMapping(controllerClass, method));
            }
        }
    }
}
