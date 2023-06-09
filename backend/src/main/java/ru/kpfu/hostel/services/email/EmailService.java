package ru.kpfu.hostel.services.email;

import jakarta.validation.constraints.Email;
import ru.kpfu.hostel.models.Hostel;
import ru.kpfu.hostel.models.Place;
import ru.kpfu.hostel.models.Room;

public interface EmailService {
    void sendVerifyEmail(@Email String email, String token);

    void sendDataAfterVerifyEmail(String email, String password, String key);

    void sendSuccessNotification(String email);

    void sendDeclinedNotification(String email);

    void sendNotification(String email, Place place);
}