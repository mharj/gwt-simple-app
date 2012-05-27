package lan.sahara.simpleapp.client;

import java.util.HashMap;

import lan.sahara.simpleapp.client.apps.*;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;

@SuppressWarnings("unused")
public class AppController implements ValueChangeHandler<String> {
	private final HashMap<String,AppAbstract> apps = new HashMap<String,AppAbstract>();
	private final SubSystems sub;
	private HasWidgets container;
	private HashMap<String, String[]> params;
	/* cons */
	public AppController(SubSystems sub) {
		this.sub = sub;
		History.addValueChangeHandler(this);
		// register act name and construct applications here
		apps.put(HandleGreetings.act, new HandleGreetings(sub));		
		apps.put(HandleCodeSplitExample.act, new HandleCodeSplitExample(sub));
	}
	/* controller attach */
	public void attach(final HasWidgets container) {
		this.container = container;
		if (History.getToken().length() == 0) 
			History.newItem("act="+HandleGreetings.act); // default action
		History.fireCurrentHistoryState();
	}
	/* History change handler */
	public void onValueChange(ValueChangeEvent<String> event) {
		params = parseHistory(event.getValue());
		if (params.containsKey("act") ) {
			String act = params.get("act")[0];
			if ( apps.containsKey(act)) {
				AppAbstract app = apps.get(act);
				container.clear();		// clear old content
				app.setParams(params);	// give history HashMap parameters to application
				container.add(app);		// add application to screen
			} else 
				Window.alert("Missing application");
		}
	}
	/* Build history HashMap */
	private HashMap<String,String[]> parseHistory(String qstr) {
		qstr = URL.decode(qstr);
		HashMap<String,String[]> ret = new HashMap<String,String[]>();
		for ( String attr : qstr.split("&") ) {
			String e[] = attr.split("=",2);
			if ( ! ret.containsKey(e[0]) ) {
				ret.put(e[0], new String[1]);
				ret.get(e[0])[0]=e[1];
			} else {
				String[] c = ret.get(e[0]);
				String[] extended = new String[c.length+1];
				extended[c.length] = e[1];
				System.arraycopy(c, 0, extended, 0, c.length);
				ret.put(e[0], new String[1]);
				ret.put(e[0], extended);
			}
		}
		return ret;		
	}
}
