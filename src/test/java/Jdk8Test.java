import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 * Created by decaywood on 2015/5/23.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-config.xml")
public class Jdk8Test {

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


    @Autowired
    private HttpServletRequest httpServletRequest;
    @Test
    public void autoWiredTest() {
        System.out.println(httpServletRequest);
    }

}
