package uz.gigalab.remonlineimport.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class ModelsGadgets {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private Long id;

    @Column(length = 70, unique = true)
    private String name;
    private Short status = 1;
    private Short sort = 150;

    public ModelsGadgets(String name) {
        this.name = name;
    }

    //    @OneToMany(mappedBy = "gadgets")
//    private List<Models> models = new ArrayList<>();
}
