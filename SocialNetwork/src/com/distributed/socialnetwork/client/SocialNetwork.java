package com.distributed.socialnetwork.client;

import java.util.logging.Logger;

import com.distributed.socialnetwork.client.gallery.GalleryView;
import com.distributed.socialnetwork.client.gallery.UploadContent;
import com.distributed.socialnetwork.client.services.ConnectionService;
import com.distributed.socialnetwork.client.services.ConnectionServiceAsync;
import com.distributed.socialnetwork.client.services.UserContentService;
import com.distributed.socialnetwork.client.services.UserContentServiceAsync;
import com.distributed.socialnetwork.client.services.WebUIService;
import com.distributed.socialnetwork.client.services.WebUIServiceAsync;
import com.distributed.socialnetwork.server.database.DatabaseManager;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.PasswordTextBox;

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
	
	private GalleryView galleryView = new GalleryView(this);
	private UploadContent uploadWidget = new UploadContent(userService);
	
	final SplitLayoutPanel panel1 = new SplitLayoutPanel();
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		RootPanel.get().add(createLoginForm());
	}
	
	public void loadApplication() {
		
		RootPanel.get().add(createSearchForm());
		RootPanel.get().add(createGalleryForm());
		RootPanel.get().add(createPostForm());
	}
	
	private FormPanel createLoginForm() {
		
		final FormPanel login = new FormPanel();
		final HorizontalPanel loginPanel = new HorizontalPanel();
		final TextBox email = new TextBox();
		final PasswordTextBox pass = new PasswordTextBox();
		
		email.setName("email");
		pass.setName("pass");
		
		login.setAction("/login");
		login.getElement().getStyle().setBackgroundColor("#BCC6C6");
		
		login.setWidget(loginPanel);
		
		loginPanel.add(email);
		loginPanel.add(pass);
		loginPanel.add(new Button("Login", new ClickHandler() {
			public void onClick(ClickEvent event)  {
				login.submit();
			}
		}));
		
		return login;
		
	}
	
	private FormPanel createSearchForm() {
		
		final FormPanel form1 = new FormPanel();
		final HorizontalPanel panel1 = new HorizontalPanel();
		MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
		//line below causes error, basically is meant to populate suggestion
		//box with fullname values from db, so user can just type and select instead of doing a search
		//oracle.addAll(DatabaseManager.get());
		SuggestBox suggestBox = new SuggestBox(oracle);
		
		form1.setAction("/search");
		form1.getElement().getStyle().setBackgroundColor("#BCC6C6");
		
		suggestBox.addSelectionHandler(new SelectionHandler<Suggestion>() {
		    @Override public void onSelection(SelectionEvent<Suggestion> event) {
		    	String selectedUser =   ((SuggestBox)event.getSource()).getValue();
		    	//Load user posts based on fullname selected in suggestbox
		    	
		    	
		    }
		  });
		
		form1.setWidget(panel1);
		panel1.add(suggestBox);
		
		return form1;
	}
	
	private FormPanel createPostForm() {
		
		final FormPanel form2 = new FormPanel();
		final TextArea textPost = new TextArea();
		final FileUpload upload = new FileUpload();
		final HorizontalPanel panel2 = new HorizontalPanel();
				
		form2.setAction("/upload");
		form2.getElement().getStyle().setBackgroundColor("#FFFFFF");
		textPost.getElement().setAttribute("maxlength", "130");
		upload.getElement().setAttribute("accept", "image/*");
		
		form2.setEncoding(FormPanel.ENCODING_MULTIPART);
		form2.setMethod(FormPanel.METHOD_POST);
		
		form2.setWidget(panel2);
		panel2.add(textPost);
		panel2.add(new Button("Submit", new ClickHandler() {
			public void onClick(ClickEvent event)  {
				form2.submit();
			}
		}));
		upload.setName("file");
		panel2.add(upload);
		
		form2.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
			
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
			}
		});
		return form2;
	}
	
	private SplitLayoutPanel createGalleryForm() {
		final SplitLayoutPanel panel1 = new SplitLayoutPanel();
		panel1.addEast(galleryView.getGalleryTable(), 400);		
		panel1.addWest(galleryView.getGalleryTable(), 400);
		
		return panel1;
	}
}
