package test;

import annotation.ControllerRoute;
import annotation.MethodRoute;
import controller.ModelView;
import controller.Request;

@ControllerRoute(url = "/framework")
public class Test {
    @MethodRoute(url = "/test.do")
    public ModelView test(Request request) {
        String message = request.getParameter("message");
        ModelView md = new ModelView("/index.jsp");
        md.addAttribute("message", message);
        return md;
    }
}
