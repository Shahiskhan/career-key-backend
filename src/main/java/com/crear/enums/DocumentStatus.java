package com.crear.enums;

public enum DocumentStatus {

  PENDING, // Shuruat mein jab koi kaam na hua ho
  HEC_STAMPED, // HEC stamp lagne ke baad
  IPFS_UPLOADED, // IPFS pe upload hone ke baad (Hash generate ho gaya)
  QR_GENERATED, // IPFS hash ka QR document pe print hone ke baad
  BLOCKCHAIN_ANCHORED // Final step: Jab hash blockchain pe upload ho jaye

}
