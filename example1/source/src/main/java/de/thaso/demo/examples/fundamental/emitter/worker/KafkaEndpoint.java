package de.thaso.demo.examples.fundamental.emitter.worker;

import io.quarkus.runtime.ShutdownEvent;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Path("/producer")
@ApplicationScoped
public class KafkaEndpoint {

    public static final String TOPIC = "values";

    @Inject
    KafkaProducer<String, Integer> producer;

    private Random random = new Random();

    public void terminate(@Observes ShutdownEvent ev) {
        producer.close();
    }

    @GET
    public int initValue()
            throws InterruptedException, ExecutionException, TimeoutException {
        int value = random.nextInt(100);
        System.out.printf("produce value : %d\n", value);
        producer.send(new ProducerRecord<>(TOPIC, "value", value))
                .get(5, TimeUnit.SECONDS);
        return value;
    }
}
