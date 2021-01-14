package bean;

import entity.Role;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class RoleBean {

    @PersistenceContext(unitName = "project")
    private EntityManager manager;

    public Role getRole(long id) {
        return manager.find(Role.class, id);
    }

    public List<Role> getAllRoles() {
        return manager.createQuery("select r from Role r", Role.class).getResultList();
    }
}
