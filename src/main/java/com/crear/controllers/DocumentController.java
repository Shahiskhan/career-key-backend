package com.crear.controllers;

import com.crear.web3.PdfQrStampService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    @Autowired
    private com.crear.services.basic.DocumentService documentService;

    @Autowired
    private PdfQrStampService pdfQrStampService;

    @PostMapping("/stamp")
    public ResponseEntity<?> uploadAndStampPdf(
            @RequestParam("file") MultipartFile file,
            HttpServletResponse response) {

        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("❌ No file uploaded!");
            }

            System.out.println("✅ Received file: " + file.getOriginalFilename());

            String logoPath = "src/main/resources/static/stamp.png";
            float x = 450;
            float y = 50;

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=stamped.pdf");

            documentService.addStampToUploadedPdf(file, response.getOutputStream(), logoPath, x, y);

            return new ResponseEntity<>(HttpStatus.OK); // streaming response (PDF)
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("❌ Error stamping PDF: " + e.getMessage());
        }
    }

    // 🔹 Generate new sample PDF with stamp (optional)
    @GetMapping("/pdf")
    public void generatePdf(HttpServletResponse response) {
        try {
            String logoPath = "src/main/resources/static/stamp.png";
            float x = 450;
            float y = 50;

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=document.pdf");

            documentService.generatePdfWithStamp(response.getOutputStream(), logoPath, x, y);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 Generate Word (DOCX) with stamp (optional)
    @GetMapping("/word")
    public void generateWord(HttpServletResponse response) {
        try {
            String logoPath = "src/main/resources/static/stamp.png";
            int width = 100;
            int height = 100;

            response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            response.setHeader("Content-Disposition", "attachment; filename=document.docx");

            documentService.generateDocxWithStamp(response.getOutputStream(), logoPath, width, height);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/add-qr")
    public ResponseEntity<byte[]> addQrToPdf(
            @RequestParam("file") MultipartFile file,
            @RequestParam("hash") String hash) {
        try {

            byte[] updatedPdf = pdfQrStampService.addQrToPdf(file.getBytes(), hash);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=pdf_with_qr.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(updatedPdf);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

}
