package az.growlab.easypet.repository;

import az.growlab.easypet.domain.Customer;
import az.growlab.easypet.domain.PetWalker;
import az.growlab.easypet.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Optional<Customer> findByUser(User user);
}
