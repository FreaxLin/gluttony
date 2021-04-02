package mapdb;

import org.jetbrains.annotations.NotNull;
import org.mapdb.DataInput2;
import org.mapdb.DataOutput2;
import org.mapdb.Serializer;

import java.io.IOException;

/**
 * @author ：linweisen
 * @date ：Created in 2021/4/2 10:09
 * @description：${description}
 * @modified By：
 * @version: 1.0
 */
public class TestBeanSerializer implements Serializer<TestBean> {

    @Override
    public void serialize(@NotNull DataOutput2 dataOutput2, @NotNull TestBean testBean) throws IOException {
        dataOutput2.writeInt(testBean.getId());
    }

    @Override
    public TestBean deserialize(@NotNull DataInput2 dataInput2, int i) throws IOException {
        TestBean testBean = new TestBean();
        if (i != 0){
            testBean.setId(dataInput2.readInt());
        }
        return testBean;
    }
}
