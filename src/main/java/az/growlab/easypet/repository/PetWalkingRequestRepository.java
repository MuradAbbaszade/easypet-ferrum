package az.growlab.easypet.repository;

import az.growlab.easypet.domain.PetWalkingRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PetWalkingRequestRepository extends JpaRepository<PetWalkingRequest,Long> {
    List<PetWalkingRequest> findAllBySuitableDate(Date date);
}
