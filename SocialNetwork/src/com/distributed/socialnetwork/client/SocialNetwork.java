package com.distributed.socialnetwork.client;

import com.distributed.socialnetwork.client.loginView.LoginView;
import com.distributed.socialnetwork.client.services.ConnectionService;
import com.distributed.socialnetwork.client.services.ConnectionServiceAsync;
import com.distributed.socialnetwork.client.services.WebUIService;
import com.distributed.socialnetwork.client.services.WebUIServiceAsync;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SocialNetwork implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network " + "connection and try again.";

	/**
	 * Create all the Services for async calls to the server.
	 */
	private final ConnectionServiceAsync connectionService = GWT.create(ConnectionService.class);
	private final WebUIServiceAsync webUIService = (WebUIServiceAsync) GWT.create(WebUIService.class);
	
	// Define variables used for layout
	private final ScrollPanel panelMain = new ScrollPanel();
	private final  HorizontalPanel panelTop = new HorizontalPanel();
	
	private FlowPanel header = new FlowPanel();
	private FlowPanel content = new FlowPanel();
	private FlowPanel footer = new FlowPanel();
	
	private LoginView loginView = new LoginView();
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
	      /** Create a Image widget 
	      Image image = new Image();

	      //set image source
	      //image.setUrl("http://www.tutorialspoint.com/images/gwt-mini.png");

	      // Add image to the root panel.
	      panelMain.add(image);

	      RootPanel.get("gwtContainer").add(panelMain);
	      RootPanel.get("gwtContainer").add(panelTop);**/
		header.clear();
		header.add(loginView.getLoginHeader().getHpanel());
		
		content.clear();
		content.add(loginView.getMainPanel());
		
		footer.clear();
		footer.add(loginView.getLoginFooter().getHpanel());
		
		RootPanel.get("content").add(content);
		RootPanel.get("header").add(header);
		RootPanel.get("footer").add(footer);
	}
	
	public void attemptToCreateUI() {
		webUIService.prepareHomeScreen(new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("RPC to prepareHomeScreen() failed");
			}

			@Override
			public void onSuccess(Boolean result) {
				if (result) {
					RootPanel root = RootPanel.get("postbox");
					Button submit = new Button("Post");
					
					root.add(new HTML("<form action=\"submit\" method=\"post\">"));
					root.add(new HTML("		<div><textarea name=\"content\" rows=\"3\" cols=\"60\"></textarea></div>"));
					root.add(submit);
					root.add(new HTML("</form>"));
				}
			}
			
		});
	}
	
	public void attemptUserConnection() {
		connectionService.login(GWT.getHostPageBaseURL(), new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				// Was not able to login or create account with the provided information.
			}

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
