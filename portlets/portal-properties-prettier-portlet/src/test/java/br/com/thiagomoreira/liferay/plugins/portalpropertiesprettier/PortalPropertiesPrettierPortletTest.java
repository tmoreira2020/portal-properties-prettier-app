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

import java.io.ByteArrayInputStream;
import java.util.Properties;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import static org.easymock.EasyMock.*;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.portlet.MockActionRequest;
import org.springframework.mock.web.portlet.MockActionResponse;
import org.springframework.mock.web.portlet.MockPortletPreferences;
import org.springframework.mock.web.portlet.MockPortletRequest;
import org.springframework.mock.web.portlet.MockResourceRequest;
import org.springframework.mock.web.portlet.MockResourceResponse;

import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;

import br.com.thiagomoreira.liferay.plugins.portalpropertiesprettier.core.PortalPropertiesPrettier;

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

	@Test
	public void testPrettifyActionRequestActionResponse() throws Exception {
		MockActionRequest request = new MockActionRequest();
		MockActionResponse response = new MockActionResponse();

		PortalPropertiesPrettierPortlet portlet = createMockBuilder(
				PortalPropertiesPrettierPortlet.class).addMockedMethod(
				"prettify", PortletRequest.class).createMock();
		String expected = "expected";

		expect(portlet.prettify(request)).andReturn(expected);
		replay(portlet);

		portlet.prettify(request, response);

		Assert.assertEquals(expected,
				request.getAttribute("portalPrettyProperties"));
		verify(portlet);
	}

	@Test
	public void testPrettifyPortletRequest() throws Exception {
		String liferayVersion = "6.2.3-ga4";
		boolean printDefaultValue = false;
		MockActionRequest request = new MockActionRequest();
		String expected = "expected";
		ByteArrayInputStream in = new ByteArrayInputStream(expected.getBytes());
		UploadPortletRequest uploadPortletRequest = createNiceMock(UploadPortletRequest.class);

		expect(uploadPortletRequest.getParameter("liferayVersion")).andReturn(
				liferayVersion);
		expect(uploadPortletRequest.getParameter("printDefaultValue"))
				.andReturn(String.valueOf(printDefaultValue));
		expect(uploadPortletRequest.getFileAsStream("portalPropertiesFile"))
				.andReturn(in);
		replay(uploadPortletRequest);

		Portal portal = createMock(Portal.class);

		expect(portal.getUploadPortletRequest(request)).andReturn(
				uploadPortletRequest);
		replay(portal);
		new PortalUtil().setPortal(portal);

		PortalPropertiesPrettierPortlet portlet = createMockBuilder(
				PortalPropertiesPrettierPortlet.class).addMockedMethod(
				"incrementCounter").createMock();

		PortalPropertiesPrettier prettier = createMock(PortalPropertiesPrettier.class);
		expect(
				prettier.prettify(anyObject(Properties.class),
						eq(liferayVersion), eq(printDefaultValue))).andReturn(
				expected);
		replay(prettier);

		portlet.prettier = prettier;

		portlet.incrementCounter(request);
		replay(portlet);

		String actual = portlet.prettify(request);

		Assert.assertEquals(expected, actual);
		verify(portlet);
		verify(portal);
		verify(uploadPortletRequest);
		verify(prettier);
	}

	@Test
	public void testServeResource() throws Exception {
		MockResourceRequest request = new MockResourceRequest();
		MockResourceResponse response = new MockResourceResponse();

		PortalPropertiesPrettierPortlet portlet = createMockBuilder(
				PortalPropertiesPrettierPortlet.class).addMockedMethod(
				"prettify", PortletRequest.class).createMock();
		String expected = "expected";

		expect(portlet.prettify(request)).andReturn(expected);
		replay(portlet);

		portlet.serveResource(request, response);

		Assert.assertEquals(expected, response.getContentAsString());
		verify(portlet);
	}
}
