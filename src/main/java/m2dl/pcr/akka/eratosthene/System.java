package m2dl.pcr.akka.eratosthene;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class System {
    public static final Logger log = LoggerFactory.getLogger(m2dl.pcr.akka.eratosthene.System.class);

    public static void main(String... args) throws Exception {

        final ActorSystem actorSystem = ActorSystem.create("actor-system");

        Thread.sleep(5000);
        int max = 20;
        final ActorRef actorRef = actorSystem.actorOf(Props.create(EratostheneActor.class, 2, max), "eratosthene-actor");

        for (int i = 3; i <= max; i++) {
            actorRef.tell(String.valueOf(i), null);
        }

        Thread.sleep(1000);

        log.debug("Actor System Shutdown Starting...");

        actorSystem.terminate();
    }
}
