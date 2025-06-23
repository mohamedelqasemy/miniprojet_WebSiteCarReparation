package com.ensas.commandservice.feign;

import com.ensas.commandservice.config.FeignClientConfig;
import com.ensas.commandservice.models.EquipmentOrderNotification;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-service",configuration = FeignClientConfig.class)
public interface NotificationRestClient {
    @PostMapping("/publish/commande")
    EquipmentOrderNotification send(@RequestBody EquipmentOrderNotification notification);
}