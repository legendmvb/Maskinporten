package no.brreg.konkurs.maskinporten;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.util.Date;
import java.util.UUID;

@Component
@Slf4j
public class JwtService {

    @Value("${maskinporten.privatkey}")
    private String privatekey;

    @Value("${maskinporten.aud}")
    private String aud;

    @Value("${maskinporten.issuer}")
    private String issuer;

    @Value("${maskinporten.scope}")
    private String scope;

    public SignedJWT createSignedJWT() {
        try {
            RSAKey senderJWK = RSAKey.parse(privatekey);
            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .audience(aud)
                    .issuer(issuer)
                    .claim("scope", scope)
                    .jwtID(UUID.randomUUID().toString())
                    .issueTime(new Date(Clock.systemUTC().millis()))
                    .expirationTime(new Date(Clock.systemUTC().millis() + 120000))
                    .build();

            SignedJWT signedJWT = new SignedJWT(
                    new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(senderJWK.getKeyID()).build(),
                    claims);

            JWSSigner signer = new RSASSASigner(senderJWK);
            signedJWT.sign(signer);
            return signedJWT;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
