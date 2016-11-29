package com.distributed.socialnetwork.client.gallery;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Creating dockpanel layout for main gallery view page after login
 * @author Alex
 */

public class GalleryView extends Composite {

	private VerticalPanel vPanel = new VerticalPanel();
	
	private ContentGallery galleryWidget;
	private UploadContent uploadWidget;
	
	public GalleryView() {
		int windowHeight = Window.getClientHeight();
		int windowWidth = Window.getClientWidth();
		
	    DockPanel dockPanel = new DockPanel();
	    dockPanel.setStyleName("dockpanel");
	    dockPanel.setSpacing(4);
	    dockPanel.setHorizontalAlignment(DockPanel.ALIGN_CENTER);
	
	    // Add text all around
	    dockPanel.add(new HTML("This is the first north component."), 
	    DockPanel.NORTH);
	    dockPanel.add(new HTML("This is the first south component."), 
	    DockPanel.SOUTH);
	    dockPanel.add(new HTML("This is the west component."), 
	    DockPanel.WEST);
	
	    uploadWidget.addGalleryUpdatedEventHandler(galleryWidget);
	    // Add scrollable text in the center
	    ScrollPanel scroller = new ScrollPanel(uploadWidget);
	    scroller.setPixelSize(windowHeight, windowWidth);
	    dockPanel.add(scroller, DockPanel.CENTER);
	
	    // Add the widgets to the root panel.
        vPanel.add(dockPanel);
	}

	public VerticalPanel getMainPanel() {
		return this.vPanel;
	}
}
