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

import org.junit.Assert;
import org.junit.Test;

public class PortalPropertiesPrettierTest {

	@Test
	public void testFixLineBreakNull() {
		PortalPropertiesPrettier prettier = new PortalPropertiesPrettier();

		String actual = prettier.fixLineBreak(null);

		Assert.assertNull(actual);
	}

	@Test
	public void testFixLineBreakNotNull() {
		PortalPropertiesPrettier prettier = new PortalPropertiesPrettier();

		String expected = "\\n";

		String actual = prettier.fixLineBreak("\n");

		Assert.assertEquals(expected, actual);

		actual = prettier.fixLineBreak("\r");

		Assert.assertEquals(expected, actual);

		actual = prettier.fixLineBreak("\r\n");

		Assert.assertEquals(expected, actual);
	}
}
