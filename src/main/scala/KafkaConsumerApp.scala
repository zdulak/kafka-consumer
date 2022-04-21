import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}
import java.time.Duration
import java.util
import java.util.Properties
import scala.jdk.CollectionConverters._

object KafkaConsumerApp extends App {
  val props = new Properties()
  props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
  props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
  props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("group.id", "my-group")
  //  props.put("enable.auto.commit", "true")
  //  props.put("auto.commit.interval.ms", "1000")

  val consumer = new KafkaConsumer[String, String](props)
  consumer.subscribe(List("output").asJava)
  while (true) {
    val records = consumer.poll(Duration.ofMillis(100)).asScala
    for (record <- records) {
      println(record.offset, record.key, record.value)
    }
  }
}
