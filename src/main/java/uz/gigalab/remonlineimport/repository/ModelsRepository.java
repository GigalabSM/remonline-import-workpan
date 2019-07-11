package uz.gigalab.remonlineimport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.gigalab.remonlineimport.domain.Models;
import uz.gigalab.remonlineimport.domain.ModelsBrands;

import java.util.Optional;

@Repository
public interface ModelsRepository extends JpaRepository<Models, Long> {

    @Query("FROM Models m WHERE m.name = ?1 AND m.gadgets.id = ?2 AND m.brands.id = ?3")
    Optional<Models> find(String modelName, Long gadgetsId, Long brandsId);
}
