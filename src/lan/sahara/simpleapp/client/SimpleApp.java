package lan.sahara.simpleapp.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.RootPanel;

public class SimpleApp implements EntryPoint {
	public void onModuleLoad() {
		SubSystems sub = new SubSystems(); // build sub-system
		sub.eventBus = new HandlerManager(null);
		sub.greetService = GWT.create(GreetingService.class);
		
		AppController appViewer = new AppController(sub); // initialize controller
		appViewer.attach(RootPanel.get("container")); // attach to "root"
	}
}
