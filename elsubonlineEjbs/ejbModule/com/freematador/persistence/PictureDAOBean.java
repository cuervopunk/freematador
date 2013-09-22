package com.freematador.persistence;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import com.freematador.domain.Picture;
import com.freematador.domain.User;

/**
 * Session Bean implementation class PictureDAOBean
 */
@Stateless
@LocalBean
public class PictureDAOBean implements PictureDAO {

	@PersistenceContext(unitName="ElSubOnlineUNIT" )
	private javax.persistence.EntityManager em;

    /**
     * Default constructor. 
     */
    public PictureDAOBean() {
    }

	@Override
	public void insert(Picture entity) throws PersistenceException {
		try {
			em.persist(entity);
			em.flush();
		} catch (Throwable ex) {
			System.out.println("--------------------------> "+ex.getCause());
			throw new PersistenceException("Ha ocurrido un error creando la Picture");
		}
	}

	@Override
	public void delete(Integer id) throws PersistenceException {
		try {
			Picture u = em.find(Picture.class, id);
			em.remove(u);
		} catch (Throwable ex) {
			throw new PersistenceException("Ha ocurrido un error removiendo la Picture");
		}
	}

}
