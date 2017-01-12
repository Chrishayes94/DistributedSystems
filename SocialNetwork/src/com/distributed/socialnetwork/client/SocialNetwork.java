package com.distributed.socialnetwork.client;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import com.distributed.socialnetwork.client.gallery.GalleryView;
import com.distributed.socialnetwork.client.services.ConnectionService;
import com.distributed.socialnetwork.client.services.ConnectionServiceAsync;
import com.distributed.socialnetwork.client.services.UserContentService;
import com.distributed.socialnetwork.client.services.UserContentServiceAsync;
import com.distributed.socialnetwork.client.services.WebUIService;
import com.distributed.socialnetwork.client.services.WebUIServiceAsync;
import com.distributed.socialnetwork.client.ui.HintTextBox;
import com.distributed.socialnetwork.client.ui.MultipleTextBox;
import com.distributed.socialnetwork.shared.ClientInfo;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SocialNetwork extends Composite implements EntryPoint {
	
	/**
	 * This variables stores the max amount of posts to retrieve at each time, this can be 
	 * increased or decreased via an admin depending on server loads.
	 */
	private static int POSTS_INTERVAL = 25;
	
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
	private final WebUIServiceAsync webUIService = GWT.create(WebUIService.class);
	private final UserContentServiceAsync userService = GWT.create(UserContentService.class);
	
	private GalleryView galleryView = new GalleryView(this);

	final SplitLayoutPanel panel1 = new SplitLayoutPanel();
	
	private class LoginView implements HasWidgets {
		
		HorizontalPanel container;
		Label loginLabel;
		Label passwordLabel;
		HintTextBox passwordField;
		HintTextBox usernameField;
		Button loginButton;
		
		public LoginView() {
			container = new HorizontalPanel();
			usernameField = new HintTextBox("", "Username", false);
			passwordField = new HintTextBox("", "Password", true);
			loginLabel = new Label("Username");
			passwordLabel = new Label("Password");
			loginButton = new Button("Login");
			
			// Multiple columns for more organised layouts.
			final VerticalPanel col1 = new VerticalPanel();
			final VerticalPanel col2 = new VerticalPanel();
			final VerticalPanel col3 = new VerticalPanel();
			
			col1.add(loginLabel);
			col1.add(new HTML("<br />"));
			col1.add(usernameField);
			
			col2.add(passwordLabel);
			col2.add(new HTML("<br />"));
			col2.add(passwordField);
			
			col3.add(new HTML("<br />"));
			col3.add(new HTML("<br />"));
			col3.add(loginButton);
			
			container.add(col1);
			container.add(col2);
			container.add(col3);
		}

		public Widget asWidget() {
			return container;
		}
		
		@Override
		public void add(Widget w) {
			container.add(w);
		}

		@Override
		public void clear() {
			container.clear();
		}

		@Override
		public Iterator<Widget> iterator() {
			return container.iterator();
		}

		@Override
		public boolean remove(Widget w) {
			return container.remove(w);
		}
		
		public HintTextBox getEmail() {
			return usernameField;
		}
		
		public HintTextBox getPassword() {
			return passwordField;
		}
		
		public Button getButton() {
			return loginButton;
		}
	}

	private class RegistrationView implements HasWidgets {
		
		HorizontalPanel container;
		Label emailLabel;
		Label passwordLabel;
		Label passwordAgainLabel;
		Label fullnameLabel;
		HintTextBox emailField;
		HintTextBox passwordField;
		HintTextBox passwordAgainField;
		HintTextBox fullnameField;
		Button submit;
		
		public RegistrationView() {
			container = new HorizontalPanel();
			
			emailLabel = new Label("Email");
			passwordLabel = new Label("Password");
			passwordAgainLabel = new Label("Re-enter Password");
			fullnameLabel = new Label("Fullname");
			
			emailField = new HintTextBox("", "Email", false);
			passwordField = new HintTextBox("", "Password", true);
			passwordAgainField = new HintTextBox("", "Re-enter Password", true);
			fullnameField = new HintTextBox("", "Fullname", false);
			submit = new Button("Signup");
			
			// Two vertical panels to keep everything seperated
			VerticalPanel col1 = new VerticalPanel();
			VerticalPanel col2 = new VerticalPanel();
			
			col1.add(emailLabel);
			col1.add(passwordLabel);
			col1.add(passwordAgainLabel);
			col1.add(fullnameLabel);
			
			col2.add(emailField);
			col2.add(passwordField);
			col2.add(passwordAgainField);
			col2.add(fullnameField);
			
			container.add(col1);
			container.add(col2);
			container.add(submit);
		}
		
		public Widget asWidget() {
			return container;
		}
		
		@Override
		public void add(Widget w) {
			container.add(w);
		}

		@Override
		public void clear() {
			container.clear();
		}

		@Override
		public Iterator<Widget> iterator() {
			return container.iterator();
		}

		@Override
		public boolean remove(Widget w) {
			return container.remove(w);
		}
		
		public HintTextBox getEmail() {
			return emailField;
		}
		
		public HintTextBox getPassword() {
			return passwordField;
		}
		
		public HintTextBox getPasswordAgain() {
			return passwordAgainField;
		}
		
		public HintTextBox getFullname() {
			return fullnameField;
		}
		
		public Button getButton() {
			return submit;
		}
	}
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		RootPanel.get().getElement().getStyle().setBackgroundColor("#D6DCDC");
		connectionService.login("", new AsyncCallback<ClientInfo>() {

			@Override
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(ClientInfo result) {
				if (result == null) loadLogin();
				else loadApplication();
			}
			
		});
	}
	
	private void loadLogin() {
		RootPanel.get().add(createLoginForm());
		RootPanel.get().add(createRegistrationForm());
		
	}
	
	public void loadApplication() {
		
		RootPanel.get().clear();
		
		RootPanel.get().add(createSearchForm());
		RootPanel.get().add(createPostForm());
		RootPanel.get().add(createGalleryForm());
		
		galleryView.refreshGallery();
	}
	
	private FormPanel createLoginForm() {
		
		final FormPanel login = new FormPanel();
		final LoginView view = new LoginView();
		
		login.setWidget(view.asWidget());
		
		login.getElement().getStyle().setBackgroundColor("#BCC6C6");
		
		view.getButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event)  {
				connectionService.login(view.getEmail().getText() + ":" + view.getPassword().getText(), new AsyncCallback<ClientInfo>() {

					@Override
					public void onFailure(Throwable caught) {
						// Unable to login
					}

					@Override
					public void onSuccess(ClientInfo result) {
						// successful, load application.
						if (result != null)  loadApplication();
					}
					
				});
			}
		});
		return login;
		
	}
	
	private FormPanel createRegistrationForm() {
		final FormPanel register = new FormPanel();
		final RegistrationView view = new RegistrationView();
		
		register.setWidget(view.asWidget());
		
		register.getElement().getStyle().setBackgroundColor("#BCC6C6");
		
		view.getButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String email = view.getEmail().getText();
				String[] password = { view.getPassword().getText(), view.getPasswordAgain().getText() };
				String fullname = view.getFullname().getText();
				
				if (password[0].equals(password[1])) {
					connectionService.register(fullname + ":" + email + ":" + password[0], new AsyncCallback<ClientInfo>() {

						@Override
						public void onFailure(Throwable caught) {
							// Show an error again.
						}

						@Override
						public void onSuccess(ClientInfo result) {
							if (result == null) return ;// Show errror
							
							loadApplication();
						}
						
					});
				} else {
					// Display that the passwords do not match
				}
			}
			
		});
		
		return register;
	}
	
	private FormPanel createSearchForm() {
		
		final FormPanel form1 = new FormPanel();
		final HorizontalPanel panel1 = new HorizontalPanel();
		final MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
		
		
		//line below causes error, basically is meant to populate suggestion
		//box with fullname values from db, so user can just type and select instead of doing a search
		userService.get(new AsyncCallback<List<String>>() {

			@Override
			public void onFailure(Throwable caught) {
				return;
			}

			@Override
			public void onSuccess(List<String> result) {
				for (String s : result) {
					oracle.add(s);
				}
			}
			
		});
		
		final SuggestBox suggestBox = new SuggestBox(oracle, new MultipleTextBox());
		suggestBox.setWidth("300px");
		form1.getElement().getStyle().setBackgroundColor("#BCC6C6");
		
		suggestBox.addSelectionHandler(new SelectionHandler<Suggestion>() {
		    @Override public void onSelection(SelectionEvent<Suggestion> event) {
		    	String selectedUser =   ((SuggestBox)event.getSource()).getValue();
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
		textPost.getElement().setAttribute("maxlength", "80");
		textPost.getElement().setAttribute("name", "message");
		
		upload.getElement().setAttribute("accept", "image/*");
	
		form2.setEncoding(FormPanel.ENCODING_MULTIPART);
		form2.setMethod(FormPanel.METHOD_POST);
		
		form2.setWidget(panel2);
		panel2.add(textPost);
		panel2.add(new Button("Submit", new ClickHandler() {
			public void onClick(ClickEvent event)  {
				form2.submit();
				galleryView.refreshGallery();
			}

		}));
		upload.setName("file");
		panel2.add(upload);
		return form2;
	}
	
	private FlexTable createGalleryForm() {
		final SplitLayoutPanel panel1 = new SplitLayoutPanel();
		panel1.addEast(galleryView.getGalleryTable(), 400);		
		panel1.addWest(galleryView.getGalleryTable(), 400);
		
		return galleryView.getGalleryTable();
	}
	
	public UserContentServiceAsync getUserService() {
		return userService;
	}
}
