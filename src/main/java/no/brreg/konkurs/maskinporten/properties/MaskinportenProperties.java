package no.brreg.konkurs.maskinporten.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "maskinporten")
public record MaskinportenProperties(
        String aud,
        String iss,
        String scope,
        String secret,
        String tokenUrl
) {
}
