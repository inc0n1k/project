package entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.GregorianCalendar;

@Entity
@Table(name = "comments")
public class Comment {
    //**********************************************
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "publication_id")
    private Publication publication;

    private String comment;

    /*@CreationTimestamp
    private Date comment_date;*/
    private Timestamp comment_date = new Timestamp(new GregorianCalendar().getTimeInMillis());
    //**********************************************
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    /*public Date getComment_date() {
        return comment_date;
    }

    public void setComment_date(Date comment_date) {
        this.comment_date = comment_date;
    }*/

    public Timestamp getComment_date() {
        return comment_date;
    }
}
