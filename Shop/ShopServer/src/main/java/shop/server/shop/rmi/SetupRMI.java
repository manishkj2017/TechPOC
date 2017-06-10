package shop.server.shop.rmi;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import shop.server.core.ShopServerProperties;
import shop.server.shop.OpenShop;

public class SetupRMI {
	public static void setupRMI() throws RemoteException{
		
		try {
			System.out.println("RMI Host - " + InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e) {
			System.out.println(e.getMessage());
		}
		LocateRegistry.createRegistry(ShopServerProperties.getProperties().getRMIPort());
		Registry registry = LocateRegistry.getRegistry(ShopServerProperties.getProperties().getRMIPort());
		registry.rebind(ShopServerProperties.getProperties().getShopRMIName(), OpenShop.shopInterface);
		
	}
}
