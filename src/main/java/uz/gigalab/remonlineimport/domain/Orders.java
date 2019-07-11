package uz.gigalab.remonlineimport.domain;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Orders {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
//    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "contacts_id")
    private Contacts contacts;

    @ManyToOne
    @JoinColumn(name = "models_id")
    private Models models;

    private Long servicesId;

    private Long createUid;
    private LocalDateTime createDate;

    private Integer priority;

    @Column(name = "isservice")
    private boolean isService = false;

    @Column(name = "iswarranty")
    private boolean isWarranty = false;

    @Column(name = "isclose")
    private boolean isClose = true;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bug_tid")
    private Texts bug;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "note_tid")
    private Texts note;

    private Long colorRid;
    private Long discountRid;
    private Short discountType;

    private Long oriented;

    private String imei;
    private String cond;
    private String appearance;
    private String complete;

    private Integer globalSid;
    private Integer clientSid;
    private Integer acceptanceSid;
    private Integer detailSid;
    private Integer repairSid;
    private Integer deliverySid;

    private Long acceptanceUid;
    private LocalDateTime acceptanceDate;

    private Long deliveryUid;
    private LocalDateTime deliveryDate;

    private Integer deliveryType;
    private LocalDate expectDate;

    @Column(name = "sumprice")
    private Double sumPrice;
    @Column(name = "sumdiscount")
    private Double sumDiscount;
    @Column(name = "sumtotal")
    private Double sumTotal;
    @Column(name = "sumexpense")
    private Double sumExpense;
    @Column(name = "sumpaid")
    private Double sumPaid;
    @Column(name = "sumincome")
    private Double sumIncome;

    private Integer guaranteeRid;

    // @Fetch(value = FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    @Builder.Default
    private List<OrdersFinances> ordersFinances = new ArrayList<>();

    @OneToOne(mappedBy = "orders", cascade = CascadeType.ALL)
    private OrdersDenials ordersDenials;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    @Builder.Default
    private List<OrdersCosts> ordersCosts = new ArrayList<>();
}


















