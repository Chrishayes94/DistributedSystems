package com.distributed.socialnetwork.client.loginView;

import com.distributed.socialnetwork.client.services.ConnectionServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class LoginView extends Composite {

	private VerticalPanel mainpanel = new VerticalPanel();
	
	private LoginHeader loginheader = new LoginHeader();
	
	private LoginFooter loginFooter = new LoginFooter();
	
	private HTML secondHeadline = new HTML("<h1>Example GWT Main page before login</h1>");
	
	private DecoratorPanel decPanel = new DecoratorPanel();
	
	private FlexTable loginLayout = new FlexTable();
	
	// Elements in the login box
	private String headline = "Welcome to <INSERTNAME>";
	private String usernameLabel = "Username: ";
	private String passwordLabel = "Password: ";
	private TextBox username = new TextBox();
	private PasswordTextBox password = new PasswordTextBox();
	private Button loginButton = new Button("Login");
	
	public LoginView() {
		int windowHeight = Window.getClientHeight();
		int windowWidth = Window.getClientWidth();
		
		loginLayout.setCellSpacing(6);
		FlexCellFormatter cellFormatter = loginLayout.getFlexCellFormatter();
		
		loginLayout.setHTML(0, 0, this.headline);
		cellFormatter.setColSpan(0, 0, 2);
		cellFormatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
		
		username.setWidth("150px");
		password.setWidth("150px");
		loginLayout.setHTML(1, 0, this.usernameLabel);
		loginLayout.setWidget(1, 1, username);
		loginLayout.setHTML(2, 0, passwordLabel);
		loginLayout.setWidget(2, 1, password);
		
		loginLayout.setWidget(3, 0, loginButton);
		cellFormatter.setColSpan(3, 0, 2);
		cellFormatter.setHorizontalAlignment(3, 0, HasHorizontalAlignment.ALIGN_CENTER);
		
		decPanel.setWidget(loginLayout);
		
		mainpanel.setWidth(windowWidth / 2 + "px");
		mainpanel.setHeight(windowHeight * 0.6 + "px");
		mainpanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		mainpanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		mainpanel.add(secondHeadline);
		mainpanel.add(decPanel);
	}
	
	public LoginHeader getLoginHeader() {
		return this.loginheader;
	}
	
	public LoginFooter getLoginFooter() {
		return this.loginFooter;
	}
	
	public VerticalPanel getMainPanel() {
		return this.mainpanel;
	}
	
	public Button getLoginButton() {
		return loginButton;
	}
	
	public TextBox getUsernameBox() {
		return username;
	}
	
	public PasswordTextBox getPasswordBox() {
		return password;
	}
}
