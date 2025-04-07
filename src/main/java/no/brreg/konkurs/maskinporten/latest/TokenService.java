package no.brreg.konkurs.maskinporten.latest;

import lombok.extern.slf4j.Slf4j;
import no.brreg.konkurs.maskinporten.properties.AltinnProperties;
import no.brreg.konkurs.maskinporten.properties.MaskinportenProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.net.URI;

@EnableConfigurationProperties({MaskinportenProperties.class, AltinnProperties.class})
@Service
@Slf4j
public class TokenService {

    private MaskinportenProperties maskinportenProperties;
    private AltinnProperties altinnProperties;
    private RestClient restClient;

    public TokenService(MaskinportenProperties maskinportenProperties, AltinnProperties altinnProperties, RestClient.Builder builder) {
        this.maskinportenProperties = maskinportenProperties;
        this.altinnProperties = altinnProperties;
        this.restClient = builder.build();
    }

    public String getAltinnToken() {

        return restClient.get()
                .uri(URI.create(altinnProperties.tokenUrl()).normalize())
                .headers( httpHeaders -> httpHeaders.setBearerAuth(getMaskinportenToken().accessToken()))
                .retrieve()
                .body(String.class);
    }

    private MaskinportenToken getMaskinportenToken() {
        String signedJWT = JwtSignedUtil.createSignedJWT(
                maskinportenProperties.aud(),
                maskinportenProperties.iss(),
                maskinportenProperties.scope(),
                maskinportenProperties.secret()
        );

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "urn:ietf:params:oauth:grant-type:jwt-bearer");
        requestBody.add("assertion", signedJWT);

        return restClient.post()
                .uri(URI.create(maskinportenProperties.tokenUrl()).normalize())
                .accept(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .retrieve()
                .body(MaskinportenToken.class);
    }
}
