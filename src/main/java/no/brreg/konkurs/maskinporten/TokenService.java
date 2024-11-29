package no.brreg.konkurs.maskinporten;

import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.oauth2.sdk.JWTBearerGrant;
import com.nimbusds.oauth2.sdk.TokenRequest;
import com.nimbusds.oauth2.sdk.TokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {

    private final JwtService jwtService;

    @Value("${maskinporten.url}")
    String maskinportUrl;

    public String hentMaskinportenToken(String scopes) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        String jwt = jwtService.lagJwt(scopes);
        log.debug("jwt {}", jwt);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "urn:ietf:params:oauth:grant-type:jwt-bearer");
        params.add("assertion", jwt);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
        try {
            ResponseEntity<Token> response = restTemplate.exchange(maskinportUrl, HttpMethod.POST, entity, new ParameterizedTypeReference<Token>() {
            });
            log.debug("token : {}", response.getBody().getAccess_token());
            return response.getBody().getAccess_token();
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            log.error(" url {} httpkode {} melding {} ", maskinportUrl, ex.getStatusCode(), ex.getResponseBodyAsString());
            throw new RuntimeException("hent Maskinporten token feiler", ex);
        }
    }






//
//    public String validateMaskinportenToken(String scope) throws Exception {
//        TokenResponse parse = null;
//        log.info("Henter token");
//        try {
//            SignedJWT requestToken = jwtService.createSignedJWT(scope);
//            JWTBearerGrant jwtBearerGrant = new JWTBearerGrant(requestToken);
//            TokenRequest tokenRequest = new TokenRequest(URI.create(maskinportUrl), jwtBearerGrant);
//            parse = TokenResponse.parse(tokenRequest.toHTTPRequest().send());
//            if (!parse.indicatesSuccess()) {
//                var parseError = parse.toErrorResponse().toJSONObject().toJSONString();
//                log.error("Parse not successfull: {}", parseError);
//            }
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//        }
//        log.info("token: {} ", parse.toSuccessResponse().getTokens().getBearerAccessToken());
//        return  String.valueOf(parse.toSuccessResponse().getTokens().getBearerAccessToken());
//    }
}
