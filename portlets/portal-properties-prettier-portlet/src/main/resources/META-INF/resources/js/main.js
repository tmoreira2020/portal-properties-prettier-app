/*
 * Copyright © 2015 Thiago Moreira (tmoreira2020@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

AUI().use("node", "aui-tooltip", function(A) {
	var code = A.one("#portalPrettyProperties");

	if (code) {
		hljs.initHighlighting();

		var clipboard = new Clipboard("#copyButton");

		var tooltip =new A.Tooltip({
			trigger : "#copyButton",
			position : "top"
		});

		tooltip.toggle(false);


		clipboard.on("error", function(event) {
		});

		clipboard.on('success',function(e){
			e.clearSelection();
			tooltip.toggle(true);
			tooltip.render();
		});
	}
});