package ma.fsr.soa.rendezvousserviceapi.service;

import lombok.AllArgsConstructor;
import ma.fsr.soa.cabinetrepo.model.Medecin;
import ma.fsr.soa.cabinetrepo.model.Patient;
import ma.fsr.soa.cabinetrepo.model.RendezVous;
import ma.fsr.soa.cabinetrepo.repository.MedecinRepository;
import ma.fsr.soa.cabinetrepo.repository.PatientRepository;
import ma.fsr.soa.cabinetrepo.repository.RendezVousRepository;
import ma.fsr.soa.rendezvousserviceapi.dto.RendezVousDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RendezVousService {

    private final RendezVousRepository repo;
    private final PatientRepository patientRepository;
    private final MedecinRepository medecinRepository;

    private static final String[] VALID_STATUS = {"PLANIFIE", "ANNULE", "TERMINE"};
    private static final String DEFAULT_STATUS = "PLANIFIE";

    public RendezVous createRendezVous(RendezVousDto rendezVousDto) throws Exception {
        // La date du rendez-vous doit être postérieure à la date actuelle
        if (rendezVousDto.getDateRdv() == null || !rendezVousDto.getDateRdv().isAfter(LocalDate.now())) {
            throw new Exception("La date du rendez-vous doit être future");
        }

        // Le patient doit exister
        if (rendezVousDto.getPatientId() == null) {
            throw new Exception("Patient introuvable");
        }
        Optional<Patient> patient = patientRepository.findById(rendezVousDto.getPatientId());
        if (patient.isEmpty()) {
            throw new Exception("Patient introuvable");
        }

        // Le médecin doit exister
        if (rendezVousDto.getMedecinId() == null) {
            throw new Exception("Médecin introuvable");
        }
        Optional<Medecin> medecin = medecinRepository.findById(rendezVousDto.getMedecinId());
        if (medecin.isEmpty()) {
            throw new Exception("Médecin introuvable");
        }

        // Statut initial par défaut : PLANIFIE
        String status = rendezVousDto.getStatus();
        if (status == null || status.isEmpty()) {
            status = DEFAULT_STATUS;
        } else {
            // Statuts autorisés uniquement : PLANIFIE, ANNULE, TERMINE
            boolean validStatus = false;
            for (String validStatusValue : VALID_STATUS) {
                if (validStatusValue.equals(status)) {
                    validStatus = true;
                    break;
                }
            }
            if (!validStatus) {
                throw new Exception("Statut invalide. Valeurs possibles : PLANIFIE, ANNULE, TERMINE");
            }
        }

        RendezVous rendezVous = new RendezVous();
        rendezVous.setDateRdv(rendezVousDto.getDateRdv());
        rendezVous.setStatus(status);
        rendezVous.setPatient(patient.get());
        rendezVous.setMedecin(medecin.get());

        return repo.save(rendezVous);
    }

    public List<RendezVous> getAllRendezVous() {
        return repo.findAll();
    }

    public RendezVous getRendezVousById(Long id) throws Exception {
        Optional<RendezVous> rendezVous = repo.findById(id);
        if (rendezVous.isEmpty()) {
            throw new Exception("Rendez-vous introuvable : id = " + id);
        }
        return rendezVous.get();
    }

    public RendezVous updateRendezVous(Long id, RendezVousDto updatedRendezVousDto) throws Exception {
        RendezVous existingRendezVous = repo.findById(id).orElse(null);
        if (existingRendezVous == null) {
            throw new Exception("Rendez-vous introuvable : id = " + id);
        }

        // Mettre à jour les champs
        if (updatedRendezVousDto.getDateRdv() != null && !updatedRendezVousDto.getDateRdv().equals(existingRendezVous.getDateRdv())) {
            if (!updatedRendezVousDto.getDateRdv().isAfter(LocalDate.now())) {
                throw new Exception("La date du rendez-vous doit être future");
            }
            existingRendezVous.setDateRdv(updatedRendezVousDto.getDateRdv());
        }

        if (updatedRendezVousDto.getStatus() != null && !updatedRendezVousDto.getStatus().equals(existingRendezVous.getStatus())) {
            boolean validStatus = false;
            for (String status : VALID_STATUS) {
                if (status.equals(updatedRendezVousDto.getStatus())) {
                    validStatus = true;
                    break;
                }
            }
            if (!validStatus) {
                throw new Exception("Statut invalide. Valeurs possibles : PLANIFIE, ANNULE, TERMINE");
            }
            existingRendezVous.setStatus(updatedRendezVousDto.getStatus());
        }

        if (updatedRendezVousDto.getPatientId() != null && !updatedRendezVousDto.getPatientId().equals(existingRendezVous.getPatient().getId())) {
            Optional<Patient> patient = patientRepository.findById(updatedRendezVousDto.getPatientId());
            if (patient.isEmpty()) {
                throw new Exception("Patient introuvable");
            }
            existingRendezVous.setPatient(patient.get());
        }

        if (updatedRendezVousDto.getMedecinId() != null && !updatedRendezVousDto.getMedecinId().equals(existingRendezVous.getMedecin().getId())) {
            Optional<Medecin> medecin = medecinRepository.findById(updatedRendezVousDto.getMedecinId());
            if (medecin.isEmpty()) {
                throw new Exception("Médecin introuvable");
            }
            existingRendezVous.setMedecin(medecin.get());
        }

        return repo.save(existingRendezVous);
    }

    public void deleteRendezVous(Long id) {
        RendezVous rendezVous = repo.findById(id).orElse(null);
        if (rendezVous != null) {
            repo.delete(rendezVous);
        }
    }

    public List<RendezVous> getRendezVousByPatientId(Long patientId) throws Exception {
        Optional<Patient> patient = patientRepository.findById(patientId);
        if (patient.isEmpty()) {
            throw new Exception("Patient introuvable : id = " + patientId);
        }
        return repo.findByPatient(patient.get());
    }

    public List<RendezVous> getRendezVousByMedecinId(Long medecinId) throws Exception {
        Optional<Medecin> medecin = medecinRepository.findById(medecinId);
        if (medecin.isEmpty()) {
            throw new Exception("Médecin introuvable : id = " + medecinId);
        }
        return repo.findByMedecin(medecin.get());
    }

    public RendezVous updateRendezVousStatus(Long id, String status) throws Exception {
        RendezVous existingRendezVous = repo.findById(id).orElse(null);
        if (existingRendezVous == null) {
            throw new Exception("Rendez-vous introuvable : id = " + id);
        }

        // Valider le statut
        boolean validStatus = false;
        for (String validStatusValue : VALID_STATUS) {
            if (validStatusValue.equals(status)) {
                validStatus = true;
                break;
            }
        }
        if (!validStatus) {
            throw new Exception("Statut invalide. Valeurs possibles : PLANIFIE, ANNULE, TERMINE");
        }

        existingRendezVous.setStatus(status);
        return repo.save(existingRendezVous);
    }
}
