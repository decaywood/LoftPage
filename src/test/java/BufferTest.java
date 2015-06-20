import org.decaywood.buffer.MainBuffer;
import org.decaywood.buffer.MessageBuffer;
import org.decaywood.entity.KeyEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2015/6/19.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-config.xml")
public class BufferTest {


    @Resource
    private MessageBuffer buffer;


    @Test
    public void bufferTest() throws InterruptedException {

        long time = System.currentTimeMillis();
        for (int i = 0; i < 20; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 1000; i++) {
//                        System.out.println("================ " + Thread.currentThread().getId() + "==================");
                        buffer.publishEvent(new KeyEvent(String.valueOf(i)));
                    }

                }
            });
            thread.start();
            thread.join();
        }
        System.out.println(System.currentTimeMillis() - time);
        System.out.println("==================== TEST FINISHED ===================" + this.buffer.getPoolSize());
        Thread.sleep(5000);
    }
}
