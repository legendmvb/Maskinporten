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



        String test1 = tokenService.getMaskinportenToken();
        System.out.println(test1);
    }
}

