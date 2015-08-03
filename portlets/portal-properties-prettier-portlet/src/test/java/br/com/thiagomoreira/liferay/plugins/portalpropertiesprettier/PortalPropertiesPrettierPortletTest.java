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

import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.portlet.MockPortletPreferences;
import org.springframework.mock.web.portlet.MockPortletRequest;

import com.liferay.portal.kernel.util.GetterUtil;

public class PortalPropertiesPrettierPortletTest {

	@Test
	public void testIncrementCounter() throws Exception {
		int counter = 10;
		PortletPreferences preferences = new MockPortletPreferences();

		preferences.setValue("counter", String.valueOf(counter));

		MockPortletRequest request = new MockPortletRequest();

		request.setPreferences(preferences);

		PortalPropertiesPrettierPortlet portlet = new PortalPropertiesPrettierPortlet();

		portlet.incrementCounter(request);

		String actual = preferences.getValue("counter", "0");

		Assert.assertEquals(counter + 1, GetterUtil.getInteger(actual));
	}
}
