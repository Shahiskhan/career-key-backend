package com.crear.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "hec.auto-registration")
public class HecAutoRegistrationProperties {
  private boolean enabled = true;
  private String email = "admin@hec.gov.pk";
  private String password = "Admin@123";
  private String name = "HEC Pakistan";
  private String hecCode = "HEC-PK-001";
  private String headOffice = "Islamabad, Pakistan";
  private String digitalSealPath = "/certificates/hec-seal.pfx";
}