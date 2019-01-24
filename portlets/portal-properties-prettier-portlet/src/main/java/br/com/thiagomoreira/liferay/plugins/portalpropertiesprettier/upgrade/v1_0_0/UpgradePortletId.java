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
package br.com.thiagomoreira.liferay.plugins.portalpropertiesprettier.upgrade.v1_0_0;

import com.liferay.portal.kernel.upgrade.BaseUpgradePortletId;

public class UpgradePortletId extends BaseUpgradePortletId {

	@Override
	protected String[][] getRenamePortletIdsArray() {
		return new String[][]{new String[]{
				"portalpropertiesprettierportlet_WAR_portalpropertiesprettierportlet",
				"br_com_thiagomoreira_liferay_plugins_portalpropertiesprettier_PortalPropertiesPrettierPortlet"}};
	}

}
