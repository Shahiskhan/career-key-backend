package com.crear.controllers;

import com.crear.dtos.degree_attestation.IpfsUploadResponse;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.crear.web3.IpfsService;
import com.crear.web3.PdfQrStampService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpServletResponse;

import com.crear.dtos.degree_attestation.StampRequestDto;
import com.crear.entities.DegreeRequest;
import com.crear.entities.Student;
import com.crear.enums.RequestStatus;
import com.crear.services.DegreeRequestService;
import com.crear.services.basic.DocumentService;

@RestController
@RequestMapping("api/v1/hec/attestation")
public class AttestationProcess {

    private DocumentService documentService;
    private DegreeRequestService degreeRequestService;

    private com.crear.services.basic.Dc Dc;
    private IpfsService ipfsService;

    @PostMapping("/degree-requests/{degreeRequestId}/stamp")
    public void stampDegreeRequest(
            @PathVariable UUID degreeRequestId,
            HttpServletResponse response) throws Exception {

        // 1. Stamp lagaye aur file ka path le
        String stampedFilePath = Dc.stampPdfByDegreeRequestId(degreeRequestId);

        // 2. File ko path se load karein
        Path filePath = Paths.get(stampedFilePath);

        // 3. Response setup karein
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition",
                "attachment; filename=\"" + filePath.getFileName() + "\"");

        // 4. File ka size bata dein
        response.setContentLength((int) Files.size(filePath));

        // 5. File ka data response mein copy karein
        Files.copy(filePath, response.getOutputStream());
        response.getOutputStream().flush();
    }

    @PostMapping("/upload/{degreeRequestId}")
    public ResponseEntity<?> uploadDegreeToIpfs(
            @PathVariable UUID degreeRequestId) {

        try {
            // 1️⃣ Fetch request from DB
            DegreeRequest request = degreeRequestService.getDegreeRequestById(degreeRequestId);

            // 2️⃣ Load file from local storage
            File file = new File(request.getDocumentPath());

            if (!file.exists()) {
                return ResponseEntity.badRequest()
                        .body("File not found on server");
            }

            // 3️⃣ Upload to IPFS
            String ipfsHash = ipfsService.uploadFile(file);

            // 4️⃣ (Optional) Save hash in DB
            request.setIpfsHash(ipfsHash);
            degreeRequestService.updateDegreeRequest(request);

            // 5️⃣ Return response
            return ResponseEntity.ok(
                    new IpfsUploadResponse(
                            "File uploaded successfully",
                            ipfsHash,
                            "https://ipfs.io/ipfs/" + ipfsHash));

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(e.getMessage());
        }
    }

    // upload meta data to block chain and return qr code print doc

}
