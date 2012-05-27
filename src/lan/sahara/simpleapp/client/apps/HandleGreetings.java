package lan.sahara.simpleapp.client.apps;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import lan.sahara.simpleapp.client.SubSystems;
import lan.sahara.simpleapp.shared.FieldVerifier;

public class HandleGreetings extends AppAbstract {
	private static final String SERVER_ERROR = "An error occurred while attempting to contact the server. Please check your network connection and try again.";
	public static String act = "index";
	private static FlexTable table = new FlexTable();
	private final Button sendButton = new Button("Send");
	private final TextBox nameField = new TextBox();
	private final Label errorLabel = new Label();
	private final DialogBox dialogBox = new DialogBox();
	private final Button closeButton = new Button("Close");
	private final Label textToServerLabel = new Label();
	private final HTML serverResponseLabel = new HTML();	
	public HandleGreetings(SubSystems sub) {
		super(sub);
		initWidget(table);
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		nameField.setText("GWT User");
		// table
		table.setWidget(0, 0, new HTML("<b>Please enter your name:</b>"));
		table.getFlexCellFormatter().setColSpan(0, 0, 2);
		table.setWidget(1, 0, nameField);
		table.setWidget(1, 1, sendButton);
		table.setWidget(2, 0, errorLabel);
		table.getFlexCellFormatter().setColSpan(2, 0, 2);
		// dialog
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				sendButton.setEnabled(true);
				sendButton.setFocus(true);
			}
		});
		// handlers
		nameField.addKeyUpHandler(new KeyUpHandler(){
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER)
					HandleGreetings.this.sendNameToServer();
			}
		});
		sendButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				HandleGreetings.this.sendNameToServer();
			}
		});
	}

	@Override
	public void onAttachOrDetach(AttachEvent event) {
		if ( event.isAttached() ) {
			nameField.setFocus(true);
			nameField.selectAll();
		}
	}
	private void sendNameToServer() {
		// First, we validate the input.
		errorLabel.setText("");
		String textToServer = nameField.getText();
		if (!FieldVerifier.isValidName(textToServer)) {
			errorLabel.setText("Please enter at least four characters");
			return;
		}
		// Then, we send the input to the server.
		sendButton.setEnabled(false);
		textToServerLabel.setText(textToServer);
		serverResponseLabel.setText("");
		sub.greetService.greetServer(textToServer,new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				// Show the RPC error message to the user
				dialogBox.setText("Remote Procedure Call - Failure");
				serverResponseLabel.addStyleName("serverResponseLabelError");
				serverResponseLabel.setHTML(SERVER_ERROR);
				dialogBox.center();
				closeButton.setFocus(true);
			}
			public void onSuccess(String result) {
				dialogBox.setText("Remote Procedure Call");
				serverResponseLabel.removeStyleName("serverResponseLabelError");
				serverResponseLabel.setHTML(result);
				dialogBox.center();
				closeButton.setFocus(true);
			}
		});
	}
}
