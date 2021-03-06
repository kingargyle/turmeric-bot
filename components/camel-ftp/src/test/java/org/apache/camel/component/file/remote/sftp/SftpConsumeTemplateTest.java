/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.file.remote.sftp;

import org.apache.camel.Exchange;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @version $Revision$
 */
@Ignore("Disabled due CI servers fails on full build running with these tests")
public class SftpConsumeTemplateTest extends SftpServerTestSupport {

    @Override
    public boolean isUseRouteBuilder() {
        return false;
    }

    @Test
    public void testSftpSimpleConsume() throws Exception {
        if (!canTest()) {
            return;
        }

        // create file using regular file
        template.sendBodyAndHeader("file://" + FTP_ROOT_DIR, "Hello World", Exchange.FILE_NAME, "hello.txt");

        String out = consumer.receiveBody("sftp://localhost:" + getPort() + "/" + FTP_ROOT_DIR + "?username=admin&password=admin", 5000, String.class);
        assertNotNull(out);
        // Apache SSHD appends \u0000 at last byte in retrieved file
        assertTrue(out.startsWith("Hello World"));
    }

}
