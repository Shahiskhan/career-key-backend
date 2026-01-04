package com.crear.controllers;

import com.crear.dtos.ApiResponse;
import com.crear.dtos.degree_attestation.AppConstants;
import com.crear.dtos.degree_attestation.IpfsUploadResponse;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

import com.crear.web3.IpfsService;
import com.crear.web3.PdfQrStampService;
import com.crear.web3.SmartContractService;
import com.crear.web3.Web3Constants;
import com.crear.dtos.degree_attestation.StampedDocumentResponse;
import com.crear.dtos.degree_attestation.StoreResultResponse;
import com.crear.dtos.degreerequest.DegreeRequestDto;
import com.crear.entities.BlockchainTransaction;
import com.crear.entities.DegreeRequest;
import com.crear.enums.DocumentStatus;
import com.crear.services.DegreeRequestService;
import com.crear.services.basic.Dc;

@RestController
@RequestMapping("api/v1/hec/attestation")
@RequiredArgsConstructor
public class AttestationProcess {

        private final DegreeRequestService degreeRequestService;
        private final Dc dc;
        private final IpfsService ipfsService;
        private final PdfQrStampService pdfQrStampService;
        private final SmartContractService contractService;

        private final Web3Constants web3Constants;

        // @PostMapping("/degree-requests/{degreeRequestId}/stamp")
        // public void stampDegreeRequest(
        // @PathVariable UUID degreeRequestId,
        // HttpServletResponse response) throws Exception {

        // // 1. Stamp lagaye aur file ka path le
        // String stampedFilePath = Dc.stampPdfByDegreeRequestId(degreeRequestId);

        // // 2. File ko path se load karein
        // Path filePath = Paths.get(stampedFilePath);

        // // 3. Response setup karein
        // response.setContentType("application/pdf");
        // response.setHeader("Content-Disposition",
        // "attachment; filename=\"" + filePath.getFileName() + "\"");

        // // 4. File ka size bata dein
        // response.setContentLength((int) Files.size(filePath));

        // // 5. File ka data response mein copy karein
        // Files.copy(filePath, response.getOutputStream());
        // response.getOutputStream().flush();
        // }

        @PostMapping("/degree-requests/{degreeRequestId}/stamp")
        public ResponseEntity<ApiResponse<StampedDocumentResponse>> stampDegreeRequest(
                        @PathVariable UUID degreeRequestId) {

                try {
                        String stampedFilePath = dc.stampPdfByDegreeRequestId(degreeRequestId);

                        Path filePath = Paths.get(stampedFilePath);
                        byte[] bytes = Files.readAllBytes(filePath);

                        StampedDocumentResponse response = StampedDocumentResponse.builder()
                                        .fileName(filePath.getFileName().toString())
                                        .contentType("application/pdf")
                                        .base64(Base64.getEncoder().encodeToString(bytes))
                                        .build();

                        return ResponseEntity.ok(
                                        ApiResponse.<StampedDocumentResponse>builder()
                                                        .success(true)
                                                        .data(response)
                                                        .build());

                } catch (Exception e) {
                        return ResponseEntity.internalServerError()
                                        .body(ApiResponse.<StampedDocumentResponse>builder()
                                                        .success(false)
                                                        .message("Stamp PDF base64 failed: " + e.getMessage())
                                                        .build());
                }
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
                        request.setIpfsProvider("https://gateway.pinata.cloud/ipfs/");
                        request.setDocumentStatus(DocumentStatus.IPFS_UPLOADED);

                        DegreeRequestDto degreeRequestDto = degreeRequestService.updateDegreeRequestDto(request);

                        // 5️⃣ Return response
                        return ResponseEntity.ok(degreeRequestDto);

                } catch (Exception e) {
                        return ResponseEntity.internalServerError()
                                        .body(e.getMessage());
                }
        }

        @PostMapping("/degree-requests/{degreeRequestId}/qr-stamp")
        public ResponseEntity<ApiResponse<StampedDocumentResponse>> stampPdfWithQr(
                        @PathVariable UUID degreeRequestId) {

                try {
                        // 1️⃣ Fetch request
                        DegreeRequest request = degreeRequestService.getDegreeRequestById(degreeRequestId);

                        if (request == null) {
                                return ResponseEntity.badRequest()
                                                .body(ApiResponse.<StampedDocumentResponse>builder()
                                                                .success(false)
                                                                .message("Degree request not found")
                                                                .build());
                        }

                        // 2️⃣ Load original PDF
                        Path originalPdfPath = Paths.get(request.getDocumentPath());

                        if (!Files.exists(originalPdfPath)) {
                                return ResponseEntity.badRequest()
                                                .body(ApiResponse.<StampedDocumentResponse>builder()
                                                                .success(false)
                                                                .message("Original document not found")
                                                                .build());
                        }

                        byte[] originalPdfBytes = Files.readAllBytes(originalPdfPath);

                        // 3️⃣ Build verifier URL
                        String verifierUrl = AppConstants.VERIFIER_QR_URL + degreeRequestId;

                        // 4️⃣ Add QR to PDF
                        byte[] stampedPdfBytes = pdfQrStampService.addQrToPdf(originalPdfBytes, verifierUrl);

                        // 5️⃣ Ensure directory exists
                        Files.createDirectories(
                                        Paths.get(AppConstants.QR_FILE_STORAGE_PATH));

                        // 6️⃣ Save stamped PDF
                        String fileName = "qr-stamped-" + degreeRequestId + ".pdf";

                        Path savedPath = Paths.get(
                                        AppConstants.QR_FILE_STORAGE_PATH + fileName);

                        Files.write(savedPath, stampedPdfBytes);

                        // 7️⃣ Update request with new path
                        request.setDocumentPath(savedPath.toString());
                        request.setDocumentStatus(DocumentStatus.QR_GENERATED);

                        degreeRequestService.updateDegreeRequest(request);

                        // 8️⃣ Response
                        StampedDocumentResponse response = StampedDocumentResponse.builder()
                                        .fileName(fileName)
                                        .contentType("application/pdf")
                                        .base64(Base64.getEncoder().encodeToString(stampedPdfBytes))
                                        .build();

                        return ResponseEntity.ok(
                                        ApiResponse.<StampedDocumentResponse>builder()
                                                        .success(true)
                                                        .data(response)
                                                        .build());

                } catch (Exception e) {
                        return ResponseEntity.internalServerError()
                                        .body(ApiResponse.<StampedDocumentResponse>builder()
                                                        .success(false)
                                                        .message("QR Stamp failed: " + e.getMessage())
                                                        .build());
                }
        }

        @PostMapping("/degree-requests/{degreeRequestId}/store-result")
        public ResponseEntity<ApiResponse<StoreResultResponse>> storeResultOnBlockchain(
                        @PathVariable UUID degreeRequestId) {

                try {
                        // 1️⃣ Fetch DegreeRequest + Student
                        DegreeRequest degreeRequest = degreeRequestService.getDegreeRequestById(degreeRequestId);
                        if (degreeRequest == null) {
                                return ResponseEntity.badRequest().body(
                                                ApiResponse.<StoreResultResponse>builder()
                                                                .success(false)
                                                                .message("Degree request not found")
                                                                .build());
                        }

                        var student = degreeRequest.getStudent();

                        String rollNumber = String.valueOf(degreeRequest.getRollNumber());
                        String cnic = String.valueOf(student.getCnic());
                        String ipfsHash = String.valueOf(degreeRequest.getIpfsHash());

                        // 2️⃣ Call Blockchain → String txHash
                        String txHash = contractService.storeResult(rollNumber, cnic, ipfsHash);

                        // 3️⃣ Create BlockchainTransaction entity
                        BlockchainTransaction tx = BlockchainTransaction.builder()
                                        .degreeRequest(degreeRequest)
                                        .transactionHash(txHash)
                                        .blockNumber(null) // jab receipt nahi hai, set null
                                        .gasUsed(null) // optional
                                        .contractAddress(web3Constants.CONTRACT_ADDRESS)
                                        .status("PENDING") // initially pending
                                        .build();

                        // 4️⃣ Link + Save
                        degreeRequest.setBlockchainTransaction(tx);
                        degreeRequest.setDocumentStatus(DocumentStatus.BLOCKCHAIN_ANCHORED);
                        degreeRequestService.updateDegreeRequest(degreeRequest);

                        // 5️⃣ Prepare response DTO
                        StoreResultResponse response = StoreResultResponse.builder()

                                        .studentCnic(cnic)
                                        .rollNumber(rollNumber)
                                        .degreeRequestId(degreeRequest.getId())
                                        .ipfsHash(ipfsHash)
                                        .transactionHash(tx.getTransactionHash())
                                        .blockNumber(tx.getBlockNumber() != null ? tx.getBlockNumber().toString()
                                                        : null)
                                        .status(tx.getStatus())
                                        .build();

                        return ResponseEntity.ok(
                                        ApiResponse.<StoreResultResponse>builder()
                                                        .success(true)
                                                        .message("Result stored on blockchain successfully (pending confirmation)")
                                                        .data(response)
                                                        .build());

                } catch (Exception e) {
                        return ResponseEntity.internalServerError().body(
                                        ApiResponse.<StoreResultResponse>builder()
                                                        .success(false)
                                                        .message("Failed to store result on blockchain: "
                                                                        + e.getMessage())
                                                        .build());
                }
        }

}
