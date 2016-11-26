package com.distributed.socialnetwork.client.loginView;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;

public class LoginHeader {
	private FlowPanel hpanel = new FlowPanel();
	
	public LoginHeader() {
		Canvas background = Canvas.createIfSupported();
		
		if (background == null){
			hpanel.add(new HTML("<h1>Sorry, your browser doesn't support HTML5"));
			return;
		}
	
		background.setStyleName("headerCanvas");
		background.setWidth(Window.getClientWidth() + "px");
		background.setCoordinateSpaceWidth(Window.getClientWidth());
		
		background.setHeight("100px");
		background.setCoordinateSpaceHeight(100);
		
		// Fill a solid color with red
		background.getContext2d().setFillStyle(CssColor.make(255, 100, 100));
		background.getContext2d().fillRect(0, 0, Window.getClientWidth() - 25, 100);
		background.getContext2d().fill();
		
		hpanel.add(background);
	}
	
	public FlowPanel getHpanel() {
		return hpanel;
	}
}
