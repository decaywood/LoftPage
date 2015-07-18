import com.lmax.disruptor.EventHandler;
import org.decaywood.buffer.MultiSendersBuffer;
import org.decaywood.buffer.handler.RepositoryHandler;
import org.decaywood.entity.KeyEvent;

import java.util.concurrent.atomic.LongAdder;

/**
 * @author: decaywood
 * @date: 2015/7/18 11:32
 */

/**
 * test RepositoryHandler
 *
 */
public class TestSendersBuffer extends MultiSendersBuffer {

    private static LongAdder adder  =  new LongAdder();

    public TestSendersBuffer() {
        super(() -> new EventHandler[]{

                new RepositoryHandler(){
                    @Override
                    protected void execute(KeyEvent event) {
                        adder.increment();
                        System.out.println(adder.intValue());
                    }
                },

                new RepositoryHandler(){
                    @Override
                    protected void execute(KeyEvent event) {
                        adder.increment();
                        System.out.println(adder.intValue());
                    }
                },

                new RepositoryHandler(){
                    @Override
                    protected void execute(KeyEvent event) {
                        adder.increment();
                        System.out.println(adder.intValue());
                    }
                }
        });
    }

    @Override
    protected void buildBuffer() {
        this.ringBuffer = this.generator.initRingBuffer(manager, simpMessagingTemplate, sequencer);
    }



}
