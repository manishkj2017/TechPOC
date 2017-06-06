package shop.server.services;

import java.util.List;

import shop.core.domain.PetInventory;
import shop.core.domain.PetOrderSummaryData;
import shop.core.domain.PetSaleSummaryData;

public interface ShopDataService {
	public List<PetSaleSummaryData> getPetSaleSummary();
	public List<PetOrderSummaryData> getPetOrderSummary();
	public List<PetInventory> getPetInventory();
	
}
