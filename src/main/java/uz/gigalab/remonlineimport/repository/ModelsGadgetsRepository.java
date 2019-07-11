package uz.gigalab.remonlineimport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.gigalab.remonlineimport.domain.Contacts;
import uz.gigalab.remonlineimport.domain.ModelsGadgets;

import java.util.Optional;

@Repository
public interface ModelsGadgetsRepository extends JpaRepository<ModelsGadgets, Long> {

    Optional<ModelsGadgets> findByName(String name);
}
