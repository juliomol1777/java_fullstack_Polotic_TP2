
package persistencia;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import logica.Perros;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author Julio
 */
public class PerrosJpaController implements Serializable {

    public PerrosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;
    
    //CREO EL CONSTRUCTOR DONDE RELACIONO EL EMF CON MI UNIDAD DE PERSISTENCIA
    
    public PerrosJpaController() {
        emf = Persistence.createEntityManagerFactory("Molina_julio_tpo2PU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Perros perros) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(perros);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Perros perros) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            perros = em.merge(perros);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = perros.getNroCliente();
                if (findPerros(id) == null) {
                    throw new NonexistentEntityException("The perros with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Perros perros;
            try {
                perros = em.getReference(Perros.class, id);
                perros.getNroCliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The perros with id " + id + " no longer exists.", enfe);
            }
            em.remove(perros);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Perros> findPerrosEntities() {
        return findPerrosEntities(true, -1, -1);
    }

    public List<Perros> findPerrosEntities(int maxResults, int firstResult) {
        return findPerrosEntities(false, maxResults, firstResult);
    }

    private List<Perros> findPerrosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Perros.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Perros findPerros(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Perros.class, id);
        } finally {
            em.close();
        }
    }

    public int getPerrosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Perros> rt = cq.from(Perros.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
