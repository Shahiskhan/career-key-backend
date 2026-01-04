package com.crear.web3;

import com.crear.model.Src_solidity_StudentResults_sol_StudentResults;
import com.crear.model.Src_solidity_StudentResults_sol_StudentResults.StudentRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.gas.ContractGasProvider;

@Service
public class SmartContractService {

    private final Src_solidity_StudentResults_sol_StudentResults contract;

    public SmartContractService(
            Web3j web3j,
            Credentials credentials,
            ContractGasProvider gasProvider,
            Web3Constants constants) {

        this.contract = Src_solidity_StudentResults_sol_StudentResults.load(
                constants.CONTRACT_ADDRESS,
                web3j,
                credentials,
                gasProvider);
    }

    // OWNER
    public String getOwner() throws Exception {
        return contract.owner().send();
    }

    // ADMIN
    public String addAdmin(String adminAddress) throws Exception {
        return contract.addAdmin(adminAddress)
                .send()
                .getTransactionHash();
    }

    public String removeAdmin(String adminAddress) throws Exception {
        return contract.removeAdmin(adminAddress)
                .send()
                .getTransactionHash();
    }

    public boolean isAdmin(String address) throws Exception {
        return contract.admins(address).send();
    }

    // RESULT WRITE
    public String storeResult(String studentId, String cnic, String ipfsHash)
            throws Exception {

        return contract.storeResult(studentId, cnic, ipfsHash)
                .send()
                .getTransactionHash();
    }

    public String updateResult(String studentId, String newIpfsHash)
            throws Exception {

        return contract.updateResult(studentId, newIpfsHash)
                .send()
                .getTransactionHash();
    }

    public String deleteResult(String studentId) throws Exception {
        return contract.deleteResult(studentId)
                .send()
                .getTransactionHash();
    }

    // RESULT READ
    public StudentRecord getResultByStudentId(String studentId)
            throws Exception {
        return contract.getResult(studentId).send();
    }

    public StudentRecord getResultByCnic(String cnic)
            throws Exception {
        return contract.getResultByCnic(cnic).send();
    }
}
