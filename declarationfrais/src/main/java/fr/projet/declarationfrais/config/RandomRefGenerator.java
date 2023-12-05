package fr.projet.declarationfrais.config;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomRefGenerator {

    public String generateRef() {
        Random random = new Random();
        int randomInt = random.nextInt(9000000) + 1000000;
        return "DEC" + randomInt;
    }
}
