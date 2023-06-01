package m2dl.pcr.akka.eratosthene;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Procedure;

public class EratostheneActor extends UntypedActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private ActorRef next;
    private int value, max;
    Procedure<Object> filter = new Procedure<Object>() {
        public void apply(Object msg) throws Exception {
            if (msg instanceof String) {
                var v = Integer.parseInt((String) msg);

                if (v % value != 0) {
                    next.tell(msg, getSelf());
                }
            } else {
                unhandled(msg);
            }
        }
    };
    Procedure<Object> init = new Procedure<Object>() {
        public void apply(Object msg) throws Exception {
            if (msg instanceof String) {
                var v = Integer.parseInt((String) msg);
                if (v < max && v % value != 0) {
//                    log.info(msg + " ");
                    next = getContext().actorOf(Props.create(EratostheneActor.class, v, max), "next-actor");
                    getContext().become(filter, false);
                }
            } else {
                unhandled(msg);
            }
        }
    };

    public EratostheneActor(int value, int max) {
        this.value = value;
        this.max = max;

        log.info(value + " ");
    }

    @Override
    public void onReceive(Object msg) throws Exception {
        init.apply(msg);
    }
}
