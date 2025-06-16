package com.ensas.commandservice.models;

import java.util.Date;
import java.util.List;

public record EquipmentOrderNotification(
        Long orderId,
        Long userId,
        String email,
        Date orderDate,
        List<String> equipmentNames
) {}