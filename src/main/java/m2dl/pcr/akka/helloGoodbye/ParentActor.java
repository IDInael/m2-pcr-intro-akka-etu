package m2dl.pcr.akka.helloGoodbye;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Procedure;
import m2dl.pcr.akka.helloworld2.NameActor;

public class ParentActor extends UntypedActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private ActorRef childHello;
    private ActorRef childGoodbye;

    public  ParentActor(){
        childHello = getContext().actorOf(Props.create(HelloActor.class), "hello-actor");
        childGoodbye = getContext().actorOf(Props.create(GoodbyeActor.class), "goodbye-actor");
    }
    Procedure<Object> hello = new Procedure<Object>() {
        public void apply(Object msg) throws Exception {
            if (msg instanceof String) {
                log.info("send to hello " + msg + "!");
                childHello.tell(msg,getSelf());
                getContext().become(goodbye,false);
            } else {
                unhandled(msg);
            }
        }
    };

    Procedure<Object> goodbye = new Procedure<Object>() {
        public void apply(Object msg) throws Exception {
            if (msg instanceof String) {
                log.info("send to Good bye : " + msg + "!");
                childGoodbye.tell(msg,getSelf());
                getContext().unbecome();
            } else {
                unhandled(msg);
            }
        }
    };

    @Override
    public void onReceive(Object msg) throws Exception {
        hello.apply(msg);
    }
}
