package com.ensas.historiquepannesservice.models;

import java.util.Date;

public record ReservationNotification(String name, String user, Long userId, Date date, String licensePlate, String email) {
}
