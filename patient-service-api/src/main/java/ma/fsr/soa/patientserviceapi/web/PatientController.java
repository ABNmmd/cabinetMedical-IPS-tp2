package ma.fsr.soa.patientserviceapi.web;

import lombok.AllArgsConstructor;
import ma.fsr.soa.cabinetrepo.model.Patient;
import ma.fsr.soa.patientserviceapi.dto.PatientUpdateDto;
import ma.fsr.soa.patientserviceapi.service.PatientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal/api/v1/patients")
@AllArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable Long id) throws Exception {
        return patientService.getPatientById(id);
    }

    @PostMapping
    public Patient createPatient(@RequestBody Patient patient) throws Exception {
        return patientService.createPatient(patient);
    }

    @PutMapping("/{id}")
    public Patient updatePatient(@PathVariable Long id, @RequestBody PatientUpdateDto patientDto) throws Exception {
        return patientService.updatePatient(id, patientDto);
    }

    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
    }
}
