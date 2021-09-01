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
public class HollowServerTestCase extends ServerMojoTest {

    public HollowServerTestCase() {
        super("test3-pom.xml", true, null);
    }

    @Test
    public void testHollowJar() throws Exception {
        BuildServerMojo mojo = lookupMojo("package");
        assertNotNull(mojo);
        assertTrue(mojo.hollowServer);
        assertEquals(2, mojo.layers.size());
        assertEquals("cloud-profile", mojo.layers.get(0));
        assertEquals("management", mojo.layers.get(1));
        assertEquals(1, mojo.excludedLayers.size());
        assertEquals("ee-security", mojo.excludedLayers.get(0));
        mojo.recordState = true;
        mojo.execute();
        String[] layers = {"cloud-profile", "management"};
        String[] excludedLayers = {"ee-security"};
        final Path dir = getTestDir();
        checkServer(dir, SERVER_DEFAULT_DIR_NAME, 0, false, layers, excludedLayers, mojo.recordState);
        checkMetrics(false, dir, true);
    }
}
