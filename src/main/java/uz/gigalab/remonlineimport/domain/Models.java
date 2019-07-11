package uz.gigalab.remonlineimport.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Models {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private Long id;

    @Column(length = 150)
    private String name;
    private Short status = 1;

    @ManyToOne
    @JoinColumn(name = "models_gadgets_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ModelsGadgets gadgets;

    @ManyToOne
    @JoinColumn(name = "models_brands_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ModelsBrands brands;

    public Models(String name, ModelsGadgets gadgets, ModelsBrands brands) {
        this.name = name;
        this.gadgets = gadgets;
        this.brands = brands;
    }
}
