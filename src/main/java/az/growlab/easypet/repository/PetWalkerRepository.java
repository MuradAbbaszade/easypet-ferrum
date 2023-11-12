package az.growlab.easypet.repository;

import az.growlab.easypet.domain.PetWalker;
import az.growlab.easypet.domain.User;
import az.growlab.easypet.domain.Veterinary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface PetWalkerRepository extends JpaRepository<PetWalker,Long> {
    List<PetWalker> findAllByLocation(String location);

    List<PetWalker> findAllByNameAndSurname(String name, String surname);

    Optional<PetWalker> findByUser(User user);
}
