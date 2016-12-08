package com.distributed.socialnetwork.client;

import java.util.logging.Level;
import java.util.logging.Logger;

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
import com.google.gwt.logging.client.HasWidgetsLogHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SocialNetwork implements EntryPoint {
	
	/**
	 * Lets create a global logger, used to keep track of all the users posting new information.
	 * (USED FOR ADMIN PURPOSES)
	 */
	public static Logger rootLogger = Logger.getLogger("");
	
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
		final FormPanel form = new FormPanel();
		form.setAction("/upload");
		
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		form.setMethod(FormPanel.METHOD_POST);
		
		VerticalPanel panel = new VerticalPanel();
		form.setWidget(panel);
		FileUpload upload = new FileUpload();
		upload.setName("file");
		panel.add(upload);
		
		panel.add(new Button("Submit", new ClickHandler() {
			public void onClick(ClickEvent event)  {
				form.submit();
			}
		}));
		
		form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
			
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
			}
		});
		RootPanel.get().add(form);
		
	}
}
