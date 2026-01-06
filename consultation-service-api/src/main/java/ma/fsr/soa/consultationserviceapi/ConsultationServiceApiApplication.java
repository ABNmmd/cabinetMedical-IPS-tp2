package ma.fsr.soa.consultationserviceapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;

@SpringBootApplication(scanBasePackages = "ma.fsr.soa")
@EntityScan(basePackages = "ma.fsr.soa.cabinetrepo.model")
public class ConsultationServiceApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsultationServiceApiApplication.class, args);
    }

}
