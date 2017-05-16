package shop.core.services;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.stereotype.Component;

import shop.core.domain.Pet;

@Component
public class SaleServiceImpl implements SaleService {

	private Integer maxTag = 0;
	private LinkedBlockingQueue<Pet> saleData = new LinkedBlockingQueue<Pet>();
	private Map<Integer, Pet> saleInMemoryStore = new TreeMap<Integer,Pet>();
	private Object lock = new Object();
	
	
	@Override
	public void updateSale(Pet pet) {
		updateTag(pet);
		saleData.offer(pet);

	}

	@Override
	public LinkedBlockingQueue<Pet> returnSaleData() {
		return saleData;
	}

	//TODO:move this to using Spark later
	@Override
	public void printSaleSummary() {
		System.out.println("total pets sold - " + saleInMemoryStore.values().size());
		Map<String, Integer> petCountPerType = new HashMap<>();
		for(Pet pet : saleInMemoryStore.values()){
			if(petCountPerType.get(pet.getName()) == null){
				petCountPerType.put(pet.getName(), 1);
			}else{
				int count = petCountPerType.get(pet.getName());
				petCountPerType.put(pet.getName(), ++count);
			}
		}
		petCountPerType.forEach((k,v) -> System.out.println(k + ":" + v));
		

	}

	@Override
	public Pet getPetByTag(int tag) {
		return saleInMemoryStore.get(tag);
	}
	
	private void updateTag(Pet pet){
		synchronized (lock) {
			pet.setTag(++maxTag);
			saleInMemoryStore.put(pet.getTag(), pet);
		}
	}

}
