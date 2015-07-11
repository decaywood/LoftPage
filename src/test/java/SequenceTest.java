import org.decaywood.entity.KeyEvent;
import org.decaywood.utils.cache.KeyEventSequencer;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author: decaywood
 * @date: 2015/7/9 19:15
 */

public class SequenceTest {

    @Test
    public void sequenceTest() {
        KeyEventSequencer sequencer = new KeyEventSequencer();
        ArrayList<KeyEvent> a = new ArrayList<>();
//        a.add(new KeyEvent("0", "1"));
//        a.add(new KeyEvent("2", "3"));
//        a.add(new KeyEvent("3", "4"));
//        a.add(new KeyEvent("5", "6"));
//        a.add(new KeyEvent("7", "8"));
//        a.add(new KeyEvent("4", "5"));
//        a.add(new KeyEvent("1", "2"));
//        a.add(new KeyEvent("8", "9"));
//        a.add(new KeyEvent("10", "11"));
//        a.add(new KeyEvent("9", "10"));
//        a.add(new KeyEvent("6", "7"));
        a.forEach(e -> {
            sequencer.processKeyEvent(e , ed -> {
                System.out.println(ed.getCurrentNum());
            });
        });

    }

    @Test
    public void sequenceTest2() throws InterruptedException {

        Executor excutor = Executors.newCachedThreadPool();
        KeyEventSequencer sequencer = new KeyEventSequencer();
        LongAdder l = new LongAdder();
        LongAdder compare = new LongAdder();
        l.decrement();
        System.out.println(l.intValue());
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
                    sequencer.processKeyEvent(keyEvent, keyEvent1 -> {
                        int currentNum = keyEvent1.getCurrentNum();
                        int c = compare.intValue();
                        compare.increment();
                        if(currentNum == c)  System.out.println("result pass!");
                        else try {
                            throw new Exception("wrong!");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    });


                }


            };

            excutor.execute(r);
        }

        Thread.sleep(5000);

    }
}
