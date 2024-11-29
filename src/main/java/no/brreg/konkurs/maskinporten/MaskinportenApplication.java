package no.brreg.konkurs.maskinporten;

import org.apache.hc.client5.http.fluent.Form;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.client5.http.fluent.Response;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicClassicHttpResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.time.Clock;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class MaskinportenApplication {

    public static void main(String[] args) {

        SpringApplication.run(MaskinportenApplication.class, args);


    }

}
