package ma.fsr.soa.patientserviceapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;

@SpringBootApplication(scanBasePackages = "ma.fsr.soa")
@EntityScan(basePackages = "ma.fsr.soa.cabinetrepo.model")
public class PatientServiceApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientServiceApiApplication.class, args);
    }

}
