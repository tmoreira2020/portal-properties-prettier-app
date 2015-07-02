/**
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
package br.com.thiagomoreira.liferay.plugins.portalpropertiesprettier;

import java.util.Properties;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

public class PortalPropertiesPrettierPortlet extends MVCPortlet {

	protected PortalPropertiesPrettier prettier = new PortalPropertiesPrettier();

	public void prettify(ActionRequest request, ActionResponse response)
			throws Exception {
		UploadPortletRequest uploadPortletRequest = PortalUtil
				.getUploadPortletRequest(request);

		String liferayVersion = ParamUtil.getString(uploadPortletRequest,
				"liferayVersion");

		Properties customProperties = PropertiesUtil.load(
				uploadPortletRequest.getFileAsStream("portalPropertiesFile"),
				"UTF-8");

		String prettyProperties = prettier.prettify(customProperties,
				liferayVersion);

		request.setAttribute("portalPrettyProperties", prettyProperties);

		response.setRenderParameter("liferayVersion", liferayVersion);

		incrementCounter(request);
	}

	protected void incrementCounter(PortletRequest request) throws Exception {
		PortletPreferences preferences = request.getPreferences();

		int count = GetterUtil.getInteger(preferences.getValue("counter", "0"));

		count++;

		preferences.setValue("counter", String.valueOf(count));
		preferences.store();
	}
}
