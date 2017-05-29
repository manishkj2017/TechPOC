package shop.client.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import shop.client.core.ShopClientProperties;
import shop.core.remote.ShopInterface;

public class RMISetup {
	private static ShopInterface shopInterface;
	
	public static ShopInterface getRemoteShop(){
		try{
			if(shopInterface == null){
				Registry registry = LocateRegistry.getRegistry(ShopClientProperties.getProperties().getRMIPort());
				shopInterface = (ShopInterface)registry.lookup(ShopClientProperties.getProperties().getShopRMIName());
			}
			return shopInterface;
		}catch(RemoteException | NotBoundException e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void removeRMIShopConnection(){
		System.out.println("unbinding the remote shop reference");
		Registry registry;
		try {
			registry = LocateRegistry.getRegistry(ShopClientProperties.getProperties().getRMIPort());
			registry.unbind(ShopClientProperties.getProperties().getShopRMIName());
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
