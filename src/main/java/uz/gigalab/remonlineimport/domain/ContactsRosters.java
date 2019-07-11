package uz.gigalab.remonlineimport.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "contacts_rosters")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class ContactsRosters {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id", updatable = false, nullable = false, unique=true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "contacts_id", nullable = false)
    @ToString.Exclude
    private Contacts contacts;

    private Long rostersId;
    private Long rostersTid;
    private String data;

    public static ContactsRosters createPhone(String data) {
        ContactsRosters rosters = new ContactsRosters();

        data = data.replaceAll("[^0-9]", "");

        rosters.setData(data);
        rosters.setRostersId(1L);
        rosters.setRostersTid(17L);
        return rosters;
    }

    public static ContactsRosters createEmail(String data) {
        ContactsRosters rosters = new ContactsRosters();
        rosters.setData(data);
        rosters.setRostersId(3L);
        rosters.setRostersTid(16L);
        return rosters;
    }

    public static ContactsRosters createAddress(String data) {
        ContactsRosters rosters = new ContactsRosters();
        rosters.setData(data);
        rosters.setRostersId(2L);
        rosters.setRostersTid(16L);
        return rosters;
    }

}
