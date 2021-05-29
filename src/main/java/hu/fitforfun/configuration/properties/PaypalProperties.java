package hu.fitforfun.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "paypal")
@Data
public class PaypalProperties {
    private String mode;
    private String clientId;
    private String clientSecret;
    private String successUrl;
    private String cancelUrl;
}
