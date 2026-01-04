package com.crear.config;

import com.crear.web3.Web3Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class Web3Config {

    private final Web3Constants web3;

    @Bean
    public Web3j web3j() {
        return Web3j.build(new HttpService(web3.RPC_URL));
    }

    @Bean
    public Credentials credentials() {
        return Credentials.create(web3.PRIVATE_KEY);
    }

    @Bean
    public ContractGasProvider gasProvider() {
        return new StaticGasProvider(
                web3.GAS_PRICE,
                web3.GAS_LIMIT);
    }
}
