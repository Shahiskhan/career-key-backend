package com.crear.web3;

import com.crear.model.Src_solidity_StudentResults_sol_StudentResults;
import com.crear.model.Src_solidity_StudentResults_sol_StudentResults.StudentRecord;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.ContractGasProvider;

@Service
public class SmartContractService {

    private final Src_solidity_StudentResults_sol_StudentResults contract;

    public SmartContractService(
            Web3j web3j,
            Credentials credentials,
            ContractGasProvider gasProvider) {

        this.contract = Src_solidity_StudentResults_sol_StudentResults.load(
                "0x99129Eeb952FB183DcddA6cdea46Ac7561eeFaB6",
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
        TransactionReceipt receipt = contract.addAdmin(adminAddress).send();
        return receipt.getTransactionHash();
    }

    public String removeAdmin(String adminAddress) throws Exception {
        TransactionReceipt receipt = contract.removeAdmin(adminAddress).send();
        return receipt.getTransactionHash();
    }

    public boolean isAdmin(String address) throws Exception {
        return contract.admins(address).send();
    }

    // store RESULT

    public String storeResult(
            String studentId,
            String cnic,
            String ipfsHash) throws Exception {

        TransactionReceipt receipt = contract.storeResult(studentId, cnic, ipfsHash).send();

        return receipt.getTransactionHash();
    }

    public String updateResult(
            String studentId,
            String newIpfsHash) throws Exception {

        TransactionReceipt receipt = contract.updateResult(studentId, newIpfsHash).send();

        return receipt.getTransactionHash();
    }

    public String deleteResult(String studentId) throws Exception {
        TransactionReceipt receipt = contract.deleteResult(studentId).send();

        return receipt.getTransactionHash();
    }

    // RESULT (READ)

    public StudentRecord getResultByStudentId(String studentId)
            throws Exception {

        return contract.getResult(studentId).send();
    }

    public StudentRecord getResultByCnic(String cnic)
            throws Exception {

        return contract.getResultByCnic(cnic).send();
    }
}
