package shop.core.services;

import java.util.ArrayDeque;
import java.util.Map;

public interface InventoryService {
	public boolean isStockAvailable(String stockType);
	public boolean isAnyStockAvailable();
	public void buildInventory();
	public void printInventory();
	public Map<String, ArrayDeque<Integer>> returnInventory(); 
}
