package edu.kata.task314.entity;

import edu.kata.task314.entity.parent.NameEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "role", uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
public class Role extends NameEntity implements GrantedAuthority {

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"),
            foreignKey = @ForeignKey(name = "fk_role_to_user")
    )
    private List<User> userList;

    //------------------------------------------------------------------------------------------------------------------

    @Override
    public String getAuthority() {
        return getName();
    }

    //------------------------------------------------------------------------------------------------------------------

    // на случай сбора в Set
    // see: edu.kata.task312.User

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Role role = (Role) obj;
        return getId().equals(role.getId()) && getName().equals(role.getName());
    }

    @Override
    public int hashCode() {
        int hash = 11;
        hash = 31 * hash + getId().intValue();
        hash = 31 * hash + (getName() == null ? 0 : getName().hashCode());
        return hash;
    }
}