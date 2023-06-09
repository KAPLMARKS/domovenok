package ru.kpfu.hostel.controllers;

import com.itextpdf.text.DocumentException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.hostel.models.Receipt;
import ru.kpfu.hostel.models.student.Student;
import ru.kpfu.hostel.services.receipt.PdfGenerator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/receipt")
@Validated
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin
public class ReceiptController {

    private final PdfGenerator pdfGenerator;

    @GetMapping(value = "/download", produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> downloadPdf(@AuthenticationPrincipal Student student) throws IOException {
        Receipt receipt = pdfGenerator.createReceipt(student);
        byte[] pdf = new byte[0];
        try {
            pdf = pdfGenerator.generateReceiptPdf(receipt);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "file.pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        ResponseEntity<byte[]> response = new ResponseEntity<>(pdf, headers, HttpStatus.OK);
        return response;
    }
}
