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
package org.apache.camel.model;

import org.apache.camel.ContextTestSupport;
import org.apache.camel.FailedToCreateRouteException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.JndiRegistry;
import org.apache.camel.spi.DataFormat;

/**
 * @version $Revision$
 */
public class StartingRoutesErrorReportedTest extends ContextTestSupport {

    public void testInvalidFrom() throws Exception {
        try {
            context.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from("direct:start?foo=bar").routeId("route1").to("mock:result");
                }
            });
            context.start();
            fail();
        } catch (FailedToCreateRouteException e) {
            assertTrue(e.getMessage().startsWith("Failed to create route route1: Route[[From[direct:start?foo=bar]] -> [To[mock:result]]] because of"));
        }
    }

    public void testInvalidTo() throws Exception {
        try {
            context.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from("direct:start").routeId("route2").to("mock:result?foo=bar");
                }
            });
            context.start();
            fail();
        } catch (FailedToCreateRouteException e) {
            assertTrue(e.getMessage().startsWith("Failed to create route route2 at: >>> To[mock:result?foo=bar] <<< in route: Route[[From[direct:start]] -> [To[mock:result?foo=bar]]] because of"));
        }
    }

    public void testInvalidBean() throws Exception {
        try {
            context.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from("direct:start").routeId("route3")
                        .to("mock:foo")
                        .beanRef("");
                }
            });
            context.start();
        } catch (FailedToCreateRouteException e) {
            assertTrue(e.getMessage().startsWith("Failed to create route route3 at: >>> Bean[ref:] <<< in route: Route[[From[direct:start]] -> [To[mock:foo], Bean[ref:]]] because of"));
        }
    }

    public void testUnavailableDataFormatOnClasspath() throws Exception {
        try {
            context.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    
                    from("direct:start").routeId("route3")
                        .unmarshal().jaxb()
                        .log("Will never get here");
                }
            });
            context.start();
        } catch (FailedToCreateRouteException e) {
            assertTrue(e.getMessage().contains("Ensure that the dataformat is valid and the associated Camel component is present on the classpath"));
        }
    }
    
    @Override
    public boolean isUseRouteBuilder() {
        return false;
    }
}
