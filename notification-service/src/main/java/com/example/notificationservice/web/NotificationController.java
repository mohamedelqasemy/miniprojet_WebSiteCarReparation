package com.example.notificationservice.web;

import com.example.notificationservice.entities.Notification;
import com.example.notificationservice.events.ReservationNotification;
import com.example.notificationservice.repositories.NotificationRepository;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Windowed;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyWindowStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

@RestController
public class NotificationController {
    private StreamBridge streamBridge;
    @Autowired
    private InteractiveQueryService interactiveQueryService;

    @Autowired
    private NotificationRepository repo;

    public NotificationController(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @PostMapping("/publish")
    public ReservationNotification send(@RequestBody ReservationNotification notification) {
        streamBridge.send("T1", notification);
        return notification;
    }


    @GetMapping("/notifications")
    public List<Notification> getNotifications(@RequestParam Long userId) {
        return repo.findByUserId(userId);
    }

    @GetMapping(path = "/analytics",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Map<String, Long>> analytics(){
        return Flux.interval(Duration.ofSeconds(1))
                .map(sequence->{
                    Map<String,Long> stringLongMap=new HashMap<>();
                    ReadOnlyWindowStore<String, Long> windowStore = interactiveQueryService.getQueryableStore("count-store", QueryableStoreTypes.windowStore());
                    Instant now=Instant.now();
                    Instant from=now.minusMillis(5000);
                    KeyValueIterator<Windowed<String>, Long> fetchAll = windowStore.fetchAll(from, now);
                    //WindowStoreIterator<Long> fetchAll = windowStore.fetch(page, from, now);
                    while (fetchAll.hasNext()){
                        KeyValue<Windowed<String>, Long> next = fetchAll.next();
                        stringLongMap.put(next.key.key(),next.value);
                    }
                    return stringLongMap;
                });
    }



}
