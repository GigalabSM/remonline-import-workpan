package uz.gigalab.remonlineimport.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"ordersId"})
public class OrdersDenials {

    @Id
    @Column(name="orders_id")
    private Long ordersId;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn(name="orders_id", referencedColumnName="id")
    @ToString.Exclude
    private Orders orders;

    private Integer denialsRid;
    private Long createUid;
    private LocalDateTime createDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "note_tid")
    private Texts note;

}
