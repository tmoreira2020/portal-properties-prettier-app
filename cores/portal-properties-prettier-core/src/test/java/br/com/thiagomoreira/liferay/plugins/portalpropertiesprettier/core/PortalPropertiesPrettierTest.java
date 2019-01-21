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
package br.com.thiagomoreira.liferay.plugins.portalpropertiesprettier.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import com.liferay.portal.kernel.util.PropertiesUtil;

import br.com.thiagomoreira.liferay.plugins.portalpropertiesprettier.core.PortalPropertiesPrettier;
import br.com.thiagomoreira.liferay.plugins.portalpropertiesprettier.core.PortalPropertiesPrettierImpl;

public class PortalPropertiesPrettierTest {

	@Test
	public void testFixLineBreakNull() {
		PortalPropertiesPrettierImpl prettier = new PortalPropertiesPrettierImpl();

		String actual = prettier.fixLineBreak(null);

		Assert.assertNull(actual);
	}

	@Test
	public void testFixLineBreakNotNull() {
		PortalPropertiesPrettierImpl prettier = new PortalPropertiesPrettierImpl();

		String expected = "\\n";

		String actual = prettier.fixLineBreak("\n");

		Assert.assertEquals(expected, actual);

		actual = prettier.fixLineBreak("\r");

		Assert.assertEquals(expected, actual);

		actual = prettier.fixLineBreak("\r\n");

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testIssue10() throws Exception {
		PortalPropertiesPrettierImpl prettier = new PortalPropertiesPrettierImpl();
		String expected = getContent("/portal-issue-10-expected.properties");
		String actual = getContent("/portal-issue-10.properties");
		Properties customProperties = PropertiesUtil.load(actual);

		actual = prettier.prettify(customProperties, "6.2.3-ga4");

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testIssue12() throws Exception {
		PortalPropertiesPrettierImpl prettier = new PortalPropertiesPrettierImpl();
		String expected = getContent("/portal-issue-12-expected.properties");
		String actual = getContent("/portal-issue-12.properties");
		Properties customProperties = PropertiesUtil.load(actual);

		actual = prettier.prettify(customProperties, "6.2.3-ga4");

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testIssue13() throws Exception {
		PortalPropertiesPrettier prettier = new PortalPropertiesPrettierImpl();
		String expected = getContent("/portal-issue-13-expected.properties");
		String actual = getContent("/portal-issue-13.properties");
		Properties customProperties = PropertiesUtil.load(actual);

		actual = prettier.prettify(customProperties, "6.2.3-ga4", true);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testIssue18() throws Exception {
		PortalPropertiesPrettier prettier = new PortalPropertiesPrettierImpl();
		String expected = getContent("/portal-issue-18-expected.properties");
		String actual = getContent("/portal-issue-18.properties");
		Properties customProperties = PropertiesUtil.load(actual);

		actual = prettier.prettify(customProperties, "6.2.3-ga4", true);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testIssue19() throws Exception {
		PortalPropertiesPrettier prettier = new PortalPropertiesPrettierImpl();
		String expected = getContent("/portal-issue-19-expected.properties");
		String actual = getContent("/portal-issue-19.properties");
		Properties customProperties = PropertiesUtil.load(actual);

		actual = prettier.prettify(customProperties, "6.2.3-ga4", true);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testIssue22() throws Exception {
		PortalPropertiesPrettier prettier = new PortalPropertiesPrettierImpl();
		String expected = getContent("/portal-issue-22-expected.properties");
		String actual = getContent("/portal-issue-22.properties");
		Properties customProperties = PropertiesUtil.load(actual);

		actual = prettier.prettify(customProperties, "6.2.3-ga4", false);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testIssue27DB2() throws Exception {
		PortalPropertiesPrettier prettier = new PortalPropertiesPrettierImpl();
		String expected = getContent("/portal-issue-27-db2-expected.properties");
		String actual = getContent("/portal-issue-27-db2.properties");
		Properties customProperties = PropertiesUtil.load(actual);

		actual = prettier.prettify(customProperties, "6.2.3-ga4", false);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testIssue27Derby() throws Exception {
		PortalPropertiesPrettier prettier = new PortalPropertiesPrettierImpl();
		String expected = getContent("/portal-issue-27-derby-expected.properties");
		String actual = getContent("/portal-issue-27-derby.properties");
		Properties customProperties = PropertiesUtil.load(actual);

		actual = prettier.prettify(customProperties, "6.2.3-ga4", false);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testIssue27MySQL() throws Exception {
		PortalPropertiesPrettier prettier = new PortalPropertiesPrettierImpl();
		String expected = getContent("/portal-issue-27-mysql-expected.properties");
		String actual = getContent("/portal-issue-27-mysql.properties");
		Properties customProperties = PropertiesUtil.load(actual);

		actual = prettier.prettify(customProperties, "6.2.3-ga4", false);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testIssue27SQLServer() throws Exception {
		PortalPropertiesPrettier prettier = new PortalPropertiesPrettierImpl();
		String expected = getContent("/portal-issue-27-sqlserver-expected.properties");
		String actual = getContent("/portal-issue-27-sqlserver.properties");
		Properties customProperties = PropertiesUtil.load(actual);

		actual = prettier.prettify(customProperties, "6.2.3-ga4", false);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testIssue28() throws Exception {
		PortalPropertiesPrettier prettier = new PortalPropertiesPrettierImpl();
		String expected = getContent("/portal-issue-28-expected.properties");
		String actual = getContent("/portal-issue-28.properties");
		Properties customProperties = PropertiesUtil.load(actual);

		actual = prettier.prettify(customProperties, "6.2.3-ga4", false);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testIssue30() throws Exception {
		PortalPropertiesPrettier prettier = new PortalPropertiesPrettierImpl();
		String expected = getContent("/portal-issue-30-expected.properties");
		String actual = getContent("/portal-issue-30.properties");
		Properties customProperties = PropertiesUtil.load(actual);

		actual = prettier.prettify(customProperties, "6.2.3-ga4", false);

		Assert.assertEquals(expected, actual);
	}

	protected String getContent(String path) throws IOException {
		InputStream in = getClass().getResourceAsStream(path);

		try {
			return IOUtils.toString(in);
		} catch (IOException ioex) {
			throw ioex;
		} finally {
			IOUtils.closeQuietly(in);
		}
	}

}
