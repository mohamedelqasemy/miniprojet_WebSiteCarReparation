package com.example.notificationservice.handlers;
import com.example.notificationservice.entities.Notification;
import com.example.notificationservice.events.ReservationNotification;
import com.example.notificationservice.repositories.NotificationRepository;
import com.example.notificationservice.services.EmailService;
import org.apache.kafka.common.metrics.stats.Avg;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Component
public class ReservationHandler {
    @Autowired
    NotificationRepository repo;
    @Autowired
    private EmailService emailService;
//    @Bean
//    public Consumer<ReservationNotification> pageEventConsumer(){
//        return (input)->{
//            System.out.println("************");
//            System.out.println(input.toString());
//            System.out.println("************");
//        };
//    }
    @Bean
    public Consumer<ReservationNotification> pageEventConsumer(){
        return input -> {
            emailService.send("otmanekarym69@gmail.com", "Car Repair Update", input.name() + ": " + input.car() + " has been repaired.");
            repo.save(new Notification(null, input.name(), input.user(), input.date(), input.car(), false));
        };
    }
//    @Bean
//    public Supplier<ReservationNotification> pageEventSupplier(){
//        return ()->{
//            return new ReservationNotification(
//                    Math.random()>0.5?"P1":"P2",
//                    Math.random()>0.5?"U1":"U2",
//                    new Date(),
//                    "bmw"
//            );
//        };
//    }

    @Bean
    public Function<KStream<String, ReservationNotification>, KStream<String, Long>> kStream(){
        return (stream)->
                stream
                        //.filter((k,v)->v.duration()>100)
                        .map((k,v)->new KeyValue<>(v.name(), 0L))
                        .groupByKey(Grouped.with(Serdes.String(), Serdes.Long()))
                        .windowedBy(TimeWindows.of(Duration.ofSeconds(5)))
                        //.aggregate(()->0.0, (k,v,total)->total+v, Materialized.as("total-store"))
                        .count(Materialized.as("count-store"))
                        .toStream()
                        .map((k,v)->new KeyValue<>(k.key(), v))
                ;

    }
}
