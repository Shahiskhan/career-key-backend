package com.crear.controllers;

import com.crear.dtos.ApiResponse;
import com.crear.dtos.degree_attestation.StoreResultRequest;
import com.crear.dtos.degree_attestation.StoreResultResponse;
import com.crear.dtos.degree_attestation.UpdateResultRequest;
import com.crear.entities.BlockchainTransaction;
import com.crear.entities.DegreeRequest;
import com.crear.model.Src_solidity_StudentResults_sol_StudentResults.StudentRecord;
import com.crear.services.DegreeRequestService;
import com.crear.web3.SmartContractService;
import com.crear.web3.Web3Constants;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

@RestController
@RequestMapping("/api/blockchain")
@RequiredArgsConstructor
public class SmartContractController {

  private final SmartContractService contractService;
  private final DegreeRequestService degreeRequestService;
  private final Web3Constants web3Constants;

  // ================= OWNER =================

  @GetMapping("/owner")
  public ResponseEntity<String> getOwner() throws Exception {
    return ResponseEntity.ok(contractService.getOwner());
  }

  // ================= ADMIN =================

  @PostMapping("/admin")
  public ResponseEntity<String> addAdmin(
      @RequestParam String adminAddress) throws Exception {

    String txHash = contractService.addAdmin(adminAddress);
    return ResponseEntity.ok(txHash);
  }

  @DeleteMapping("/admin/{address}")
  public ResponseEntity<String> removeAdmin(
      @PathVariable String address) throws Exception {

    String txHash = contractService.removeAdmin(address);
    return ResponseEntity.ok(txHash);
  }

  @GetMapping("/admin/{address}")
  public ResponseEntity<Boolean> isAdmin(
      @PathVariable String address) throws Exception {

    return ResponseEntity.ok(contractService.isAdmin(address));
  }

  // ================= RESULT (WRITE) =================

  @PutMapping("/result")
  public ResponseEntity<String> updateResult(
      @RequestBody UpdateResultRequest request) throws Exception {

    String txHash = contractService.updateResult(
        request.getStudentId(),
        request.getNewIpfsHash());

    return ResponseEntity.ok(txHash);
  }

  @DeleteMapping("/result/{studentId}")
  public ResponseEntity<String> deleteResult(
      @PathVariable String studentId) throws Exception {

    String txHash = contractService.deleteResult(studentId);
    return ResponseEntity.ok(txHash);
  }

  // ================= RESULT (READ) =================

  @GetMapping("/result/student/{studentId}")
  public ResponseEntity<StudentRecord> getResultByStudentId(
      @PathVariable String studentId) throws Exception {

    return ResponseEntity.ok(
        contractService.getResultByStudentId(studentId));
  }

  @GetMapping("/result/cnic/{cnic}")
  public ResponseEntity<StudentRecord> getResultByCnic(
      @PathVariable String cnic) throws Exception {

    return ResponseEntity.ok(
        contractService.getResultByCnic(cnic));
  }
}
