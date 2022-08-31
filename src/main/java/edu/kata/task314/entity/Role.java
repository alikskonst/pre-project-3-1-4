package edu.kata.task314.entity;

import edu.kata.task314.entity.parent.NameEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "role")
public class Role extends NameEntity implements GrantedAuthority {

//    @JsonBackReference
//    @ManyToMany(mappedBy = "roles")
//    private Set<User> users;

    @Override
    public String getAuthority() {
        return getName();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Role role = (Role) obj;
        return Objects.equals(getId(), role.getId()) && Objects.equals(getName(), role.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}
