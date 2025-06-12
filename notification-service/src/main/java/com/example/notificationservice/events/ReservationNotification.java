package com.example.notificationservice.events;

import java.util.Date;

public record ReservationNotification(String name,Long userId, Date date, String licensePlate,String email) {
}
