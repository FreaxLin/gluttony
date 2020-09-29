package top.interc.crawler.frontier;

public interface DocIDService {

    public int getDocId(String url);

    public int createDocId(String url);

    public void addUrlAndDocId(String url, int docId);

    public int getDocCount();

    public boolean containId(String url);
}
