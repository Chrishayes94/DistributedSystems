package com.distributed.socialnetwork.client.gallery;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class GalleryView extends Composite {

	VerticalPanel vPanel = new VerticalPanel();
	
	public GalleryView() {
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
	
	  // Add scrollable text in the center
	  HTML contents = new HTML("This is a ScrollPanel contained"
	  +" at the center of a DockPanel. "
	  +" By putting some fairly large contents in the middle"
	  +" and setting its size explicitly, it becomes a scrollable area"
	  +" within the page, but without requiring the use of an IFRAME."
	  +" Here's quite a bit more meaningless text that will serve primarily"
	  +" to make this thing scroll off the bottom of its visible area."
	  +" Otherwise, you might have to make it really, really"
	  +" small in order to see the nifty scroll bars!");
	  ScrollPanel scroller = new ScrollPanel(contents);
	  scroller.setSize("800px", "500px");
	  dockPanel.add(scroller, DockPanel.CENTER);
	
	  // Add the widgets to the root panel.
      vPanel.add(dockPanel);
	}

	public VerticalPanel getMainPanel() {
		return this.vPanel;
	}
}
