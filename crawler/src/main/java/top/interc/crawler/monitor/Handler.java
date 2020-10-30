package top.interc.crawler.monitor;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;

public interface Handler {

    public void execute(HttpServletRequest request, PrintWriter out);
}
