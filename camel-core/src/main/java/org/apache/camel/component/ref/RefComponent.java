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
package org.apache.camel.component.ref;

import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;

/**
 * Component for lookup of existing endpoints bound in the {@link org.apache.camel.spi.Registry}.
 * <p/>
 * This component uses the <tt>ref:</tt> notation instead of the mostly common <tt>uri:</tt> notation. 
 */
public class RefComponent extends DefaultComponent {

    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        // lets remove the scheme from the URI
        int index = uri.indexOf(':');
        String name = uri;
        if (index >= 0) {
            name = uri.substring(index + 1);
        }
        if (name.startsWith("//")) {
            name = name.substring(2);
        }
        return lookupEndpoint(name, parameters);
    }

    /**
     * Looks up an endpoint for a given name.
     *
     * Derived classes could use this name as a logical name and look it up on some registry.
     *
     * The default implementation will look up the name in the registry of the {@link #getCamelContext()} property
     */
    protected Endpoint lookupEndpoint(String name, Map<String, Object> parameters) {
        return getCamelContext().getRegistry().lookup(name, Endpoint.class);
    }
}
