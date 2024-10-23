package controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;

import exception.UrlNotFoundException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.UrlMapper;
import utils.UrlMapping;
import utils.Utility;

public class FrontServlet extends HttpServlet {
    public void addToContext(String key, Object object) {
        ServletContext cnt = getServletContext();
        cnt.setAttribute(key, object);
    }

    public UrlMapper getUrlMapper() {
        ServletContext cnt = getServletContext();
        UrlMapper urlMapper = (UrlMapper) cnt.getAttribute("urlMapper");
        return urlMapper;
    }

    @Override
    public void init() throws ServletException {
        UrlMapper urlMapper = new UrlMapper();
        ArrayList<Class<?>> controllers = this.discoverControllers();
        for (Class<?> controller : controllers) {
            new Controller(controller).registerRoutes(urlMapper);
        }
        addToContext("urlMapper", urlMapper);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            this.processRequest(req, res);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException | SecurityException | UrlNotFoundException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Class<?>> discoverControllers() {
        ArrayList<Class<?>> controllers = new ArrayList<>();
        String realPath = getServletContext().getRealPath("./WEB-INF/classes");
        File rootDir = new File(realPath);
        Utility.extractAllClass(controllers, rootDir, "classes", ".class");
        return controllers;
    }

    public String getUrl(HttpServletRequest req) throws UrlNotFoundException {
        String url = req.getRequestURI();
        String root = req.getContextPath();
        root += "/api";
        return Utility.retrieveUrlFromRawUrl(url, root);
    }

    public void addAttribute(HttpServletRequest req, ModelView md) {
        for (Map.Entry<String, Object> entry : md.getAttributes().entrySet()) {
            String key = entry.getKey();
            Object obj = entry.getValue();
            req.setAttribute(key, obj);
        }
    }

    public void dispatch(HttpServletRequest req, HttpServletResponse res, ModelView md)
            throws ServletException, IOException {
        if (md.getPath() != null) {
            RequestDispatcher disp = req.getRequestDispatcher(md.getPath());
            res.setContentType("text/html");
            disp.forward(req, res);
        }
    }

    public void processRequest(HttpServletRequest req, HttpServletResponse res)
            throws IOException, UrlNotFoundException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException,
            ServletException {
            String url = this.getUrl(req);
            UrlMapper urlMapper = getUrlMapper();
            UrlMapping mapping = urlMapper.findUrl(url);
            Object controllerInstance = mapping.getControllerClass().getDeclaredConstructor().newInstance();
            ModelView modelView = (ModelView) mapping.getMethod().invoke(controllerInstance, new Request(req));
            this.addAttribute(req, modelView);
            dispatch(req, res, modelView);
    }
}
