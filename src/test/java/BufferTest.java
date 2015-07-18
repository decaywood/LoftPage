import org.decaywood.buffer.MessageBuffer;
import org.decaywood.entity.KeyEvent;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAdder;

/**
 * Created by Administrator on 2015/6/19.
 */


//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:spring/spring-config.xml")
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
//                        buffer.publishEvent(new KeyEvent(String.valueOf(i)));
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


    @Test
    public void bufferedTest2() throws InterruptedException {

        Executor excutor = Executors.newCachedThreadPool();
        TestSendersBuffer buffer = new TestSendersBuffer();
        buffer.buildBuffer();
        LongAdder l = new LongAdder();
        LongAdder compare = new LongAdder();
        l.decrement();
        for (int i = 0; i < 10; i++) {

            Runnable r = () -> {

                for (int j = 0; j < 200; j++) {

                    int num = 0;
                    synchronized (l) {
                        l.increment();
                        num = l.intValue();
                    }
                    KeyEvent keyEvent = new KeyEvent();
                    keyEvent.setUserID("decaywood");
                    keyEvent.setIPAddress("localhost");
                    keyEvent.setCurrentNum(String.valueOf(num));
                    keyEvent.setExpectNum(String.valueOf(num + 1));

                    buffer.publishKeyEvent(keyEvent);


                }


            };

            excutor.execute(r);
        }

        Thread.sleep(5000);
    }

}
