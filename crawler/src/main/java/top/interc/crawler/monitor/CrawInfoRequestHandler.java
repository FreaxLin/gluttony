package top.interc.crawler.monitor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import top.interc.crawler.storage.DocIDService;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;

public class CrawInfoRequestHandler implements Handler {

    private DocIDService docIDServer;

    public CrawInfoRequestHandler(DocIDService docIDServer) {
        this.docIDServer = docIDServer;
    }

    @Override
    public void execute(HttpServletRequest request, PrintWriter out) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode conf = mapper.createObjectNode();
        conf.put("current_docId", docIDServer.getDocCount());
        conf.put("doc_count", docIDServer.getDocCount());
        out.println(conf);
        out.flush();
    }
}
