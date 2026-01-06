package ma.fsr.soa.rendezvousserviceapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;

@SpringBootApplication(scanBasePackages = "ma.fsr.soa")
@EntityScan(basePackages = "ma.fsr.soa.cabinetrepo.model")
public class RendezvousServiceApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RendezvousServiceApiApplication.class, args);
    }

}
