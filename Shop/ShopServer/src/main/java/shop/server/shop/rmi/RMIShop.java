package shop.server.shop.rmi;

import java.rmi.RemoteException;

public class RMIShop extends Thread{
	
	
	@Override
	public void run(){
		try {
			SetupRMI.setupRMI();
			System.out.println("RMI Shop open - ready to accept buy requests for pets");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
