package com.crear.web3;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

@Service
@Slf4j
public class IpfsService {

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    @Value("${ipfs.mode:pinata}")
    private String mode;

    // Pinata
    @Value("${ipfs.pinata.jwt}")
    private String pinataJwt;

    @Value("${ipfs.pinata.api.url}")
    private String pinataApiUrl;

    @Value("${ipfs.pinata.gateway.url}")
    private String pinataGatewayUrl;

    // Infura
    @Value("${ipfs.infura.gateway.url}")
    private String infuraGatewayUrl;

    @Value("${ipfs.infura.project.id:}")
    private String projectId;

    @Value("${ipfs.infura.project.secret:}")
    private String projectSecret;

    // Local
    @Value("${ipfs.local.gateway.url}")
    private String localGatewayUrl;

    public String uploadFile(File file) throws IOException {

        String url = getUploadUrl();
        log.info("Uploading file to IPFS via: {}", url);

        RequestBody fileBody = RequestBody.create(file, MediaType.parse("application/octet-stream"));
        MultipartBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(), fileBody)
                .build();

        Request.Builder builder = new Request.Builder().url(url).post(requestBody);

        // Add Auth Headers
        if ("pinata".equalsIgnoreCase(mode)) {
            builder.addHeader("Authorization", "Bearer " + pinataJwt);
        } else if ("infura".equalsIgnoreCase(mode)) {
            String credentials = projectId + ":" + projectSecret;
            String basicAuth = "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());
            builder.addHeader("Authorization", basicAuth);
        }

        try (Response response = client.newCall(builder.build()).execute()) {

            if (!response.isSuccessful())
                throw new IOException("IPFS upload failed: " + response);

            String json = response.body().string();
            JsonNode root = mapper.readTree(json);

            if ("pinata".equalsIgnoreCase(mode))
                return root.get("IpfsHash").asText();
            else
                return root.get("Hash").asText();
        }
    }

    public byte[] downloadFile(String hash) throws IOException {
        String fileUrl;

        switch (mode.toLowerCase()) {
            case "pinata":
                fileUrl = pinataGatewayUrl + hash;
                break;
            case "infura":
                fileUrl = infuraGatewayUrl + "/ipfs/" + hash;
                break;
            default:
                fileUrl = localGatewayUrl + "/ipfs/" + hash;
        }

        Request request = new Request.Builder().url(fileUrl).get().build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful())
                throw new IOException("Failed to download file: " + response);
            return response.body().bytes();
        }
    }

    private String getUploadUrl() {
        switch (mode.toLowerCase()) {
            case "pinata":
                return pinataApiUrl;
            case "infura":
                return infuraGatewayUrl + "/add";
            default:
                return localGatewayUrl + "/add";
        }
    }
}
