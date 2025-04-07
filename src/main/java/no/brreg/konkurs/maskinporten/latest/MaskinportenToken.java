package no.brreg.konkurs.maskinporten.latest;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MaskinportenToken(
        @JsonProperty("access_token")
        String accessToken,
        @JsonProperty("token_type")
        String tokenType,
        String scope,
        @JsonProperty("expires_in")
        String expiresIn
) {
}
