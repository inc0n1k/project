package entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role {
    //**********************************************
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role;
    //**********************************************
    @OneToMany(mappedBy = "role")
    private List<User> users;
    //**********************************************

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<User> getUsers() {
        return users;
    }
}
