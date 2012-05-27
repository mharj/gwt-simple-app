package lan.sahara.simpleapp.client.apps;

import java.util.HashMap;

import lan.sahara.simpleapp.client.SubSystems;

import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.user.client.ui.Composite;

public abstract class AppAbstract extends Composite implements Handler {
	protected final SubSystems sub;
	protected HashMap<String, String[]> params;
	public AppAbstract(SubSystems sub) {
		super();
	    this.sub = sub;
	    this.addAttachHandler(this);
	}
	public void setParams(HashMap<String, String[]> params) {
		this.params = params;
	}
}

