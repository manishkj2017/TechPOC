package shop.server.services;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import shop.core.domain.Pet;
import shop.core.domain.PetInventory;
import shop.core.domain.PetOrder;
import shop.core.domain.PetOrderSummaryData;
import shop.core.domain.PetSaleSummaryData;
import shop.server.shop.OpenShop;

@Component
public class ShopDataServiceImpl implements ShopDataService {

	@Override
	public List<PetSaleSummaryData> getPetSaleSummary() {

		return preparePetSaleSummary();

	}
	
	@Override
	public List<PetOrderSummaryData> getPetOrderSummary() {
		
		List<PetOrderSummaryData> orderSummaries = new  ArrayList<PetOrderSummaryData>();
		Collection<PetOrder> orderInmemoryData = OpenShop.shop.getServiceManager().getOrderService().getInMemoryStoreOrdersData();
		
		//work with local list
		List <PetOrder> orders = new ArrayList <PetOrder> ();
		orderInmemoryData.forEach(orders::add);
		
		Function<PetOrder, String> groupByClause = order -> {
			return order.getPetType()+ "-" + order.getOrderSource() + "-" + order.getStatus() + "-" + order.getStatusReason();
		};
		
		Map<String, List<PetOrder>> groupedOrders = orders.stream().collect(Collectors.groupingBy(groupByClause));
		
		groupedOrders.forEach( (k,v) -> {
			
			PetOrder order  = v.get(0);
			PetOrderSummaryData orderSummary = new PetOrderSummaryData(order.getPetType(), 
					order.getOrderSource(), order.getStatus(), order.getStatusReason(), v.size());
			
			orderSummaries.add(orderSummary);
		});
		
		
		return orderSummaries;
	}

	@Override
	public List<PetInventory> getPetInventory() {
		Map<String, ArrayDeque<Integer>> inventoryInmemoryData = OpenShop.shop.getServiceManager().getInventoryService().returnInventory();
		List<PetInventory> inventory = new ArrayList <PetInventory> ();
		
		inventoryInmemoryData.forEach((k,v) -> {
			PetInventory i = new PetInventory();
			i.setPetType(k);
			i.setStockAvailable(v.size());
			inventory.add(i);
		});
		
		return inventory;
		
	}
	
	private List<PetSaleSummaryData> preparePetSaleSummary(){
		
		
		Collection<Pet> saleInmemoryData = OpenShop.shop.getServiceManager().getSaleService().getInMemoryStorePetSaleData();
		Collection<PetOrder> orderInmemoryData = OpenShop.shop.getServiceManager().getOrderService().getInMemoryStoreOrdersData();
		
		//work with local lists
		List <Pet> pets = new ArrayList<Pet> ();
		List <PetOrder> orders = new ArrayList <PetOrder> ();
		List<PetWrapper> petWrappers = new ArrayList<PetWrapper>();
		
		saleInmemoryData.forEach(pets::add);
		orderInmemoryData.forEach(orders::add);
		
		Map <Integer, String> tagToOrderSource = new HashMap <Integer, String> (); 
		orders.forEach(order -> tagToOrderSource.put(order.getPetTag(), order.getOrderSource()));

		//Function 1 : set order source on Pets
		Function<Pet, PetWrapper> setOrderSource = pet -> {
			PetWrapper petWrapper = new PetWrapper();
			petWrapper.setPet(pet);
			petWrappers.add(petWrapper);
			petWrapper.setOrderSource(tagToOrderSource.get(pet.getTag())); return petWrapper;};
		
		//Function 2 : create sale summary data objects for Pets
		List<PetSaleSummaryData> petSummaries = new ArrayList <PetSaleSummaryData> ();
		Function<PetWrapper, PetSaleSummaryData> assignSaleSummary = (petw) -> {
			PetSaleSummaryData summ = new PetSaleSummaryData(petw.getPet().getName(), petw.getOrderSource(), 
					new Long(1), 0, petw.getPet().getPrice(), null, 0);
			petSummaries.add(summ);
			return summ;
		};
		
		//Function 3 : group pets sale by pet type and order source 
		Function<PetSaleSummaryData, String> groupByClause = summ -> { return summ.getPetType() + "-" + summ.getOrderSource(); };
		
		//final computation using stream
		Map<String, PetSaleSummaryData> groupByPetTypeAndOrderSource = pets.parallelStream().map(setOrderSource).map(assignSaleSummary)
				.collect(Collectors.groupingBy(groupByClause, new PetSaleSummaryCollector<PetSaleSummaryData>()));
		
		groupByPetTypeAndOrderSource.forEach((k,v) -> System.out.println(k + ":" + v));
		
		List<PetSaleSummaryData> grpSummaries = new ArrayList <PetSaleSummaryData> ();
		grpSummaries.addAll(groupByPetTypeAndOrderSource.values());
		System.out.println(grpSummaries.size());
		
		return grpSummaries;
	}
	
	//Interface Collector<T,A,R>
	class PetSaleSummaryCollector<T> implements Collector<PetSaleSummaryData, PetSaleSummaryData, PetSaleSummaryData> {

			@Override
			//[supplier:Description]
			//supplier tells you what is your final container where you want to accumulate your results
			//in this case I am summing up multiple PetSaleSummaryData objects into a single PetSaleSummaryData object
			//How I am accumulating is coded in accumulator below
			//Supplier needs to return "A" from in terms of collector interface arguments.
			
			//public Supplier<A> supplier() {returns property of A or collection of A}
			public Supplier<PetSaleSummaryData> supplier() {
				return PetSaleSummaryData::new;
			}

			@Override
			//[accumulator:Description]
			//accumulator is the place you implement core logic of collection
			//I am adding total sold and revenue to container returned by my supplier above
			//Accumulator take "T" and "A" as arguments and add values from T to A in terms of collector interface arguments.
			//In my case both summ2 is A and summ1 is T
			
			//public BiConsumer<A, T> accumulator()
			public BiConsumer<PetSaleSummaryData, PetSaleSummaryData> accumulator() {
				return (summ2, summ1) -> {
					summ2.setPetType(summ1.getPetType());
					summ2.setOrderSource(summ1.getOrderSource());
					summ2.setTotalSold(summ2.getTotalSold() + summ1.getTotalSold());
					summ2.setRevenue(summ2.getRevenue().add(summ1.getRevenue()));
				};
			}

			@Override
			//[combiner:Description]
			//combiner comes into play for parallel streams
			//you define here how to perform final combine in case accumulation happens parallely
			//for me it is just similar to accumulator.. basically accumulate final results in the same way
			//combiner argument is "A" in terms of collector interface arguments. summ2 is A and summ1 is T
			
			//public BinaryOperator<A> combiner() {}
			public BinaryOperator<PetSaleSummaryData> combiner() {
				return (summ2, summ1) -> {
					System.out.println("running combiner");
					summ2.setPetType(summ1.getPetType());
					summ2.setOrderSource(summ1.getOrderSource());
					summ2.setTotalSold(summ2.getTotalSold() + summ1.getTotalSold());
					summ2.setRevenue(summ2.getRevenue().add(summ1.getRevenue()));
					return summ2;
				};
			}

			@Override
			//[finisher:Description]
			//finisher arguments are "A" and "R" in terms of collector interface arguments.
			//You can transform A further into final result R. I am not doing that and I am just returning the "A" as "R"

			//public Function<A, R> finisher(){return R}
			public Function<PetSaleSummaryData, PetSaleSummaryData> finisher() {
				return t -> t;
			}

			@Override
			public Set<Characteristics> characteristics() {
				Set <Characteristics> set = new HashSet <Characteristics> ();
				set.add(Characteristics.IDENTITY_FINISH);
				return set;
			}
			
		}
		
		private class PetWrapper {
			private Pet pet;
			private String orderSource;
			
			public Pet getPet() {
				return pet;
			}
			public void setPet(Pet pet) {
				this.pet = pet;
			}
			public String getOrderSource() {
				return orderSource;
			}
			public void setOrderSource(String orderSource) {
				this.orderSource = orderSource;
			}
		}

}
