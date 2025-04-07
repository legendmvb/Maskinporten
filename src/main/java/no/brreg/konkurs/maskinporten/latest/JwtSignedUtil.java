package no.brreg.konkurs.maskinporten.latest;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;

import java.time.Clock;
import java.util.Date;
import java.util.UUID;

@Slf4j
public class JwtSignedUtil {
    private static final String CLAIM_SCOPE = "scope";
    private static final int TIME_MILLIS = 1200;

    public static String createSignedJWT(String aud, String iss, String scope, String secret) {
        try {
            RSAKey senderJWK = RSAKey.parse(secret);
            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .audience(aud)
                    .issuer(iss)
                    .claim(CLAIM_SCOPE, scope)
                    .jwtID(UUID.randomUUID().toString())
                    .issueTime(new Date(Clock.systemUTC().millis()))
                    .expirationTime(new Date(Clock.systemUTC().millis() + TIME_MILLIS))
                    .build();

            SignedJWT signedJWT = new SignedJWT(
                    new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(senderJWK.getKeyID()).build(),
                    claims);

            JWSSigner signer = new RSASSASigner(senderJWK);
            signedJWT.sign(signer);
            return signedJWT.serialize();

        } catch (Exception e) {
            log.info("Oppretting av SignedJWT feilet", e);
        }
        return null;
    }
}
