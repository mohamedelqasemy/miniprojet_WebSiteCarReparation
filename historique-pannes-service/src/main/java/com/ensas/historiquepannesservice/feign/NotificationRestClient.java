package com.ensas.historiquepannesservice.feign;

import com.ensas.historiquepannesservice.config.FeignClientConfig;
import com.ensas.historiquepannesservice.models.ReservationNotification;
import com.ensas.historiquepannesservice.models.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-service",configuration = FeignClientConfig.class)
public interface NotificationRestClient {
    @PostMapping("/publish")
    ReservationNotification send(@RequestBody ReservationNotification notification);
}
