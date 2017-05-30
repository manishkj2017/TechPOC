package shop.gui.services;

import java.util.List;

import shop.core.domain.PetSaleSummaryData;


public interface PetSaleSummaryService {
	
	public List<PetSaleSummaryData> getPetSaleSummary();
	public boolean isShopClosed();
}
