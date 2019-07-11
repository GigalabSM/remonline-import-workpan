package uz.gigalab.remonlineimport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.gigalab.remonlineimport.domain.ModelsBrands;
import uz.gigalab.remonlineimport.domain.ModelsGadgets;

import java.util.Optional;

@Repository
public interface ModelsBrandsRepository extends JpaRepository<ModelsBrands, Long> {

    Optional<ModelsBrands> findByName(String name);
}
