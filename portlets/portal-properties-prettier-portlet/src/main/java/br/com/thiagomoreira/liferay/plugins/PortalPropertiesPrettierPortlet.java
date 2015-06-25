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
package br.com.thiagomoreira.liferay.plugins;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPoolUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

public class PortalPropertiesPrettierPortlet extends MVCPortlet {

	private static Log log = LogFactoryUtil
			.getLog(PortalPropertiesPrettierPortlet.class);

	public void prettify(ActionRequest request, ActionResponse response)
			throws Exception {
		UploadPortletRequest uploadPortletRequest = PortalUtil
				.getUploadPortletRequest(request);

		String liferayVersion = ParamUtil.getString(uploadPortletRequest,
				"liferayVersion");

		String defaultPortalProperties = getDefaultPortalProperties(liferayVersion);

		Properties customPortalProperties = PropertiesUtil.load(
				uploadPortletRequest.getFileAsStream("portalPropertiesFile"),
				"UTF-8");

		String currentContext = null;
		StringBuilder currentComment = new StringBuilder();
		Set<String> processedContexts = new HashSet<String>();
		StringBuilder pretty = new StringBuilder();
		BufferedReader reader = new BufferedReader(new StringReader(
				defaultPortalProperties));
		String line = reader.readLine();
		int oldCommentLength = 0;

		while (line != null) {
			if (line.startsWith("## ")) {
				currentContext = line;
			}
			if (line.startsWith("    #")) {
				oldCommentLength = currentComment.length();
				currentComment.append("\n");
				currentComment.append(line);
			}
			if (line.length() == 0) {
				currentComment.setLength(0);
			}

			Enumeration<Object> keys = (Enumeration<Object>) customPortalProperties
					.keys();
			while (keys.hasMoreElements()) {
				String key = (String) keys.nextElement();
				String value = fixLineBreak(customPortalProperties
						.getProperty(key));
				if (line.startsWith("    " + key + "=")
						|| line.startsWith("    #" + key + "=")) {
					if (!line.startsWith("    " + key + "=" + value)) {
						if (!processedContexts.contains(currentContext)) {
							pretty.append("\n");
							pretty.append("##");
							pretty.append("\n");
							pretty.append(currentContext);
							pretty.append("\n");
							pretty.append("##");
							processedContexts.add(currentContext);
						}
						if (line.startsWith("    #" + key + "=")) {
							currentComment.setLength(oldCommentLength);

							if (currentComment.length() != 0) {
								pretty.append(currentComment);
								pretty.append("\n");
								currentComment.setLength(0);
							}
						} else {
							pretty.append(currentComment);
							pretty.append("\n");
						}
						pretty.append("    " + key + "=" + value);
						pretty.append("\n");
					} else {
						log.info("Removing property:" + key);
					}
					customPortalProperties.remove(key);
				}

			}
			line = reader.readLine();
		}

		pretty.insert(0,
				processRemainingCustomProperties(customPortalProperties));

		request.setAttribute("portalPrettyProperties", pretty.toString());

		response.setRenderParameter("liferayVersion", liferayVersion);
	}

	protected String fixLineBreak(String text) {
		return text.replaceAll("(\\r|\\n|\\r\\n)+", "\\\\n");
	}

	protected String getDefaultPortalProperties(String liferayVersion)
			throws IOException {
		PortalCache<Serializable, Object> portalCache = SingleVMPoolUtil
				.getCache(PortalPropertiesPrettierPortlet.class.getName());

		String defaultPortalPropertiesURL = "https://raw.githubusercontent.com/liferay/liferay-portal/"
				+ liferayVersion + "/portal-impl/src/portal.properties";
		String defaultPortalProperties = (String) portalCache
				.get(defaultPortalPropertiesURL);

		if (Validator.isNull(defaultPortalProperties)) {
			defaultPortalProperties = HttpUtil
					.URLtoString(defaultPortalPropertiesURL);

			portalCache.put(defaultPortalPropertiesURL,
					defaultPortalProperties, 60);
		}

		return defaultPortalProperties;
	}

	protected String processRemainingCustomProperties(
			Properties customPortalProperties) {
		StringBuilder customProperties = new StringBuilder();

		customProperties.append("##\n## Custom properties\n##");

		Enumeration<Object> keys = (Enumeration<Object>) customPortalProperties
				.keys();
		while (keys.hasMoreElements()) {
			Object key = (Object) keys.nextElement();
			String value = fixLineBreak(customPortalProperties.getProperty(key
					.toString()));
			customProperties.append("\n");
			customProperties.append("    " + key + "=" + value);
		}
		customProperties.append("\n");

		return customProperties.toString();
	}
}
