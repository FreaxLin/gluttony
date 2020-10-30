package top.interc.crawler.monitor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class RequestHandler extends AbstractHandler {

    private Map<String, Handler> handlerMap = new HashMap<>();

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
        throws IOException {

        PrintWriter out = response.getWriter();
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        Handler handler = handlerMap.get(target);
        if (handler == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.flush();
        }else{
            handler.execute(request, out);
        }


    }

    public void addHander(String path, Handler handler){
        this.handlerMap.put(path, handler);
    }
}
