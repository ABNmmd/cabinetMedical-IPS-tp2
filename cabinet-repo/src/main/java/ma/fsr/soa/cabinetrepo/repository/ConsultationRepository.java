package ma.fsr.soa.cabinetrepo.repository;

import ma.fsr.soa.cabinetrepo.model.Consultation;
import ma.fsr.soa.cabinetrepo.model.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConsultationRepository extends JpaRepository<Consultation,Long> {
    Optional<ma.fsr.soa.cabinetrepo.model.Consultation> findByRendezVous(RendezVous rendezVous);
}
