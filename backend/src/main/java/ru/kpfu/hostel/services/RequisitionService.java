package ru.kpfu.hostel.services;

import ru.kpfu.hostel.dto.RequisitionDto;

public interface RequisitionService {

    void createRequisition(RequisitionDto.Request.CreateRequisition requisitionDto);

    void confirmRequisition(String verifyToken);
}
