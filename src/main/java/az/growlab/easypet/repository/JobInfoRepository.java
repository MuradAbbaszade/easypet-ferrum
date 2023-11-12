package az.growlab.easypet.repository;

import az.growlab.easypet.domain.JobInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobInfoRepository extends JpaRepository<JobInfo,Long> {
}
