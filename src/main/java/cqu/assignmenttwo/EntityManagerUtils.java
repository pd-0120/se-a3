package cqu.assignmenttwo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 *
 * @author PJ
 */
public class EntityManagerUtils {
    public final EntityManager em;

    public EntityManagerUtils() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("cqu_drs");
        em = emf.createEntityManager();
    }

    public EntityManager getEm() {
        return em;
    }

}
