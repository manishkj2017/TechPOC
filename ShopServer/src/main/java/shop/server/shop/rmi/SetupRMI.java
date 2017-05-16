package shop.server.shop.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import shop.core.core.Shop;
import shop.server.shop.OpenShop;

public class SetupRMI {
	public static void setupRMI() throws RemoteException{
		
		LocateRegistry.createRegistry(Shop.getProperties().getRMIPort());
		Registry registry = LocateRegistry.getRegistry(Shop.getProperties().getRMIPort());
		registry.rebind(Shop.getProperties().getShopRMIName(), OpenShop.shopInterface);
		
	}
}
