package de.thaso.demo.examples.fundamental.emitter.worker;

import io.smallrye.common.annotation.Identifier;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

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
    KafkaConsumer<String, Integer> getConsumer() {
        return new KafkaConsumer<>(config,
                new StringDeserializer(),
                new IntegerDeserializer());
    }
}
