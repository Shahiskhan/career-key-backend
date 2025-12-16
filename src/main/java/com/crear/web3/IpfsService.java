package com.crear.web3;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

@Service
public class IpfsService {

    private final OkHttpClient ipfsClient = new OkHttpClient();

    @Value("${ipfs.mode:local}")
    private String mode;

    @Value("${ipfs.local.gateway.url}")
    private String localGatewayUrl;

    @Value("${ipfs.infura.gateway.url}")
    private String infuraGatewayUrl;

    @Value("${ipfs.infura.project.id:}")
    private String projectId;

    @Value("${ipfs.infura.project.secret:}")
    private String projectSecret;

    // ✅ Upload file (works for both local & Infura)
    public String uploadFile(File file) throws IOException {
        String targetUrl = getGatewayUrl() + "/add";

        RequestBody fileBody = RequestBody.create(file, MediaType.parse("application/octet-stream"));

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(), fileBody)
                .build();

        Request.Builder requestBuilder = new Request.Builder()
                .url(targetUrl)
                .post(requestBody);

        // 🔐 Add Infura Auth header only if mode = infura
        if (isInfura()) {
            String credentials = projectId + ":" + projectSecret;
            String basicAuth = "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());
            requestBuilder.addHeader("Authorization", basicAuth);
        }

        try (Response response = ipfsClient.newCall(requestBuilder.build()).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("IPFS upload failed: " + response);
            }

            String jsonResponse = response.body().string();
            String hash = jsonResponse.split("\"Hash\":\"")[1].split("\"")[0];
            return hash;
        }
    }

    // ✅ Download file (works for both local & Infura)
    public byte[] downloadFile(String hash) throws IOException {
        String fileUrl;

        if (isLocal()) {
            fileUrl = "http://127.0.0.1:8080/ipfs/" + hash;
        } else {
            fileUrl = "https://ipfs.io/ipfs/" + hash;
        }

        Request request = new Request.Builder().url(fileUrl).get().build();

        try (Response response = ipfsClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Failed to fetch from IPFS: " + response);
            }
            return response.body().bytes();
        }
    }

    // 🧩 Helper methods
    private boolean isInfura() {
        return "infura".equalsIgnoreCase(mode);
    }

    private boolean isLocal() {
        return "local".equalsIgnoreCase(mode);
    }

    private String getGatewayUrl() {
        return isInfura() ? infuraGatewayUrl : localGatewayUrl;
    }
}
