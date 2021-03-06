package shop.server.services;

import java.util.Collection;
import java.util.concurrent.LinkedBlockingQueue;

import shop.core.domain.Pet;

public interface SaleService {
	public void updateSale(Pet pet);
	public LinkedBlockingQueue<Pet> returnSaleData();
	public void printSaleSummary();
	public Pet getPetByTag(int tag);
	public Collection<Pet> getInMemoryStorePetSaleData();
}
