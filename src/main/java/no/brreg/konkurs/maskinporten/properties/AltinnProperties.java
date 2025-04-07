package no.brreg.konkurs.maskinporten.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "altinn")
public record AltinnProperties(
        String url,
        String tokenUrl
) {
}
