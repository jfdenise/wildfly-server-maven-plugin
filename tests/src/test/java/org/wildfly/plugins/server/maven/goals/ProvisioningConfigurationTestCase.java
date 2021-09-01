/*
 * Copyright 2020 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wildfly.plugins.server.maven.goals;

import java.nio.file.Path;

import org.junit.Test;

/**
 * @author jdenise
 */
public class ProvisioningConfigurationTestCase extends ServerMojoTest {

    public ProvisioningConfigurationTestCase() {
        super("test4-pom.xml", true, "provisioning1.xml");
    }

    @Test
    public void testProvisioningConfiguration() throws Exception {
        BuildServerMojo mojo = lookupMojo("package");
        assertNotNull(mojo);
        assertFalse(mojo.contextRoot);
        mojo.recordState = true;
        mojo.execute();
        String[] layers = {"web-server", "management"};
        final Path dir = getTestDir();
        checkServer(dir, SERVER_DEFAULT_DIR_NAME, 1, true, null, null, mojo.recordState);
        checkDeployment(false, dir, false);
    }
}
