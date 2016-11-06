package com.distributed.networksocial.handlers;

public class NewsFeedDAO {

	public static String[] newTextPost(String text) {
		String[] defaultTemplate = getFeedCardTemplate();
		
		return new String[] {
				defaultTemplate[0], 
				defaultTemplate[2], 
				defaultTemplate[4].replaceAll("SECONDARY-TEXT", text), 
				defaultTemplate[5]
		};
	}
	
	private static String[] getFeedCardTemplate() {
		return new String[] {
				"<div class=\"card\" style\"max-width:20%\"",
				"	<img src=\"resources/images/blank.png\" alt=\"\" style=\"width:100%\">",
				"	<div class=\"container\">",
				"		<h4>PRIMARY-TEXT</h4>",
				"		<p>SECONDARY-TEXT</p>",
				"	</div>"
		};
	}
}
