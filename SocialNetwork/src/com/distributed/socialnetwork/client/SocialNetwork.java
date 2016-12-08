package com.distributed.socialnetwork.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.distributed.socialnetwork.client.gallery.GalleryView;
import com.distributed.socialnetwork.client.gallery.UploadContent;
import com.distributed.socialnetwork.client.services.ConnectionService;
import com.distributed.socialnetwork.client.services.ConnectionServiceAsync;
import com.distributed.socialnetwork.client.services.UserContentService;
import com.distributed.socialnetwork.client.services.UserContentServiceAsync;
import com.distributed.socialnetwork.client.services.WebUIService;
import com.distributed.socialnetwork.client.services.WebUIServiceAsync;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.HorizontalPanel;

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
	
	private GalleryView galleryView = new GalleryView(this);

	private UploadContent uploadWidget;
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		RootPanel.get().add(createPostForm());
		
		final FormPanel form2 = new FormPanel();
		HorizontalPanel panel2 = new HorizontalPanel();
		panel2.add(galleryView.getGalleryTable());
		
		form2.setWidget(panel2);
		
		RootPanel.get().add(form2);
		
	}
	
	private FormPanel createPostForm() {
		final FormPanel form1 = new FormPanel();
		final TextArea textPost = new TextArea();
		final FileUpload upload = new FileUpload();
		final HorizontalPanel panel1 = new HorizontalPanel();
		
		form1.setAction("/upload");
		
		textPost.getElement().setAttribute("maxlength", "250");
		form1.getElement().getStyle().setBackgroundColor("#EE5C42");
		upload.getElement().setAttribute("accept", "image/*");
		
		form1.setEncoding(FormPanel.ENCODING_MULTIPART);
		form1.setMethod(FormPanel.METHOD_POST);
		
		form1.setWidget(panel1);
		panel1.add(textPost);
		upload.setName("file");
		panel1.add(upload);
		
		panel1.add(new Button("Submit", new ClickHandler() {
			public void onClick(ClickEvent event)  {
				form1.submit();
			}
		}));
		
		form1.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
			
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
			}
		});
		return form1;
	}
}
