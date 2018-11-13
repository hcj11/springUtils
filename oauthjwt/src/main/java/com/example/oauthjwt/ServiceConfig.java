package com.example.oauthjwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by hcj on 18-7-24
 */
@Data
@Component
@ConfigurationProperties(prefix = "hcj")
public class ServiceConfig {
  public String signkey;

}
