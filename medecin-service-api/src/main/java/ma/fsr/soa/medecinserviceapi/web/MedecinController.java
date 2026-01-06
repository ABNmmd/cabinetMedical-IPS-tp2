package ma.fsr.soa.medecinserviceapi.web;

import lombok.AllArgsConstructor;
import ma.fsr.soa.cabinetrepo.model.Medecin;
import ma.fsr.soa.medecinserviceapi.dto.MedecinUpdateDto;
import ma.fsr.soa.medecinserviceapi.service.MedecinService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal/api/v1/medecins")
@AllArgsConstructor
public class MedecinController {

    private final MedecinService medecinService;

    @GetMapping
    public List<Medecin> getAllMedecins() {
        return medecinService.getAllMedecins();
    }

    @GetMapping("/{id}")
    public Medecin getMedecinById(@PathVariable Long id) throws Exception {
        return medecinService.getMedecinById(id);
    }

    @PostMapping
    public Medecin createMedecin(@RequestBody Medecin medecin) throws Exception {
        return medecinService.createMedecin(medecin);
    }

    @PutMapping("/{id}")
    public Medecin updateMedecin(@PathVariable Long id, @RequestBody MedecinUpdateDto medecinDto) throws Exception {
        return medecinService.updateMedecin(id, medecinDto);
    }

    @DeleteMapping("/{id}")
    public void deleteMedecin(@PathVariable Long id) {
        medecinService.deleteMedecin(id);
    }
}
