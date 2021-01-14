package bean;

import entity.Publication;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Stateless
public class PublicationBean {

    @PersistenceContext(unitName = "project")
    private EntityManager manager;

    @Transactional(rollbackOn = Exception.class)
    public void addPublication(Publication publication) {
        manager.persist(publication);
    }

    @Transactional(rollbackOn = Exception.class)
    public void removePublication(Publication publication) {
        manager.remove(manager.contains(publication) ? publication : manager.merge(publication));
    }

    @Transactional(rollbackOn = Exception.class)
    public void updatePublication(Publication publication) {
        manager.merge(publication);
    }

    public List<Publication> getAllPublications(Boolean state) {
        return manager.createQuery("select p from Publication  p where p.state = ?1", Publication.class).
                setParameter(1, state).
                getResultList();
    }

    public List<Publication> getTop10Publications() {
        return manager.createQuery("select p from Publication p left join Rating r on p = r.publication where p.state = true group by p.id order by avg(r.rating) desc ", Publication.class).
                setMaxResults(10).
                getResultList();
    }

    public Publication getPublication(long id) {
        return manager.find(Publication.class, id);
    }

}
