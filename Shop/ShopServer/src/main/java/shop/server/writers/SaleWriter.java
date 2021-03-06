package shop.server.writers;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.concurrent.TimeUnit;

import shop.core.domain.Pet;
import shop.core.domain.PetInventory;
import shop.server.core.ServiceManager;
import shop.server.core.Shop;
import shop.server.core.ShopServerProperties;

public class SaleWriter implements Runnable{
	
	private ServiceManager serviceManager;
	
	public SaleWriter(ServiceManager sm) {
		this.serviceManager = sm;
	}
	
	@Override
	public void run() {
		System.out.println("starting sale update thread..");
		writeInventoryData();
		
		try(OutputStreamWriter saleWriter = new OutputStreamWriter(new BufferedOutputStream(
				new FileOutputStream(ShopServerProperties.getProperties().getPetStorePath()+"/"+ShopServerProperties.getProperties().getPetSaleFileName())))){
		
			saleWriter.write(buildHeader());
			while (true){
				Pet pet = getServiceManager().getSaleService().returnSaleData().poll(1, TimeUnit.MINUTES);
				if(pet == null && !getServiceManager().getInventoryService().isAnyStockAvailable()){
					System.out.println("Exiting sale writer");
					saleWriter.flush();
					return;
				}
				
				if(pet != null){
					saleWriter.write(buildLineEntry(pet));
					getServiceManager().getDao().insertPet(pet);
				}
			}
			
		}catch(IOException | InterruptedException e){
			System.out.println("sale writer failed - " + e.getMessage());
		}
		
	}
	
	public ServiceManager getServiceManager() {
		return serviceManager;
	}
	
	private void writeInventoryData(){
		try(OutputStreamWriter inventoryWriter = new OutputStreamWriter(new BufferedOutputStream(
				new FileOutputStream(ShopServerProperties.getProperties().getPetStorePath()+"/"+ShopServerProperties.getProperties().getPetInventoryFileName())))){
			
			for(String petType : this.getServiceManager().getInventoryService().returnInventory().keySet()){
				inventoryWriter.write(petType + ":" +  this.getServiceManager().getInventoryService().returnInventory().get(petType).size() + "\n");
				PetInventory inventory = new PetInventory();
				inventory.setPetType(petType);
				inventory.setStockAvailable(this.getServiceManager().getInventoryService().returnInventory().get(petType).size());
				getServiceManager().getDao().insertInventory(inventory);
			}
		}catch(IOException e){
			System.out.println("failed to write inventory data - " + e.getMessage());
		}
	}
	
	private String buildHeader(){
		StringBuffer sb = new StringBuffer();
		sb.append("PetTag,PetType,SellPrice,CustomerNo,OrderNo");
		return sb.toString();
	}
	
	private String buildLineEntry(Pet pet){
		StringBuffer sb = new StringBuffer();
		sb.append(pet.getTag() + ",");
		sb.append(pet.getName() + ",");
		sb.append(pet.getPrice() + ",");
		sb.append(pet.getCustomerNumber() + ",");
		sb.append(pet.getOrderNumber() + "\n");
		
		return sb.toString();
		
	}
}
