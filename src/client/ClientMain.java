package client;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import sessionBeans.CreateTestDataInterface;
import sessionBeans.RetrieveInterface;

public class ClientMain {
	
	static RetrieveInterface retrieve;
	private static MainWindow mainWindow;
	private static Thread mainWindowThread;
	private static Thread autoSave;
	private static String serverURL = "localhost";

	public static void main(String[] args) throws Exception {
		
		//Get "Retrieve" object, which will be the port to all Entity Beans objects.
		Context ctx = getContext();
//		CreateTestDataInterface cd = (CreateTestDataInterface) ctx.lookup("CreateTestData/remote");
//		cd.doIt();
		System.out.println("Seems like it was done");
		
		retrieve =  (RetrieveInterface) ctx.lookup("Retrieve/remote");	
		
		ServerHandler.setServer(retrieve);
		ServerHandler.loadData();
		//Start main window and the autoSave thread.
		mainWindow = new MainWindow();
		mainWindowThread = new Thread(mainWindow);
		autoSave = new Thread(new AutoSave());
		mainWindowThread.start();
		autoSave.start();
		mainWindowThread.join();
		autoSave.interrupt();
		System.out.println("Closing Main Thread.");
	}
	
	
	// Implementation of the AutoSave Thread subclass.
	public static class AutoSave implements Runnable{
	
		
		public void run() {
			while(mainWindowThread.isAlive()) {
				try {
					ServerHandler.commit();
					Thread.sleep(1000);
				} catch (InterruptedException e) {

				} 
			}	
			System.out.println("Last Modifications Saved.");
		}
	}
		   
   private static Context getContext() throws Exception{
      //create a context
      Properties p = new Properties();
      p.put(Context.INITIAL_CONTEXT_FACTORY,   "org.jnp.interfaces.NamingContextFactory");
      p.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
      p.put(Context.PROVIDER_URL, serverURL);
      Context ctx = new InitialContext(p);
      return ctx;
   }

	

	
}
