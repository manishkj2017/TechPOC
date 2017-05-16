package shop.core.remote;

import java.math.BigDecimal;
import java.rmi.Remote;
import java.rmi.RemoteException;

import shop.core.domain.Pet;

public interface ShopInterface extends Remote{
	public Pet buyRequest(String petType, String customerName, BigDecimal bidPrice, int customerNumber, String orderSource, int orderNumber) throws RemoteException;
	public boolean isShopClosed() throws RemoteException;
	public Pet getPetByTag(int tag) throws RemoteException;
	public boolean isOrderDupe(int orderNumber) throws RemoteException; //web requests sometimes processed twice by CXF engine - not sure why	
}
