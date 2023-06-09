package ru.kpfu.hostel.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.kpfu.hostel.exception.NotFoundElementException;
import ru.kpfu.hostel.models.Requisition;
import ru.kpfu.hostel.models.VerificationToken;
import ru.kpfu.hostel.repositories.VerificationTokenRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {
    @Value("${verification-token.expiry-time-in-minutes}")
    private int expiryTimeInMinutes;

    private final VerificationTokenRepository verificationTokenRepository;

    public VerificationTokenServiceImpl(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @Override
    public VerificationToken generateToken(Requisition requisition) {
        VerificationToken verificationToken = VerificationToken.builder()
                .token(UUID.randomUUID().toString())
                .expiryData(calculateExpiryDate())
                .requisition(requisition)
                .build();
        verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }

    @Override
    public VerificationToken findVerificationToken(String verifyToken) {
        return verificationTokenRepository
                .findByToken(verifyToken)
                .orElseThrow(NotFoundElementException::new);
    }

    @Override
    public boolean isVerifyToken(VerificationToken verificationToken) {
        return !(new Date().after(verificationToken.getExpiryData()));
    }

    private Date calculateExpiryDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expiryTimeInMinutes);
        System.out.println(calendar);
        return new Date(calendar.getTime().getTime());
    }


}
