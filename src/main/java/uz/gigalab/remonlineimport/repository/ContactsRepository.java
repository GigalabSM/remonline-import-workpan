package uz.gigalab.remonlineimport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.gigalab.remonlineimport.domain.Contacts;

@Repository
public interface ContactsRepository extends JpaRepository<Contacts, Long> {


}
