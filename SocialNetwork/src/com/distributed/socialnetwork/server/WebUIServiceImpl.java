package com.distributed.socialnetwork.server;

import com.distributed.socialnetwork.client.services.WebUIService;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class WebUIServiceImpl extends RemoteServiceServlet implements WebUIService {

	@Override
	public boolean prepareHomeScreen() {
		return false;
	}

}
