package bean;

import entity.Rating;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Stateless
public class RatingBean {

    @PersistenceContext(unitName = "project")
    private EntityManager manager;

    @Transactional(rollbackOn = Exception.class)
    public void addRating(Rating rating) {
        manager.persist(rating);
    }

    public Double getAvgRatingForPublication(long id) {
        return manager.createQuery("select avg(r.rating) from Rating r where r.publication.id= ?1", Double.class).
                setParameter(1, id).
                getSingleResult();
    }

    public List getRatingsForPublication(long id) {
        return manager.createQuery("select r from Rating r where r.publication.id = ?1 group by r.user.id", Rating.class).
                setParameter(1, id).
                getResultList();
    }
}
