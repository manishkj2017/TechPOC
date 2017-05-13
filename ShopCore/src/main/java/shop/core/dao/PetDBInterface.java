package shop.core.dao;

import shop.core.domain.Pet;
import shop.core.domain.PetOrder;

public interface PetDBInterface {
	public void insertOrder(PetOrder order);
	public void insertPet(Pet pet);
	public int countOrders(String source, String status, String subStatus);
	public Pet getPet(int tag);
}
