/*
 * Copyright (C) 2015 Thiago Moreira (tmoreira2020@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

ZeroClipboard.config({
	hoverClass : "btn-clipboard-hover",
	title: "Copy to clipboard!"
});


AUI().use("node", "aui-tooltip", function(A) {
	var code = A.one("#portalPrettyProperties");

	if (code) {
		hljs.initHighlighting();
	}

	var client = new ZeroClipboard(document.getElementById("copyButton"));

	var bridge = A.one("#global-zeroclipboard-html-bridge");

	var tooltip =new A.Tooltip({
		trigger : "#global-zeroclipboard-html-bridge",
		position : "top"
	});

	tooltip.toggle(false);
	tooltip.render();


	client.on("error", function(event) {
		ZeroClipboard.destroy();
	});

	client.on("ready", function( readyEvent ) {
		bridge.attr("title", "Copy to clipboard!");

		client.on("beforecopy", function( event ) {
			tooltip.toggle(false);
			bridge.attr("title", "Copied!");
		});

		client.on("aftercopy", function( event ) {
			tooltip.toggle(true);
		});
	});
});