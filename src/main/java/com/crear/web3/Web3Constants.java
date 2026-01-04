package com.crear.web3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class Web3Constants {

  @Value("${web3.rpc.url}")
  public String RPC_URL;

  @Value("${web3.wallet.private-key}")
  public String PRIVATE_KEY;

  @Value("${web3.contract.address}")
  public String CONTRACT_ADDRESS;

  @Value("${web3.gas.price}")
  public BigInteger GAS_PRICE;

  @Value("${web3.gas.limit}")
  public BigInteger GAS_LIMIT;
}
