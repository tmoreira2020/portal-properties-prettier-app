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

		String fixed = prettier.fixLineBreak(null);

		Assert.assertNull(fixed);
	}

	@Test
	public void testFixLineBreakNotNull() {
		PortalPropertiesPrettier prettier = new PortalPropertiesPrettier();

		String fixed = prettier.fixLineBreak("\n");

		Assert.assertEquals("\\n", fixed);

		fixed = prettier.fixLineBreak("\r");

		Assert.assertEquals("\\n", fixed);

		fixed = prettier.fixLineBreak("\r\n");

		Assert.assertEquals("\\n", fixed);
	}
}
