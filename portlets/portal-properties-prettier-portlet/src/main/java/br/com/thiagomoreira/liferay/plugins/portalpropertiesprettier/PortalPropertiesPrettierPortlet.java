/**
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
package br.com.thiagomoreira.liferay.plugins.portalpropertiesprettier;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.Properties;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.commons.io.IOUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;

import br.com.thiagomoreira.liferay.plugins.portalpropertiesprettier.core.PortalPropertiesPrettier;

@Component(immediate = true, property = {
		"com.liferay.portlet.display-category=category.thiagomoreira",
		"com.liferay.portlet.instanceable=false",
		"com.liferay.portlet.preferences-company-wide=true",
		"com.liferay.portlet.requires-namespaced-parameters=false",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.footer-portlet-javascript=/js/main.js",
		"javax.portlet.display-name=Portal Properties Prettier",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"}, service = Portlet.class)
public class PortalPropertiesPrettierPortlet extends MVCPortlet {

	protected PortalPropertiesPrettier prettier;

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

	@Reference(unbind = "-")
	public void setPortalPropertiesPrettier(PortalPropertiesPrettier prettier) {
		this.prettier = prettier;
	}

	protected String prettify(PortletRequest request) throws IOException,
			PortletException {
		UploadPortletRequest uploadPortletRequest = PortalUtil
				.getUploadPortletRequest(request);

		String liferayVersion = ParamUtil.getString(uploadPortletRequest,
				"liferayVersion", "7.2.0-ga1");
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
