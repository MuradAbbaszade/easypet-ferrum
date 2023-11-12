package az.growlab.easypet.repository;

import az.growlab.easypet.domain.PetWalkerAnnouncement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PetWalkerAnnouncementRepository extends JpaRepository<PetWalkerAnnouncement,Long> {
    List<PetWalkerAnnouncement> findAllBySuitableDate(Date date);
}
