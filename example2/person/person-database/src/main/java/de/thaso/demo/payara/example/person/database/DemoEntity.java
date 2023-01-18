package de.thaso.demo.payara.example.person.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * DemoEntity
 */
@Entity
@Table(name = "T_DEMO")
@NamedQueries({
//        @NamedQuery(name = DemoEntity.FIND_ACTIVE_DEMOS, query = "select o from DemoEntity o where o.active is not null and o.active is true order by o.name asc"),
        @NamedQuery(name = DemoEntity.FIND_ACTIVE_DEMOS, query = "select o from DemoEntity o"),
})
public class DemoEntity extends EntityBase {

    private static final long serialVersionUID = -8836233993826476466L;

    public static final String FIND_ACTIVE_DEMOS = "FIND_ACTIVE_DEMOS";

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DemoSequence")
//    @SequenceGenerator(name = "DemoSequence", sequenceName = "SEQ_ID_DEMO", allocationSize = 10)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    @NotNull
    private String name;

    @Column(name = "ACTIVE")
    private Boolean active;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
