/*
 * Copyright 2005-2015 tuling123.com. All rights reserved.
 * Support: http://www.tuling123.com
 * License: http://www.tuling123.com
 * 
 * JavaScript - Input
 * Version: 4.0
 */

$().ready( function() {

	if ($.tools != null) {
		var $tab = $("#tab");
		var $title = $("#inputForm :input[title], #inputForm label[title]");
		
		// Tab效果
		$tab.tabs("table.tabContent, div.tabContent", {
			tabs: "input"
		});
		
		// 表单提示
		$title.tooltip({
			position: "bottom right",
			effect: "fade"
		});
	}

});