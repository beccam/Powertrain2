package services

import java.util.Properties
import javax.inject.{Provider, Inject, Singleton}

import com.datastax.demo.vehicle.VehicleLocation
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.{ProducerConfig, KafkaProducer}
import play.api.{Configuration, Logger}
import play.api.inject.ApplicationLifecycle

case class KafkaConfig(topic:String, producer: KafkaProducer[String,String])

@Singleton
class KafkaProvider @Inject()(appLifecycle: ApplicationLifecycle, config:Configuration) extends Provider[KafkaConfig] {

  lazy val get = {

   // val host: String = config.getString("powertrain.kafkaHost").getOrElse("localhost")
   // val topic: String = config.getString("powertrain.kafkaHost").getOrElse("vehicle_events")

    val host: String = "192.168.99.100:9092"
    val topic: String = "vehicle_events"

    val props = new Properties()
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, host)
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
    //props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, classOf[KryoInternalSerializer].getName)
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
    //props.put("serializer.class","services.KafkaEncoder")

    val producer = new KafkaProducer[String, String](props)

    Logger.info(s"created producer on host $host and producer $producer")

    KafkaConfig(topic, producer)

  }
}

