package shop.server.shop.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import shop.server.core.ShopServerProperties;
import shop.server.shop.OpenShop;

public class SetupRMI {
	public static void setupRMI() throws RemoteException{
		
		LocateRegistry.createRegistry(ShopServerProperties.getProperties().getRMIPort());
		Registry registry = LocateRegistry.getRegistry(ShopServerProperties.getProperties().getRMIPort());
		registry.rebind(ShopServerProperties.getProperties().getShopRMIName(), OpenShop.shopInterface);
		
	}
}
