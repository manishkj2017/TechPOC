package shop.core.dao;

import java.util.List;

import shop.core.domain.Pet;
import shop.core.domain.PetInventory;
import shop.core.domain.PetOrder;
import shop.core.domain.PetOrderSummaryData;
import shop.core.domain.PetSaleSummaryData;

public interface PetDBInterface {
	public void insertOrder(PetOrder order);
	public void insertPet(Pet pet);
	public void insertInventory(PetInventory inventory);
	
	public int countOrders(String source, String status, String subStatus);
	public int countPets(String petType);
	public Pet getPet(int tag);

	public List<PetSaleSummaryData> getPetSaleSummary();
	public List<PetInventory> getPetsInventory();
	public List<PetOrderSummaryData> getPetOrderSummary();
}
