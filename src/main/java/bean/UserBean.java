package bean;

import entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Stateless
public class UserBean {

    @PersistenceContext(unitName = "project")
    private EntityManager manager;

    @Transactional(rollbackOn = Exception.class)
    public void addUser(User user) {
        manager.persist(user);
    }

    @Transactional(rollbackOn = Exception.class)
    public void updateUser(User user) {
        manager.merge(user);
    }

    public User getUser(long id) {
        return manager.find(User.class, id);
    }

    public List<User> getAllUsers() {
        return manager.createQuery("select u from User u", User.class).
                getResultList();
    }

}