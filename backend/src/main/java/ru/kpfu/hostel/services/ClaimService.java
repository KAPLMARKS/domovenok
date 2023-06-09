package ru.kpfu.hostel.services;

import ru.kpfu.hostel.dto.ClaimDto;
import ru.kpfu.hostel.models.Hostel;
import ru.kpfu.hostel.models.University;
import ru.kpfu.hostel.models.UserModel;
import ru.kpfu.hostel.models.claim.AnswerType;
import ru.kpfu.hostel.models.claim.Claim;
import ru.kpfu.hostel.models.claim.Type;

import java.util.List;

public interface ClaimService {
    Claim createClaim(ClaimDto.Response.CreateClaim createClaim, UserModel userModel, Long placeId);

//    Claim createClaimWithDoc(String message, Type type, String docUrl, UserModel userModel);

    List<Claim> getClaims(UserModel userModel);

    List<Claim> getClaimsByStudent(UserModel userModel);

    List<Claim> geyClaimsByAnswerType(AnswerType answerType, UserModel userModel);

    Claim successClaim(Long claimId);

    Claim declinedClaim(Long claimId);

    List<Claim> getClaimsByHostel(Hostel hostel);

    List<Claim> getClaimsByTypeAndHostel(Type type, Hostel hostel);

    List<Claim> getClaimsByStudGorodok(AnswerType answerType, UserModel userModel);

    Claim editClaim(Long claimId);
}
