package com.crear.services.basic;

import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public class DocumentService {

    // 🔹 Add stamp (logo) to an uploaded existing PDF with overlay effect
    public void addStampToUploadedPdf(MultipartFile uploadedFile, OutputStream outputStream,
            String logoPath, float x, float y) throws Exception {

        try (InputStream inputStream = uploadedFile.getInputStream()) {
            PdfReader reader = new PdfReader(inputStream);
            PdfStamper stamper = new PdfStamper(reader, outputStream);

            Image stamp = Image.getInstance(logoPath);
            stamp.scaleToFit(100, 100);
            stamp.setAbsolutePosition(x, y);

            // ✅ Overlay transparency setup
            PdfGState gState = new PdfGState();
            gState.setFillOpacity(0.4f); // 0.0 = invisible, 1.0 = solid (adjust as needed)

            int totalPages = reader.getNumberOfPages();
            for (int i = 1; i <= totalPages; i++) {
                PdfContentByte content = stamper.getOverContent(i); // use getUnderContent() if you want it behind text
                content.setGState(gState);
                content.addImage(stamp);
            }

            stamper.close();
            reader.close();
        }
    }

    // 🔹 Generate a simple PDF with transparent stamp (for testing)
    public void generatePdfWithStamp(OutputStream outputStream, String logoPath, float x, float y) throws Exception {
        com.itextpdf.text.Document document = new com.itextpdf.text.Document();
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
        document.open();

        document.add(new com.itextpdf.text.Paragraph("This is a PDF with a semi-transparent (overlay) digital stamp."));

        Image stamp = Image.getInstance(logoPath);
        stamp.scaleToFit(100, 100);
        stamp.setAbsolutePosition(x, y);

        PdfContentByte canvas = writer.getDirectContent();
        PdfGState gState = new PdfGState();
        gState.setFillOpacity(0.4f); // 🔹 make it semi-transparent
        canvas.setGState(gState);
        canvas.addImage(stamp);

        document.close();
    }

    // 🔹 Generate a DOCX with stamp (same as before)
    public void generateDocxWithStamp(OutputStream outputStream, String logoPath, int width, int height)
            throws Exception {
        XWPFDocument document = new XWPFDocument();

        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();

        try (FileInputStream is = new FileInputStream(logoPath)) {
            run.addPicture(is, XWPFDocument.PICTURE_TYPE_PNG, "logo.png", Units.toEMU(width), Units.toEMU(height));
        }

        run.addBreak();
        run.setText("This is a Word document with a digital stamp.");

        document.write(outputStream);
        document.close();
    }
}