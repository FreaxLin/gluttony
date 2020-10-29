package seg;

import org.ansj.app.keyword.KeyWordComputer;
import org.ansj.app.keyword.Keyword;
import org.ansj.domain.Result;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.junit.Test;

import java.util.Collection;

public class AnsjTest {

    @Test
    public void test(){
        String str = "Re:可可爱爱的陈姐";
        Result result = ToAnalysis.parse(str);
        System.out.println(result);
        KeyWordComputer kwc = new KeyWordComputer(5);
        Collection<Keyword> keywords = kwc.computeArticleTfidf(str);
        System.out.println(keywords);
    }
}
