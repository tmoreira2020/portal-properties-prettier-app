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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPoolUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

public class PortalPropertiesPrettier {

	private static Log log = LogFactoryUtil
			.getLog(PortalPropertiesPrettier.class);

	public String prettify(Properties customProperties, String liferayVersion)
			throws Exception {

		String defaultPortalProperties = getDefaultPortalProperties(liferayVersion);
		String currentContext = null;
		StringBuilder currentComment = new StringBuilder();
		Set<String> processedContexts = new HashSet<String>();
		Properties removedProperties = new Properties();
		StringBuilder pretty = new StringBuilder();
		BufferedReader reader = new BufferedReader(new StringReader(
				defaultPortalProperties));
		String line = reader.readLine();
		int oldCommentLength = 0;
		Pattern keyDigitPattern = Pattern.compile("([a-z]|\\.|\\[|\\])+(\\d)$");

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

			Enumeration<Object> keys = (Enumeration<Object>) customProperties
					.keys();
			while (keys.hasMoreElements()) {
				String key = (String) keys.nextElement();
				String value = fixLineBreak(customProperties.getProperty(key));
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

						Matcher matcher = keyDigitPattern.matcher(key);
						if (matcher.matches()) {

							String digitFound = matcher.group(2);
							for (int i = 1; i < 10; i++) {
								String tempKey = key.replace(digitFound,
										String.valueOf(i));
								value = fixLineBreak(customProperties
										.getProperty(tempKey));

								if (value != null) {
									pretty.append("    " + tempKey + "="
											+ value);
									pretty.append("\n");

									customProperties.remove(tempKey);
								}
							}
						}
					} else {
						removedProperties.put(key, value);
					}
					customProperties.remove(key);
				}

			}
			line = reader.readLine();
		}

		pretty.insert(0, processRemainingCustomProperties(customProperties));

		pretty.insert(0, processRemovedProperties(removedProperties));

		return pretty.toString();
	}

	protected String fixLineBreak(String text) {
		if (text == null) {
			return null;
		}

		return text.replaceAll("(\\r|\\n|\\r\\n)+", "\\\\n");
	}

	protected String getDefaultPortalProperties(String liferayVersion)
			throws IOException {
		PortalCache<Serializable, Object> portalCache = SingleVMPoolUtil
				.getCache(PortalPropertiesPrettier.class.getName());

		String defaultPortalPropertiesURL = "https://raw.githubusercontent.com/liferay/liferay-portal/"
				+ liferayVersion + "/portal-impl/src/portal.properties";
		String defaultPortalProperties = (String) portalCache
				.get(defaultPortalPropertiesURL);

		if (Validator.isNull(defaultPortalProperties)) {
			defaultPortalProperties = HttpUtil
					.URLtoString(defaultPortalPropertiesURL);

			portalCache.put(defaultPortalPropertiesURL,
					defaultPortalProperties, 60 * 60 * 24);
		}

		return defaultPortalProperties;
	}

	protected String processRemainingCustomProperties(
			Properties customPortalProperties) {
		if (customPortalProperties.isEmpty()) {
			return StringPool.BLANK;
		}

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

	protected String processRemovedProperties(Properties removedProperties) {
		if (removedProperties.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("##\n## Removed properties\n##");

		Enumeration<Object> keys = (Enumeration<Object>) removedProperties
				.keys();
		while (keys.hasMoreElements()) {
			Object key = (Object) keys.nextElement();
			String value = fixLineBreak(removedProperties.getProperty(key
					.toString()));
			stringBuilder.append("\n");
			stringBuilder.append("    #" + key + "=" + value);
		}
		stringBuilder.append("\n\n");

		return stringBuilder.toString();
	}
}