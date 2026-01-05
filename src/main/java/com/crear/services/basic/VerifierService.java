package com.crear.services.basic;

import com.crear.dtos.DegreeRequestVerifierDto;
import com.crear.entities.BlockchainTransaction;
import com.crear.entities.DegreeRequest;
import com.crear.entities.Student;
import com.crear.entities.University;
import com.crear.services.DegreeRequestService;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerifierService {

  private final DegreeRequestService degreeRequestService;

  /**
   * Verify a degree request by UUID
   * 
   * @param degreeRequestId UUID of the degree request
   * @return DegreeRequestVerifierDto with student, degree, IPFS, and blockchain
   *         info
   * @throws Exception if something goes wrong
   */
  public DegreeRequestVerifierDto verifyDegreeRequestById(UUID degreeRequestId) throws Exception {

    // 1️⃣ Fetch degree request
    DegreeRequest degreeRequest = degreeRequestService.getDegreeRequestById(degreeRequestId);
    if (degreeRequest == null) {
      throw new RuntimeException("Degree request not found");
    }

    // 2️⃣ Get related entities
    Student student = degreeRequest.getStudent();
    University university = degreeRequest.getUniversity();
    BlockchainTransaction tx = degreeRequest.getBlockchainTransaction();

    DegreeRequestVerifierDto dto = DegreeRequestVerifierDto.builder()
        .degreeRequestId(degreeRequest.getId())
        .program(degreeRequest.getProgram())
        .rollNumber(degreeRequest.getRollNumber())
        .passingYear(degreeRequest.getPassingYear())
        .cgpa(degreeRequest.getCgpa())
        .requestDate(degreeRequest.getRequestDate())
        .status(degreeRequest.getStatus())
        .documentStatus(degreeRequest.getDocumentStatus())
        .remarks(degreeRequest.getRemarks())
        .studentName(student != null ? student.getFullName() : null)
        .studentCnic(student != null ? student.getCnic() : null)
        .universityName(university != null ? university.getName() : null)
        .ipfsHash(degreeRequest.getIpfsHash())
        .ipfsProvider(degreeRequest.getIpfsProvider())
        .transactionHash(tx != null ? tx.getTransactionHash() : null)
        .blockNumber(tx != null ? tx.getBlockNumber() : null)
        .gasUsed(tx != null ? tx.getGasUsed() : null)
        .contractAddress(tx != null ? tx.getContractAddress() : null)
        .blockchainStatus(tx != null ? tx.getStatus() : null)
        .build();

    return dto;
  }

}
