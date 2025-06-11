package com.example.notificationservice.events;

import java.util.Date;

public record ReservationNotification(String name, String user,Long userId, Date date, String licensePlate,String email) {
}
