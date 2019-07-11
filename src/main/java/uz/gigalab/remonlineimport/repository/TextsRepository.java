package uz.gigalab.remonlineimport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.gigalab.remonlineimport.domain.Texts;

@Repository
public interface TextsRepository extends JpaRepository<Texts, Long> {


}
