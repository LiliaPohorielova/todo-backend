package ua.com.alevel.entity;

import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Setter
@NoArgsConstructor
public class Priority extends BaseEntity {

    private Long id;
    private String title;
    private String color;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    @Basic
    @Column(name = "color")
    public String getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Priority priority = (Priority) o;
        return getId() != null && Objects.equals(getId(), priority.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
