package com.distributed.socialnetwork.client.ui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;

public class PostView extends Composite {
	
	private static Image resize(final Image image) {
		int oHeight = image.getOffsetHeight();
		int oWidth = image.getOffsetWidth();
		
		if (oHeight > oWidth) {
			image.setHeight(64 + "px");
		} else {
			image.setWidth(64 + "px");
		}
		return image;
	}
	
	private PushButton button = new PushButton();
	private Image image = null;
	private Label area = new Label();
	
	private DockPanel root = new DockPanel();

	private boolean isImageBox = false;
	
	public PostView() {
		super();
		isImageBox = image == null ? false : true;
		
		area.getElement().setAttribute("border", "0px");
		
		area.setWidth("300px");
		area.setHeight("100px");
		
		root.setStylePrimaryName("postBox");
		root.add(area, DockPanel.NORTH);
		
		if (image != null) root.add(image); 
		
		button = new PushButton(resize(new Image("/images/like-button.png")));
		button.setWidth("64px");
		button.setHeight("32px");
		
		root.add(new HTML("</br>"), DockPanel.NORTH);
		root.add(button, DockPanel.NORTH);
	}
	
	public PostView(Image image) {
		this();
		this.image = image;
	}
	
	public PostView(String text) {
		this();
		this.area.setText(text);
	}
	
	public PostView(Image image, String text) {
		this();
		this.area.setText(text);
	}
	
	public DockPanel getPanel() {
		return root;
	}
}
