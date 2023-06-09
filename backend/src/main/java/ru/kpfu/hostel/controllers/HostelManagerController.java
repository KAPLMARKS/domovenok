package ru.kpfu.hostel.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.kpfu.hostel.dto.EmployeeDto;
import ru.kpfu.hostel.models.Hostel;
import ru.kpfu.hostel.models.HostelManager;
import ru.kpfu.hostel.models.UserModel;
import ru.kpfu.hostel.models.claim.Claim;
import ru.kpfu.hostel.models.claim.Type;
import ru.kpfu.hostel.repositories.HostelRepository;
import ru.kpfu.hostel.services.ClaimService;
import ru.kpfu.hostel.services.HostelManagerService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/hostelManager")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin
public class HostelManagerController {
    private final HostelManagerService hostelManagerService;
    private final HostelRepository hostelRepository;
    private final ClaimService claimService;

    @Autowired
    public HostelManagerController(HostelManagerService hostelManagerService, HostelRepository hostelRepository, ClaimService claimService) {
        this.hostelManagerService = hostelManagerService;
        this.hostelRepository = hostelRepository;
        this.claimService = claimService;
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('UNIVERSITY_ADMIN')")
    public ResponseEntity<HostelManager> loadHostelHostel(@RequestBody EmployeeDto.Request.EmployeeLoad hostelManager,
                                                          @AuthenticationPrincipal UserModel userModel, @RequestParam Long hostelId) {
        Hostel hostel = hostelRepository.findById(hostelId).orElseThrow();
        return ResponseEntity.ok(hostelManagerService.loadHostelManager(hostelManager, userModel.getUniversity(), hostel));
    }

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('UNIVERSITY_ADMIN')")
    public ResponseEntity<List<HostelManager>> importEmployee(@RequestParam("file") MultipartFile multipartFile,
                                                         @AuthenticationPrincipal UserModel userModel, @RequestParam Long hostelId) {
        try {
            Hostel hostel = hostelRepository.findById(hostelId).orElseThrow();
            return ResponseEntity.ok(hostelManagerService.loadHostelManagerExcel(multipartFile.getInputStream(), userModel.getUniversity(),  hostel));
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/claims/")
    @PreAuthorize("hasAuthority('HOSTEL_MANAGER')")
    public ResponseEntity<List<Claim>> getClaimsByHostel(@AuthenticationPrincipal HostelManager hostelManager) {
        Hostel hostel = hostelManager.getHostel();
        return ResponseEntity.ok(claimService.getClaimsByHostel(hostel));
    }

    @GetMapping("/claims/type")
    @PreAuthorize("hasAuthority('HOSTEL_MANAGER')")
    public ResponseEntity<List<Claim>> getClaims(@AuthenticationPrincipal HostelManager hostelManager, @RequestParam Type type) {
        Hostel hostel = hostelManager.getHostel();
        return ResponseEntity.ok(claimService.getClaimsByTypeAndHostel(type, hostel));
    }
}
