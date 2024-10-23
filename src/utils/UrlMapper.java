package utils;

import java.util.HashMap;

import exception.UrlNotFoundException;

public class UrlMapper {
    private final HashMap<String, UrlMapping> urlMap;

    public UrlMapper() {
        urlMap = new HashMap<>();
    }

    public void addMapping(String url, UrlMapping mapping) {
        urlMap.put(url, mapping);
    }

    public UrlMapping findUrl(String url) throws UrlNotFoundException {
        UrlMapping mapping = urlMap.get(url);
        if (mapping == null) {
            throw new UrlNotFoundException(url + " is not found");
        }
        return mapping;
    }

    public HashMap<String, UrlMapping> getUrlMap() {
        return urlMap;
    }
}

