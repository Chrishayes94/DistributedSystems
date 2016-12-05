package com.distributed.socialnetwork.client;

import com.distributed.socialnetwork.client.gallery.GalleryView;
import com.distributed.socialnetwork.client.gallery.UploadContent;
import com.distributed.socialnetwork.client.loginView.LoginView;
import com.distributed.socialnetwork.client.services.ConnectionService;
import com.distributed.socialnetwork.client.services.ConnectionServiceAsync;
import com.distributed.socialnetwork.client.services.UserContentService;
import com.distributed.socialnetwork.client.services.UserContentServiceAsync;
import com.distributed.socialnetwork.client.services.WebUIService;
import com.distributed.socialnetwork.client.services.WebUIServiceAsync;
import com.distributed.socialnetwork.server.database.DatabaseManager;
import com.distributed.socialnetwork.shared.ClientInfo;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

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
	private final UserContentServiceAsync userService = (UserContentServiceAsync) GWT.create(UserContentService.class);
	
	private VerticalPanel vPanel = new VerticalPanel();
	
	private FlowPanel header = new FlowPanel();
	private FlowPanel content = new FlowPanel();
	private FlowPanel footer = new FlowPanel();
	
	private LoginView loginView = new LoginView();
	private GalleryView galleryView = null;

	private UploadContent uploadWidget;
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		/** This code will be implemented to our login button.
		 connectionService.login(userBox.getValue(), passBox.getValue(), new AsyncCallback<ClientInfo>() {
			
		  		@Override
		  		public void onSuccess(ClientInfo result) {
		  			if (result.getLoggedIn()) {
		  				RootPanel.get().clear();
		  				// Load the next page
		  				
		  				// Set the session expiry date (default 1 day).
		  				String sessionID = result.getSessionID();
		  				final long duration = 1000 * 60 * 60 * 24 * 1;
		  				Date expires = new Date(Systeem.currentTimeMillis() + duration);
		  				Cookesi.setCookie("sid", sessionID, expires, null "/", false);
		  			} else {
		  				// Display error
		  			}
		  		}
		  		
		  		@Override
		  		public void onFailture(Throwable caught) {
		  			// Error message.
		  		}
		  )};
		 **/
		
		/*
		header.clear();
		header.add(loginView.getLoginHeader().getHpanel());
		content.clear();
		content.add(loginView.getMainPanel());
		footer.clear();
		footer.add(loginView.getLoginFooter().getHpanel());
		prepareLoginButton(loginView.getLoginButton(), loginView.getUsernameBox(), loginView.getPasswordBox());
		
		
		galleryView = new GalleryView(userService, this);
		galleryView.getGalleryTable().insertRow(0);
		galleryView.getGalleryTable().setWidget(0, 0, content);
		RootLayoutPanel.get().add(galleryView);**/
	}
}
