package shop.core.services;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Component;

import shop.core.core.Shop;
import shop.core.enums.PetTypes;

@Component
public class InventoryServiceImpl implements InventoryService {

	private boolean isInventoryBuilt = false;
	private Map<String, ArrayDeque<Integer>> inventory = new HashMap();
	private Random inventorySize = new Random();
	private Object inventoryLock = new Object();
	
	
	@Override
	public boolean isStockAvailable(String stockType) {
		synchronized (inventoryLock) {
			ArrayDeque<Integer> totalStock = inventory.get(stockType);
			if(totalStock.isEmpty())
				return false;
			else{
				totalStock.pop();
				return true;
			}
					
		}
	}

	@Override
	public boolean isAnyStockAvailable() {
		for(PetTypes type : PetTypes.values()){
			if(!inventory.get(type.name()).isEmpty())
				return true;
		}
		return false;
	}

	@Override
	public void buildInventory() {
		if(!isInventoryBuilt)
			buildFromLocal();

	}

	

	@Override
	public void printInventory() {
		int totalStock = 0;
		for(String petType : inventory.keySet()){
			totalStock += inventory.get(petType).size();
			System.out.println("Available " + petType + " : " + inventory.get(petType).size());
		}
		System.out.println("total stock of all pets - " + totalStock);

	}

	@Override
	public Map<String, ArrayDeque<Integer>> returnInventory() {
		return inventory;
	}
	
	private void buildFromLocal() {
		for(PetTypes type : PetTypes.values()){
			int maxStock = inventorySize.nextInt(Shop.getProperties().getPetMaxInventorySize());
			ArrayDeque<Integer> petStock = new ArrayDeque<Integer> ();
			for(int i=1; i <=maxStock; i++){
				petStock.offer(i);
			}
			inventory.put(type.name(), petStock);
		}
		
		isInventoryBuilt = true;
		
	}
	
	

}
