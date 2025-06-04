package com.example.notificationservice.events;

import java.util.Date;

public record ReservationNotification(String name, String user, Date date, String car) {
}
