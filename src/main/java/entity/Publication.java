package entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.List;

@Entity
@Table(name = "publications")
public class Publication {
    //**********************************************
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Boolean state;

    private String title;

    private String content;

    /*@CreationTimestamp
    private Date public_date;*/

    private Timestamp public_date = new Timestamp(new GregorianCalendar().getTimeInMillis());
    //**********************************************
    @OneToMany(mappedBy = "publication")
    private List<Rating> ratings;

    @OneToMany(mappedBy = "publication")
    private List<Comment> comments;

    //**********************************************
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /*public Date getPublic_date() {
        return public_date;
    }

    public void setPublic_date(Date public_date) {
        this.public_date = public_date;
    }*/

    public Timestamp getPublic_date() {
        return public_date;
    }

   /* public void setPublic_date(Timestamp public_date) {
        this.public_date = public_date;
    }*/

    public List<Rating> getRatings() {
        return ratings;
    }

    public List<Comment> getComments() {
        return comments;
    }
}
