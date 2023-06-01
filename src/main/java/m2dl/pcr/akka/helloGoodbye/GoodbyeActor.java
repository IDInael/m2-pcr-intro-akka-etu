package m2dl.pcr.akka.helloGoodbye;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class GoodbyeActor extends UntypedActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object msg) throws Exception {
        if (msg instanceof String) {
            log.info(" Message received : "+msg+" ! Goodbye");
        } else {
            unhandled(msg);
        }
    }
}
