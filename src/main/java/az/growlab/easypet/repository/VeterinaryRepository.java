package az.growlab.easypet.repository;

import az.growlab.easypet.domain.PetWalker;
import az.growlab.easypet.domain.User;
import az.growlab.easypet.domain.Veterinary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VeterinaryRepository extends JpaRepository<Veterinary, Long> {
    List<Veterinary> findAllByLocation(String location);

    List<Veterinary> findAllByNameAndSurname(String name, String surname);

    Optional<Veterinary> findByUser(User user);
}
