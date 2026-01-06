package ma.fsr.soa.medecinserviceapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;

@SpringBootApplication(scanBasePackages = "ma.fsr.soa")
@EntityScan(basePackages = "ma.fsr.soa.cabinetrepo.model")
public class MedecinServiceApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedecinServiceApiApplication.class, args);
    }

}
