package ru.kpfu.hostel.services;

import ru.kpfu.hostel.models.Requisition;
import ru.kpfu.hostel.models.VerificationToken;

public interface VerificationTokenService {
    VerificationToken generateToken(Requisition requisition);

    VerificationToken findVerificationToken(String verifyToken);

    boolean isVerifyToken(VerificationToken verificationToken);
}
