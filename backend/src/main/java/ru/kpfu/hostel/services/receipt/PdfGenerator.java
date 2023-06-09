package ru.kpfu.hostel.services.receipt;

import com.google.zxing.WriterException;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kpfu.hostel.models.Receipt;
import ru.kpfu.hostel.models.student.Student;
import ru.kpfu.hostel.repositories.ReceiptRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PdfGenerator {
    private final ReceiptRepository receiptRepository;
    private final QRCodeGenerator qrCodeGenerator;

    public Receipt createReceipt(Student student) {

        Receipt receipt = new Receipt();
        receipt.setPrice(800);
        receipt.setPaymentDate(LocalDate.of(2023, 12, 12));
        try {
            receipt.setQrCode(qrCodeGenerator.generateQRCode(student.getFirstName() + student.getLastName() + student.getMiddleName(), 350, 350));
        } catch (IOException | WriterException e) {
            e.printStackTrace();
        }
        receipt.setStudent(student);

        return receiptRepository.save(receipt);
    }

    public byte[] generateReceiptPdf(Receipt receipt) throws DocumentException, IOException {
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        Student student =  receipt.getStudent();
        Image image = Image.getInstance(receipt.getQrCode());

        BaseFont font = BaseFont.createFont("C:\\Users\\Murot\\Desktop\\hostel\\backend\\src\\main\\resources\\Arial.ttf", "Cp1251", BaseFont.EMBEDDED);
        Font unicodeFont = new Font(font, 12, Font.NORMAL, BaseColor.BLACK);

        Paragraph p = new Paragraph("ФИО: " + student.getLastName() + " " + student.getFirstName() + " " + student.getMiddleName(), unicodeFont);
        document.add(p);

        p = new Paragraph("Место: " + student.getPlace(), unicodeFont);
        document.add(p);

        p = new Paragraph("Сумма: " + receipt.getPrice() , unicodeFont);
        document.add(p);

        p = new Paragraph("Оплата до: " + receipt.getPaymentDate(), unicodeFont);
        document.add(p);

        document.add(image);

        document.close();
        return outputStream.toByteArray();
    }
}
