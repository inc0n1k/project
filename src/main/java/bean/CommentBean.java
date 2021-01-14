package bean;

import entity.Comment;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Stateless
public class CommentBean {

    @PersistenceContext(unitName = "project")
    private EntityManager manager;

    @Transactional(rollbackOn = Exception.class)
    public void addComment(Comment comment) {
        manager.persist(comment);
    }

    @Transactional(rollbackOn = Exception.class)
    public void removeComment(Comment comment) {
        manager.remove(manager.contains(comment) ? comment : manager.merge(comment));
    }

    public Comment getComment(long id) {
        return manager.find(Comment.class, id);
    }

    public List<Comment> getCommentsForPublication(long id) {
        return manager.createQuery("select c from Comment c where c.publication.id = ?1", Comment.class).
                setParameter(1, id).
                getResultList();
    }
}
