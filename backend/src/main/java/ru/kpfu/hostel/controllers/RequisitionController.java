package ru.kpfu.hostel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.hostel.dto.RequisitionDto;
import ru.kpfu.hostel.services.RequisitionService;

@RestController
@RequestMapping("/api/v1/requisition")
@CrossOrigin
public class RequisitionController {

    private final RequisitionService requisitionService;

    @Autowired
    public RequisitionController(RequisitionService requisitionService) {
        this.requisitionService = requisitionService;
    }

    @PostMapping
    public ResponseEntity createRequisition(
            @RequestBody RequisitionDto.Request.CreateRequisition requisition
    ) {
        requisitionService.createRequisition(requisition);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify/{token}")
    public ResponseEntity verifyRequisition(
            @PathVariable("token") String verifyToken
    ) {
        requisitionService.confirmRequisition(verifyToken);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
