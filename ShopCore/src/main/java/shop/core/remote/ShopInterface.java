package shop.core.remote;

import java.math.BigDecimal;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import shop.core.domain.Pet;
import shop.core.domain.PetInventory;
import shop.core.domain.PetOrderSummaryData;
import shop.core.domain.PetSaleSummaryData;

public interface ShopInterface extends Remote{
	public Pet buyRequest(String petType, String customerName, BigDecimal bidPrice, int customerNumber, String orderSource, int orderNumber) throws RemoteException;
	public boolean isShopClosed() throws RemoteException;
	public Pet getPetByTag(int tag) throws RemoteException;
	public boolean isOrderDupe(int orderNumber) throws RemoteException; //web requests sometimes processed twice by CXF engine - not sure why
	public List<PetSaleSummaryData> getPetSaleSummary() throws RemoteException; 
	public List<PetOrderSummaryData> getPetOrderSummary() throws RemoteException;
	public List<PetInventory> getPetsInventorySummary() throws RemoteException;
	
}
