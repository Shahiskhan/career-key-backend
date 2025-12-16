package com.crear.utils;

import com.crear.dtos.degreerequest.DocInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;

@Service
public class DocService {

    @Value("${degree.doc.upload-path:src/main/resources/static/AttestationDoc}")
    private String uploadPath;

    public DocInfo saveDocumentWithUserName(MultipartFile document, String userName) {

        if (document == null || document.isEmpty()) {
            return null;
        }

        if (userName == null || userName.isBlank()) {
            userName = "unknown";
        }

        try {
            // Ensure directory exists
            Files.createDirectories(Paths.get(uploadPath));

            // 🔥 Extract extension from original file
            String originalName = document.getOriginalFilename();
            String extension = "";

            if (originalName != null && originalName.contains(".")) {
                extension = originalName.substring(originalName.lastIndexOf("."));
            }

            // ✅ FINAL file name → only userName + extension
            String fileName = userName + extension;
            Path filePath = Paths.get(uploadPath, fileName);

            // Save file (overwrite if exists)
            Files.write(filePath, document.getBytes(), StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);

            // Return DocInfo
            return new DocInfo(fileName, filePath.toString());

        } catch (Exception e) {
            throw new RuntimeException("Failed to save document", e);
        }
    }
}
