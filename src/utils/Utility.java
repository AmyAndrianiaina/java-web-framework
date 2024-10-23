package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;

import annotation.ControllerRoute;
import exception.UrlNotFoundException;

public class Utility {
    private Utility() {
    }

    public static void addClass(ArrayList<Class<?>> ans, File file, String root, String extension) {
        try {
            Class<?> cls = Class.forName(Utility.extractPath(file.getPath(), root, extension));
            ControllerRoute controller = cls.getAnnotation(ControllerRoute.class);
            if (controller != null) {
                ans.add(cls);
            }
        } catch (ClassNotFoundException | NoClassDefFoundError e) {
        }
    }

    public static void extractAllClass(ArrayList<Class<?>> ans, File file, String root, String extension) {
        Utility.addClass(ans, file, root, extension);
        if (file.isDirectory()) {
            for (var f : file.listFiles()) {
                Utility.extractAllClass(ans, f, root, extension);
            }
        }
    }

    public static int extractLastIndexR(String str, String original) {
        int last = original.lastIndexOf(str);
        last += str.length() + 1;
        last = (last > original.length() ? original.length() : last);
        return last;
    }

    public static int extractLastIndexL(String str, String original) {
        int last = original.lastIndexOf(str);
        return last >= 0 ? last : original.length();
    }

    public static String extractPath(String path, String root, String extension) {
        String ans = extractPath(path, root);
        int last = extractLastIndexL(extension, ans);
        return ans.substring(0, last);
    }

    public static String extractPath(String path, String root) {
        int last = extractLastIndexR(root, path);
        String ans = path.substring(last, path.length());
        String regex = Matcher.quoteReplacement("\\");
        ans = ans.replaceAll(regex, ".");
        return ans;
    }

    public static String retrieveUrlFromRawUrl(String url, String root) throws UrlNotFoundException {
        String[] splt = url.split(root);
        if (splt.length > 0) {
            return splt[splt.length - 1];
        }
        throw new UrlNotFoundException();
    }
}
