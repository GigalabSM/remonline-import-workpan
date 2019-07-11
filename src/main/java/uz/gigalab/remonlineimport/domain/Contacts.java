package uz.gigalab.remonlineimport.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contacts")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Contacts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id", updatable = false, nullable = false, unique=true)
    private Long id;

    private String name;
    private Long createUsersId;

    @CreatedDate
    @Column(name = "date_create", nullable = false, updatable = false)
    private LocalDateTime dateCreate;

    @LastModifiedDate
    @Column(name = "date_update", nullable = false, updatable = false)
    private LocalDateTime dateUpdate;

    //@Enumerated(EnumType.STRING)
    private String types = "client";
    private Short status = 1;

    private Long updateUsersId;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "contacts",
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private List<ContactsRosters> rosters = new ArrayList<>();

    public void addRosters(ContactsRosters rosters) {
        rosters.setContacts(this);
        this.rosters.add(rosters);
    }
}
