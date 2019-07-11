package uz.gigalab.remonlineimport.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Services {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private Long id;

    @Column(unique = true)
    private String name;
    private Integer status;

    @ColumnTransformer(read = "type + 0")
    private Integer type;

}
