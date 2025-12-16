package com.crear.web3;

import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.gas.DefaultGasProvider;

@Service
public class BlockchainDeployService {

    private final Web3j web3j;
    private final Credentials credentials;
    private final DefaultGasProvider gasProvider;

    public BlockchainDeployService(Web3j web3j, Credentials credentials, DefaultGasProvider gasProvider) {
        this.web3j = web3j;
        this.credentials = credentials;
        this.gasProvider = gasProvider;
    }

}
