package controller;

import java.util.HashMap;

public class ModelView {
    String path;
    HashMap<String, Object> attribut;

    public ModelView(String path) {
        this.path = path;
        this.attribut = new HashMap<String, Object>();
    }

    public void addAttribute(String key, Object value) {
        this.getAttributes().put(key, value);
    }

    public HashMap<String, Object> getAttributes() {
        return attribut;
    }

    public String getPath() {
        return path;
    }
}
