package ma.fsr.soa.consultationserviceapi.service;

import lombok.AllArgsConstructor;
import ma.fsr.soa.cabinetrepo.model.Consultation;
import ma.fsr.soa.cabinetrepo.model.RendezVous;
import ma.fsr.soa.cabinetrepo.repository.ConsultationRepository;
import ma.fsr.soa.cabinetrepo.repository.RendezVousRepository;
import ma.fsr.soa.consultationserviceapi.dto.ConsultationDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConsultationService {

    private final ConsultationRepository repo;
    private final RendezVousRepository rendezVousRepository;

    public Consultation createConsultation(ConsultationDto consultationDto) throws Exception {
        // Le rendez-vous doit exister
        if (consultationDto.getRendezVousId() == null) {
            throw new Exception("Rendez-vous introuvable");
        }
        Optional<RendezVous> rendezVous = rendezVousRepository.findById(consultationDto.getRendezVousId());
        if (rendezVous.isEmpty()) {
            throw new Exception("Rendez-vous introuvable");
        }

        // La date de consultation est obligatoire
        if (consultationDto.getDateConsultation() == null) {
            throw new Exception("La date de consultation est obligatoire");
        }

        // La date de consultation doit être ≥ à la date du rendez-vous
        if (consultationDto.getDateConsultation().isBefore(rendezVous.get().getDateRdv())) {
            throw new Exception("Date de consultation invalide");
        }

        // Le rapport est obligatoire (au moins 10 caractères)
        if (consultationDto.getRapport() == null || consultationDto.getRapport().trim().length() < 10) {
            throw new Exception("Rapport de consultation insuffisant");
        }

        Consultation consultation = new Consultation();
        consultation.setDateConsultation(consultationDto.getDateConsultation());
        consultation.setRapport(consultationDto.getRapport());
        consultation.setRendezVous(rendezVous.get());

        return repo.save(consultation);
    }

    public List<Consultation> getAllConsultations() {
        return repo.findAll();
    }

    public Consultation getConsultationById(Long id) throws Exception {
        Optional<Consultation> consultation = repo.findById(id);
        if (consultation.isEmpty()) {
            throw new Exception("Consultation introuvable : id = " + id);
        }
        return consultation.get();
    }

    public Consultation getConsultationByRendezVousId(Long rendezVousId) throws Exception {
        Optional<RendezVous> rendezVous = rendezVousRepository.findById(rendezVousId);
        if (rendezVous.isEmpty()) {
            throw new Exception("Rendez-vous introuvable");
        }

        Optional<Consultation> consultation = repo.findByRendezVous(rendezVous.get());
        if (consultation.isEmpty()) {
            throw new Exception("Aucune consultation trouvée pour ce rendez-vous : id = " + rendezVousId);
        }
        return consultation.get();
    }

    public Consultation updateConsultation(Long id, ConsultationDto updatedConsultationDto) throws Exception {
        Consultation existingConsultation = repo.findById(id).orElse(null);
        if (existingConsultation == null) {
            throw new Exception("Consultation introuvable : id = " + id);
        }

        // Mettre à jour les champs

        if (updatedConsultationDto.getDateConsultation() != null && !updatedConsultationDto.getDateConsultation().equals(existingConsultation.getDateConsultation())) {
            // La date de consultation doit être ≥ à la date du rendez-vous
            if (updatedConsultationDto.getDateConsultation().isBefore(existingConsultation.getRendezVous().getDateRdv())) {
                throw new Exception("Date de consultation invalide");
            }
            existingConsultation.setDateConsultation(updatedConsultationDto.getDateConsultation());
        }

        if (updatedConsultationDto.getRapport() != null && !updatedConsultationDto.getRapport().equals(existingConsultation.getRapport())) {
            if (updatedConsultationDto.getRapport().trim().length() < 10) {
                throw new Exception("Rapport de consultation insuffisant");
            }
            existingConsultation.setRapport(updatedConsultationDto.getRapport());
        }

        if (updatedConsultationDto.getRendezVousId() != null && !updatedConsultationDto.getRendezVousId().equals(existingConsultation.getRendezVous().getId())) {
            Optional<RendezVous> rendezVous = rendezVousRepository.findById(updatedConsultationDto.getRendezVousId());
            if (rendezVous.isEmpty()) {
                throw new Exception("Rendez-vous introuvable");
            }
            // Vérifier que la date de consultation est compatible avec le nouveau rendez-vous
            if (existingConsultation.getDateConsultation().isBefore(rendezVous.get().getDateRdv())) {
                throw new Exception("Date de consultation invalide");
            }
            existingConsultation.setRendezVous(rendezVous.get());
        }

        return repo.save(existingConsultation);
    }

    public void deleteConsultation(Long id) {
        Consultation consultation = repo.findById(id).orElse(null);
        if (consultation != null) {
            repo.delete(consultation);
        }
    }
}
