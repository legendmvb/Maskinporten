package no.brreg.konkurs.maskinporten;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MaskinportenTest {
    @Autowired
    TokenService tokenService;

    @Test
    void maskinporten() throws Exception {

       String test = tokenService.hentMaskinportenToken("brreg:konkurs.info");





        //String test1 = tokenService.validateMaskinportenToken("brreg:konkursinformasjon");
        //System.out.println(test);
        System.out.println(test);
    }
}

