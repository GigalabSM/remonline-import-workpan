package uz.gigalab.remonlineimport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.gigalab.remonlineimport.domain.Services;

@Repository
public interface ServicesRepository extends JpaRepository<Services, Long> {

    Services findFirstByStatusAndType(int status, int type);


}
