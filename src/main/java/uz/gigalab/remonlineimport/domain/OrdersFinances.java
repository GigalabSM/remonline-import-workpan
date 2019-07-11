package uz.gigalab.remonlineimport.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class OrdersFinances {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "orders_id")
    @ToString.Exclude
    private Orders orders;

    private LocalDate paymentDate;
    private double amount;

    @Column(nullable = false, updatable = false)
    private Long createUid;

    private LocalDateTime createDate;

    @Column(nullable = false, updatable = false)
    private Long updateUid;
    private LocalDateTime updateDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "note_tid")
    private Texts note;

















}
