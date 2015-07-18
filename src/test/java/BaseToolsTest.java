import org.decaywood.utils.CommonUtils;
import org.decaywood.utils.NameDomainMapper;
import org.junit.Test;

/**
 * Created by decaywood on 2015/5/24.
 */
public class BaseToolsTest {

    @Test
    public void enumTest() {
        System.out.println(NameDomainMapper.REQUEST_DATAS.name());
    }

    @Test
    public void hashGenTest() {
        System.out.println(CommonUtils.generateHashCode("localhost", "decaywood"));

    }

}
