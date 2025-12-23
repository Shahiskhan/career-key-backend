package com.crear.web3;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

@Service
public class PdfQrStampService {

    @Autowired
    private QrGeneratorService qrGeneratorService;

    public byte[] addQrToPdf(byte[] inputPdfBytes, String hash) throws Exception {
        // InputStream from input bytes
        ByteArrayInputStream inputStream = new ByteArrayInputStream(inputPdfBytes);
        PdfReader reader = new PdfReader(inputStream);

        // Output stream for new PDF
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfStamper stamper = new PdfStamper(reader, outputStream);

        // Generate QR image
        BufferedImage qrImage = qrGeneratorService.generateQrCode(hash, 120, 120);
        ByteArrayOutputStream qrBaos = new ByteArrayOutputStream();
        ImageIO.write(qrImage, "png", qrBaos);
        Image qr = Image.getInstance(qrBaos.toByteArray());

        // Set QR position and size
        qr.scaleToFit(100, 100);
        qr.setAbsolutePosition(450, 50);

        // Add QR to last page
        PdfContentByte content = stamper.getOverContent(reader.getNumberOfPages());
        content.addImage(qr);

        // Add hash below QR
        ColumnText.showTextAligned(content, Element.ALIGN_CENTER,
                new Phrase(hash, new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD)),
                500, 45, 0);

        // Close streams
        stamper.close();
        reader.close();

        return outputStream.toByteArray();
        // return processed PDF bytes
    }

}
