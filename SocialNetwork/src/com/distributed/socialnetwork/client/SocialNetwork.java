package com.distributed.socialnetwork.client;

import com.distributed.socialnetwork.client.gallery.ContentGallery;
import com.distributed.socialnetwork.client.gallery.UploadContent;
import com.distributed.socialnetwork.client.loginView.LoginView;
import com.distributed.socialnetwork.client.services.ConnectionService;
import com.distributed.socialnetwork.client.services.ConnectionServiceAsync;
import com.distributed.socialnetwork.client.services.WebUIService;
import com.distributed.socialnetwork.client.services.WebUIServiceAsync;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

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
	
	private FlowPanel header = new FlowPanel();
	private FlowPanel content = new FlowPanel();
	private FlowPanel footer = new FlowPanel();
	
	private LoginView loginView = new LoginView();
	
	private ContentGallery galleryWidget;
	private UploadContent uploadWidget;
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
	      
		header.clear();
		header.add(loginView.getLoginHeader().getHpanel());
		
		content.clear();
		content.add(loginView.getMainPanel());
		
		footer.clear();
		footer.add(loginView.getLoginFooter().getHpanel());
		
		RootPanel.get("content").add(content);
		RootPanel.get("header").add(header);
		RootPanel.get("footer").add(footer);
		
		prepareLoginButton(loginView.getLoginButton(), loginView.getUsernameBox(), loginView.getPasswordBox());
		
		galleryWidget = new ContentGallery(this);
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
	
	private void prepareLoginButton(final Button button, final TextBox box, final PasswordTextBox password) {
		button.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				connectionService.login(box.getText() + ":" + password.getText(), new AsyncCallback<Boolean>() {

					@Override
					public void onFailure(Throwable caught) {
						// Error occurred while logging in
					}

					@Override
					public void onSuccess(Boolean result) {
						if (result) {

							RootPanel.get("gallery").add(galleryWidget);
							
						} else if (!result) {
							// Flag an error message detailing there is a problem with either username or password.
						}
						else {
							
						}
					}
					
				});
			}
		});
	}
}
