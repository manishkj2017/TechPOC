package shop.server.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import shop.core.domain.Pet;
import shop.core.domain.PetInventory;
import shop.core.domain.PetOrder;
import shop.core.domain.PetOrderSummaryData;
import shop.core.domain.PetSaleSummaryData;

@Repository
public class PetDBInterfaceImpl implements PetDBInterface {

	@PersistenceContext
	EntityManager em;
	
	@Autowired
	PetRepository petRepository;
	
	@Autowired
	PetInventoryRepository petInventory;
	
	
	@Transactional
	@Override
	public void insertOrder(PetOrder order) {
		////this is an example of using plain jpa
		em.persist(order);
		em.flush();

	}

	@Transactional
	@Override
	public void insertPet(Pet pet) {
		//this is an example of using Spring data jpa instead of plain jpa
		petRepository.save(pet);
		
		//below is the approach without Spring data jpa
		//em.persist(pet);
		//em.flush();

	}
	
	@Transactional
	@Override
	public void insertInventory(PetInventory inventory) {
		//this is an example of using Spring data jpa instead of plain jpa
		petInventory.saveAndFlush(inventory);
		
		//below is the approach without Spring data jpa
		//em.persist(inventory);
		//em.flush();
		
	}

	@Override
	public int countOrders(String source, String status, String subStatus) {
		Query query = em.createQuery("select count(o) "
				+ "from PetOrder o where o.orderSource=:source and o.status=:status and o.statusReason=:substatus");
		query.setParameter("source", source);
		query.setParameter("status", status);
		query.setParameter("substatus", subStatus);
		return ((Long)query.getSingleResult()).intValue();
	}

	@Override
	public Pet getPet(int tag) {
		return em.find(Pet.class, tag);
	}

	@Override
	public int countPets(String petType) {
		Query query = em.createQuery("select count(p.tag) from Pet p where p.name =:petType ");
		query.setParameter("petType", petType);
		Long count =  (Long)query.getSingleResult();
		return count.intValue();
	}

	@Override
	public List<PetSaleSummaryData> getPetSaleSummary() {
		
		List<PetSaleSummaryData> saleSummaries = new ArrayList<PetSaleSummaryData>();

		Query query = em.createNamedQuery(Pet.FIND_PET_SALE_SUMMARY);
		List <Object[]>resultList = query.getResultList();

		for(int i=0; i< resultList.size(); i++){
			Object[] values = resultList.get(i);
			PetSaleSummaryData saleSummary = new PetSaleSummaryData((String) values[0],(String) values[1],
														(Long) values[2],(Integer) values[3],
														(BigDecimal) values[4], new BigDecimal((Integer) values[5]),
														(Integer) values[6]);
			
			
			saleSummaries.add(saleSummary);
		}
		
		
		return saleSummaries;
	}

	@Override
	public List<PetInventory> getPetsInventory() {
		
		//this is an example of using Spring data jpa instead of plain jpa for a customize query
		return petInventory.getPetsInventory();
		
		//below is the approach without Spring data jpa
		/*List<PetInventory> inventory = new ArrayList <PetInventory> ();
		TypedQuery<PetInventory> query = em.createNamedQuery(PetInventory.ALL_PETS_INVENTORY, PetInventory.class);
		inventory = query.getResultList();
		return inventory;*/
		
		
	}

	@Override
	public List<PetOrderSummaryData> getPetOrderSummary() {
		List<PetOrderSummaryData> orderSummaries = new ArrayList<PetOrderSummaryData>();
		
		Query query = em.createNamedQuery(PetOrder.FIND_PET_ORDER_SUMMARY);
		
		List <Object[]>resultList = query.getResultList();

		for(int i=0; i< resultList.size(); i++){
			Object[] values = resultList.get(i);
			PetOrderSummaryData orderSummary = new PetOrderSummaryData((String) values[0], 
																	   (String) values[1],
																	   (String) values[2], 
																	   (String) values[3],
																	   ((Long) values[4]).intValue());
			
			orderSummaries.add(orderSummary);
		}
		
		return orderSummaries;
		
	}

	

}
