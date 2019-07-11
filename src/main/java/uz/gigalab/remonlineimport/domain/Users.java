package uz.gigalab.remonlineimport.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table
@Data
@EqualsAndHashCode(of = {"id"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private Long id;

    private LocalDateTime createDate;
    private Long createUid;

    private String name;
    private String lastname;
    private String patronymic;

    private String login;
    private String passwd;
    private String salt;

    @Column(name = "isadmin")
    private boolean isAdmin;

    private Long authsGroupsId;
    private Long servicesId;
    private Long statusId;
    private Long rostersPostsId;
    private Long localesId;

    private LocalDate careerStart;

    private String note;

}
