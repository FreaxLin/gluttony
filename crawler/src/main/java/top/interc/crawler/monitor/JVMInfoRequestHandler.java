package top.interc.crawler.monitor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.util.List;

public class JVMInfoRequestHandler implements Handler {
    @Override
    public void execute(HttpServletRequest request, PrintWriter out) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode conf = mapper.createObjectNode();

        OperatingSystemMXBean osb = ManagementFactory.getOperatingSystemMXBean();
        conf.put("system_type", osb.getName());
        conf.put("system_version", osb.getVersion());
        conf.put("cpu", osb.getAvailableProcessors());
        conf.put("load_average", osb.getSystemLoadAverage());


        MemoryMXBean mxb = ManagementFactory.getMemoryMXBean();
        conf.put("head_max", mxb.getHeapMemoryUsage().getMax()/ 1024 / 1024 + "MB");
        conf.put("head_init", mxb.getHeapMemoryUsage().getInit()/ 1024 / 1024 + "MB");
        conf.put("head_used", mxb.getHeapMemoryUsage().getUsed()/ 1024 / 1024 + "MB");
        conf.put("head_committed", mxb.getHeapMemoryUsage().getCommitted()/ 1024 / 1024 + "MB");

        List<GarbageCollectorMXBean> gcList = ManagementFactory.getGarbageCollectorMXBeans();
        long gcCount = 0;
        long gcTime = 0;
        for (GarbageCollectorMXBean gcBean : gcList){
            gcCount += gcBean.getCollectionCount();
            gcTime += gcBean.getCollectionTime();
        }
        conf.put("gc_count", gcCount);
        conf.put("gc_time", gcTime);
        out.println(conf);
        out.flush();
    }
}
