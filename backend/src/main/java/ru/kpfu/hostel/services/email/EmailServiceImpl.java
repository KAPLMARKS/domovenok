package ru.kpfu.hostel.services.email;

import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.kpfu.hostel.exception.EmailSendingException;
import ru.kpfu.hostel.models.Hostel;
import ru.kpfu.hostel.models.Place;
import ru.kpfu.hostel.models.Room;
import ru.kpfu.hostel.repositories.RoomRepository;


@Service
public class EmailServiceImpl implements EmailService {
    @Value("${requisition.verify_link}")
    private String linkForVerifyEmail;
    private final String formatVerifyEmail = "Перейдите по ссылке для подтверждения почты и завершения регистрации.Никому" +
            " не показывайте ссылку!\n %s";

    private final String formatSuccessVerify = "Вы успешно создали аккаунт и зарегистрировали вашу организацию!\n" +
            "Данные для входа:почта-%s, пароль-%s. Ваш ключ для api %s.";

    private final String formatSuccessNotification = "Ваша заявка одобрена";

    private final String formatDeclinedNotification = "Ваша заявка отклонена";

    private final String formatNotification = "Место %s в комнате %s общежития %s освободилось";

    private final JavaMailSender javaMailSender;

    @Autowired
    EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Value("${spring.mail.username}")
    private String sender;

    private boolean sendSimpleMail(EmailDetails details) {
        try {
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());

            javaMailSender.send(mailMessage);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void sendVerifyEmail(@Email String email, String token) {
        String verificationLink = linkForVerifyEmail + token;
        EmailDetails emailDetails = new EmailDetails(
                email,
                String.format(formatVerifyEmail, verificationLink),
                "Подтвердите почту");
        if (!sendSimpleMail(emailDetails)) {
            throw new EmailSendingException();
        }
    }

    @Override
    public void sendDataAfterVerifyEmail(String email, String password, String key) {
        EmailDetails emailDetails = new EmailDetails(
                email,
                String.format(formatSuccessVerify, email, password, key),
                "Успешная регистрация!");
        if (!sendSimpleMail(emailDetails)) {
            throw new EmailSendingException();
        }
    }

    @Override
    public void sendSuccessNotification(String email) {
        EmailDetails emailDetails = new EmailDetails(
                email,
                formatSuccessNotification,
                "Уведомление");
        if (!sendSimpleMail(emailDetails)) {
            throw new EmailSendingException();
        }
    }

    @Override
    public void sendDeclinedNotification(String email) {
        EmailDetails emailDetails = new EmailDetails(
                email,
                formatDeclinedNotification,
                "Уведомление");
        if (!sendSimpleMail(emailDetails)) {
            throw new EmailSendingException();
        }
    }

    @Override
    public void sendNotification(String email, Place place) {
        EmailDetails emailDetails = new EmailDetails(
                email,
                String.format(formatNotification, place, place.getRoom(), place.getRoom().getHostel()),
                "Уведомление");
        if (!sendSimpleMail(emailDetails)) {
            throw new EmailSendingException();
        }
    }
}
