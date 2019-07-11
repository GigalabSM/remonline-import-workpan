package uz.gigalab.remonlineimport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.gigalab.remonlineimport.domain.OrdersDenials;

@Repository
public interface OrdersDenialsRepository extends JpaRepository<OrdersDenials, Long> {


}
