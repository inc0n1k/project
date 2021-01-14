package entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    //**********************************************
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;

    private String password;

    private String surname;

    private String name;

    /*@CreationTimestamp
    private Date registration_date;*/

    private Timestamp registration_date = new Timestamp(new GregorianCalendar().getTimeInMillis());

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    private Boolean blocked;
    //**********************************************
    @OneToMany(mappedBy = "user")
    private List<Rating> rating;

    @OneToMany(mappedBy = "user")
    private List<Publication> publications;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    //**********************************************
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   /* public Date getRegistration_date() {
        return registration_date;
    }

    public void setRegistration_date(Date registration_date) {
        this.registration_date = registration_date;
    }*/

    public Timestamp getRegistration_date() {
        return registration_date;
    }

   /* public void setRegistration_date(Timestamp registration_date) {
        this.registration_date = registration_date;
    }*/

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    public List<Rating> getRating() {
        return rating;
    }

    public List<Publication> getPublications() {
        return publications;
    }

    public List<Comment> getComments() {
        return comments;
    }
}
