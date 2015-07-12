import org.decaywood.dataAccess.UserDao;
import org.decaywood.entity.User;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 * Created by decaywood on 2015/5/23.
 */

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:spring/spring-config.xml")
public class Jdk8Test {
    @Resource(name = "userDataAccess")
    UserDao userDao;
    @Test
    public void mysqlTest() {
        User user = new User().setUserLoginName("decawgawgaywood")
                .setUserEmail("decaawgywood@qq.com")
                .setUserPassword("123456")
                .setUserID("awrawr")
                .setUserName("decaywood")
                .setUserIPAddress("localhost")
                .setUserStatus("go")
                .setUserRole("");
        userDao.saveUser(user);
    }

    @Test
    public void notNullTest() {
        String notNull = "a value";
        Optional<String> optional = Optional.ofNullable(notNull);
        String result = optional.orElseGet(() -> "new one");
        assertEquals("a value", result);
    }

    @Test
    public void nullTest() {
        String nullValue = null;
        Optional<String> nullOptional = Optional.ofNullable(nullValue);
        String result = nullOptional.orElseGet(() -> "new one");
        assertEquals("new one", result);
    }

    @Test
    public void reduceTest() {
        String[] values = new String[]{"1","2","3","4","5","6"};
        String result = Arrays.stream(values).reduce("", (acc, element) -> acc = acc + "," + element);
        assertEquals(",1,2,3,4,5,6", result);
    }

    @Test
    public void collectTest() {
        String[] values = new String[]{"1","2","3","4","5","6"};
        String result = Arrays.stream(values).collect(Collectors.joining(","));
        assertEquals("1,2,3,4,5,6", result);
    }


    @Test
    public void reduceTest2() {
        String[] seeds = new String[]{"hash1", "hash2", "hash3"};
        long hash = Arrays.stream(seeds).map(x -> (long)x.hashCode()).peek(System.out::println)
                .reduce(0L, (first, second) -> first * 31 + second);
        System.out.println(hash);
    }


}
