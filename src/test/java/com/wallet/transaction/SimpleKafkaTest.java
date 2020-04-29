package com.wallet.transaction;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;


@EmbeddedKafka(partitions = 1, topics = {"testTopic"})
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SimpleKafkaTest {

    private static final String TEST_TOPIC = "testTopic";

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    private static final String TOPIC = "spring";

//    public static EmbeddedKafkaRule kafka = new EmbeddedKafkaRule(1,false, TOPIC);

    @Test
    public void testReceivingKafkaEvents() {
        Map<String, Object> configs = new HashMap<>(KafkaTestUtils.producerProps(embeddedKafkaBroker));
        Consumer<Integer, String> consumer = configureConsumer();
        Producer<Integer, String> producer = configureProducer();

        producer.send(new ProducerRecord<>(TEST_TOPIC, 123, "my-test-value"));

        ConsumerRecord<Integer, String> singleRecord = KafkaTestUtils.getSingleRecord(consumer, TEST_TOPIC);
        assertThat(singleRecord).isNotNull();
        assertThat(singleRecord.key()).isEqualTo(123);
        assertThat(singleRecord.value()).isEqualTo("my-test-value");

        consumer.close();
        producer.close();


//        try(Consumer<Integer, String> consumer2 = new KafkaConsumer<>(
//
//                KafkaTestUtils.consumerProps("spring_group", "true", embeddedKafkaBroker))) {
//
//            KafkaTemplate<Integer, String> template = new KafkaTemplate<>(
//                    new DefaultKafkaProducerFactory<>(KafkaTestUtils.producerProps(embeddedKafkaBroker)));
//
//            consumer2.subscribe(Collections.singleton(TOPIC));
//
//            template.send(TOPIC, "one");
//            template.send(TOPIC, "two");
//
//            ConsumerRecords<Integer, String> records = KafkaTestUtils.getRecords(consumer2);
//
//            assertThat(records).are(value("one"));
//        }

    }

    private Consumer<Integer, String> configureConsumer() {
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("testGroup", "true", embeddedKafkaBroker);
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        Consumer<Integer, String> consumer = new DefaultKafkaConsumerFactory<Integer, String>(consumerProps)
                .createConsumer();
        consumer.subscribe(Collections.singleton(TEST_TOPIC));
        return consumer;
    }

    private Producer<Integer, String> configureProducer() {
        Map<String, Object> producerProps = new HashMap<>(KafkaTestUtils.producerProps(embeddedKafkaBroker));
        return new DefaultKafkaProducerFactory<Integer, String>(producerProps).createProducer();
    }


}
