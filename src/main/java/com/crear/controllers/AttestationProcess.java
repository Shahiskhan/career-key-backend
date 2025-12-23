package com.crear.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
public class AttestationProcess {

    private DocumentService documentService;
    private DegreeRequestService degreeRequestService;
    private com.crear.services.basic.Dc Dc;

    @PostMapping("/degree-requests/{degreeRequestId}/stamp")
    public void stampDegreeRequest(
            @PathVariable UUID degreeRequestId,
            HttpServletResponse response) throws Exception {

        String s = Dc.stampPdfByDegreeRequestId(degreeRequestId);
    }

    // uplaod to ipfs

    // upload meta data to block chain and return qr code print doc

}
