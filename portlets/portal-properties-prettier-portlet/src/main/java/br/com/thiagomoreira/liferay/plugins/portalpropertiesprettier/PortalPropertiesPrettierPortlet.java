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

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.Properties;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.commons.io.IOUtils;

import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

public class PortalPropertiesPrettierPortlet extends MVCPortlet {

	protected PortalPropertiesPrettier prettier = new PortalPropertiesPrettier();

	public void prettify(ActionRequest request, ActionResponse response)
			throws IOException, PortletException {

		String prettyProperties = prettify(request);

		request.setAttribute("portalPrettyProperties", prettyProperties);
	}

	public void serveResource(ResourceRequest request, ResourceResponse response)
			throws IOException, PortletException {

		String prettyProperties = prettify(request);

		OutputStream out = response.getPortletOutputStream();
		IOUtils.copy(new StringReader(prettyProperties), out);
		IOUtils.closeQuietly(out);
	}

	protected String prettify(PortletRequest request)
			throws IOException, PortletException {
		UploadPortletRequest uploadPortletRequest = PortalUtil
				.getUploadPortletRequest(request);

		String liferayVersion = ParamUtil.getString(uploadPortletRequest,
				"liferayVersion", "6.2.3-ga4");
		boolean printDefaultValue = ParamUtil.getBoolean(uploadPortletRequest,
				"printDefaultValue");

		Properties customProperties = PropertiesUtil.load(
				uploadPortletRequest.getFileAsStream("portalPropertiesFile"),
				"UTF-8");

		String prettyProperties = prettier.prettify(customProperties,
				liferayVersion, printDefaultValue);

		incrementCounter(request);

		return prettyProperties;
	}

	protected void incrementCounter(PortletRequest request) throws IOException,
			PortletException {
		PortletPreferences preferences = request.getPreferences();

		int count = GetterUtil.getInteger(preferences.getValue("counter", "0"));

		count++;

		preferences.setValue("counter", String.valueOf(count));
		preferences.store();
	}
}
