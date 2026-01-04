package com.crear.services.basic;

import com.crear.entities.DegreeRequest;
import com.crear.enums.DocumentStatus;
import com.crear.repositories.DegreeRequestRepository;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.*;
import java.util.UUID;

@Service
public class Dc {

  private final DegreeRequestRepository degreeRequestRepository;

  // 🔒 CONSTANTS (filhal hard-coded)
  private static final float STAMP_X = 450;
  private static final float STAMP_Y = 50;
  private static final float STAMP_OPACITY = 0.4f;

  private static final String STAMP_IMAGE_PATH = "src/main/resources/static/stamp.png";

  private static final String STAMPED_DIR = "src/main/resources/static/upload/stampedFiles";

  public Dc(DegreeRequestRepository degreeRequestRepository) {
    this.degreeRequestRepository = degreeRequestRepository;
  }

  // =========================================================
  // ======================= PDF =============================
  // =========================================================
  public String stampPdfByDegreeRequestId(UUID degreeRequestId) throws Exception {

    // 1️⃣ DegreeRequest get
    DegreeRequest request = degreeRequestRepository.findById(degreeRequestId)
        .orElseThrow(() -> new RuntimeException("DegreeRequest not found"));

    // 2️⃣ Original document path
    Path originalPath = Paths.get(request.getDocumentPath());
    if (!Files.exists(originalPath)) {
      throw new RuntimeException("Original PDF not found on disk");
    }

    // 3️⃣ Stamped directory ensure
    Path stampedDir = Paths.get(STAMPED_DIR);
    if (!Files.exists(stampedDir)) {
      Files.createDirectories(stampedDir);
    }

    // 4️⃣ New stamped file path
    Path stampedPath = stampedDir.resolve(
        "stamped_" + originalPath.getFileName());

    // 5️⃣ Stamp process
    try (
        InputStream inputStream = Files.newInputStream(originalPath);
        OutputStream outputStream = Files.newOutputStream(stampedPath)) {
      PdfReader reader = new PdfReader(inputStream);

      PdfStamper stamper = new PdfStamper(reader, outputStream);

      Image stamp = Image.getInstance(STAMP_IMAGE_PATH);
      stamp.scaleToFit(100, 100);
      stamp.setAbsolutePosition(STAMP_X, STAMP_Y);

      PdfGState gState = new PdfGState();
      gState.setFillOpacity(STAMP_OPACITY);

      for (int i = 1; i <= reader.getNumberOfPages(); i++) {
        PdfContentByte content = stamper.getOverContent(i);
        content.setGState(gState);
        content.addImage(stamp);
      }

      stamper.close();
      reader.close();
    }
    request.setDocumentPath(stampedPath.toString());
    request.setDocumentStatus(DocumentStatus.HEC_STAMPED);
    degreeRequestRepository.save(request);

    return stampedPath.toString();
  }

  // =========================================================
  // ======================= DOCX ============================
  // =========================================================
  public String stampDocxByDegreeRequestId(UUID degreeRequestId) throws Exception {

    // 1️⃣ DegreeRequest get
    DegreeRequest request = degreeRequestRepository.findById(degreeRequestId)
        .orElseThrow(() -> new RuntimeException("DegreeRequest not found"));

    // 2️⃣ Original document path
    Path originalPath = Paths.get(request.getDocumentPath());
    if (!Files.exists(originalPath)) {
      throw new RuntimeException("Original DOCX not found on disk");
    }

    // 3️⃣ Stamped directory ensure
    Path stampedDir = Paths.get(STAMPED_DIR);
    if (!Files.exists(stampedDir)) {
      Files.createDirectories(stampedDir);
    }

    // 4️⃣ New stamped file path
    Path stampedPath = stampedDir.resolve(
        "stamped_" + originalPath.getFileName());

    // 5️⃣ Stamp process
    try (
        InputStream is = Files.newInputStream(originalPath);
        OutputStream os = Files.newOutputStream(stampedPath)) {
      XWPFDocument document = new XWPFDocument(is);

      XWPFParagraph paragraph = document.createParagraph();
      XWPFRun run = paragraph.createRun();

      try (FileInputStream stampStream = new FileInputStream(STAMP_IMAGE_PATH)) {
        run.addPicture(
            stampStream,
            XWPFDocument.PICTURE_TYPE_PNG,
            "stamp.png",
            Units.toEMU(100),
            Units.toEMU(100));
      }

      run.addBreak();
      run.setText("Digitally stamped document");

      document.write(os);
      document.close();
    }

    // 6️⃣ Return stamped file path
    return stampedPath.toString();
  }
}
