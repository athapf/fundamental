package de.thaso.demo.examples.fundamental.emitter.worker;

import io.smallrye.common.annotation.Identifier;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.util.Map;

@ApplicationScoped
public class KafkaProviders {

    @Inject
    @Identifier("default-kafka-broker")
    Map<String, Object> config;

    @Produces
    KafkaProducer<String, Integer> getProducer() {
        return new KafkaProducer<>(config,
                new StringSerializer(),
                new IntegerSerializer());
    }
}
