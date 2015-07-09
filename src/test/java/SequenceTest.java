import org.decaywood.entity.KeyEvent;
import org.decaywood.utils.cache.KeyEventSequencer;
import org.junit.Test;

import java.util.ArrayList;

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
}
