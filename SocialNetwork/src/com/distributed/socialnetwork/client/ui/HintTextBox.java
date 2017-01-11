package com.distributed.socialnetwork.client.ui;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.PasswordTextBox;

public class HintTextBox extends PasswordTextBox implements BlurHandler, FocusHandler {

	boolean isPasswordBox;
	
	String waterMark;
	HandlerRegistration blurHandler;
	HandlerRegistration focusHandler;
	
	public HintTextBox() {
		super();
		this.setStylePrimaryName("textInput");
		
		if (isPasswordBox) {
			getElement().setAttribute("type", "password");
		} else {
			getElement().setAttribute("type", "text");
		}
	}
	
	public HintTextBox(boolean password) {
		this();
		this.isPasswordBox = password;
	}
	
	public HintTextBox(String defaultValue) {
		this();
		setText(defaultValue);
	}
	
	public HintTextBox(String defaultValue, boolean password) {
		this(defaultValue);
		this.isPasswordBox = password;
	}
	
	public HintTextBox(String defaultValue, String watermark, boolean password) {
		this(defaultValue);
		setWatermark(watermark);
		this.isPasswordBox = password;
	}
	
	public void setWatermark(final String watermark) {
		this.waterMark = watermark;
		
		if ((watermark != null) && (watermark != "")) {
			blurHandler = addBlurHandler(this);
			focusHandler = addFocusHandler(this);
			enableWatermark();
		} else {
			blurHandler.removeHandler();
			focusHandler.removeHandler();
			getElement().setAttribute("type", isPasswordBox == true ? "password" : "text");
		}
	}
	
	
	private void enableWatermark() {
		String text = getText();
		if ((text.length() == 0) || (text.equalsIgnoreCase(waterMark))) {
			setText(waterMark);
			addStyleDependentName("watermark");
			getElement().setAttribute("type", "text");
		}
	}
	
	@Override
	public void onBlur(BlurEvent event) {
		enableWatermark();
	}
	
	@Override
	public void onFocus(FocusEvent event) {
		removeStyleDependentName("watermark");
		
		if (getText().equalsIgnoreCase(waterMark)) {
			setText("");
			getElement().setAttribute("type", isPasswordBox == true ? "password" : "text");
		}
	}
}
