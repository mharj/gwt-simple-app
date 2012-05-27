package lan.sahara.simpleapp.client.apps;

import lan.sahara.simpleapp.client.SubSystems;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;

public class HandleCodeSplitExample extends AppAbstract {
	public static String act = "codesplit";
	private static final String SERVER_ERROR = "An error occurred while attempting to contact the server. Please check your network connection and try again.";
	private SimplePanel panel = new SimplePanel();
	public HandleCodeSplitExample(SubSystems sub) {
		super(sub);
		initWidget(panel);
	}
	@Override
	public void onAttachOrDetach(AttachEvent event) {
		if ( event.isAttached() ) {
			 GWT.runAsync(new RunAsyncCallback() { // Code Split
				@Override
				public void onFailure(Throwable reason) {
					Window.alert(SERVER_ERROR);
				}
				@Override
				public void onSuccess() {
					HandleCodeSplitExample.this.panel.add(new HTML("Hello world!"));
				} // Code Split
			 });
		}
	}

}
