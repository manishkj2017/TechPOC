package shop.core.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import shop.core.domain.Pet;
import shop.core.domain.PetOrder;

@Repository
public class PetDBInterfaceImpl implements PetDBInterface {

	@PersistenceContext
	EntityManager em;
	
	
	@Transactional
	@Override
	public void insertOrder(PetOrder order) {
		em.persist(order);

	}

	@Transactional
	@Override
	public void insertPet(Pet pet) {
		em.persist(pet);

	}

	@Override
	public int countOrders(String source, String status, String subStatus) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Pet getPet(int tag) {
		return em.find(Pet.class, tag);
	}

}
