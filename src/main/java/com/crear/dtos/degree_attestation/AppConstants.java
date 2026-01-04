package com.crear.dtos.degree_attestation;

public final class AppConstants {

  private AppConstants() {
  }

  // 🔗 Public Verifier Portal Base URL
  public static final String VERIFIER_PORTAL_BASE_URL = "http://localhost:5173/verifier-portal";

  // 🔗 QR Verify Path
  public static final String VERIFIER_QR_URL = VERIFIER_PORTAL_BASE_URL + "/verify?requestId=";
  public static final String QR_FILE_STORAGE_PATH = "D:/FYP-PROJECT/career-key/src/main/resources/static/upload/QrFiles/";
}
