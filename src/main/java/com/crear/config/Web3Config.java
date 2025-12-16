package com.crear.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;

@Configuration
public class Web3Config {

    // Ganache RPC
    private static final String GANACHE_URL = "http://127.0.0.1:7545";

    /*
     * Account Address (for reference)
     * 0xBdE6b3Bc59074d1072a2Af8348e8c9087CC8AB9e
     *
     * ⚠️ Testing private key (Ganache only)
     */
    private static final String PRIVATE_KEY = "0x635702d09b3bdf1fc9231af854940f14a63e2c263d6f80579edadc89f4da6ee5";

    @Bean
    public Web3j web3j() {
        return Web3j.build(new HttpService(GANACHE_URL));
    }

    @Bean
    public Credentials credentials() {
        return Credentials.create(PRIVATE_KEY);
    }

    @Bean
    public DefaultGasProvider defaultGasProvider() {
        return new DefaultGasProvider();
    }

    @Bean
    public ContractGasProvider gasProvider() {
        return new StaticGasProvider(
                BigInteger.valueOf(20_000_000_000L), // 20 Gwei
                BigInteger.valueOf(6_000_000) // Ganache-safe gas limit
        );
    }
}
