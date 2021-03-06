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
package org.apache.camel.component.bean;

import java.io.InputStream;
import java.io.Reader;

import org.apache.camel.Handler;

/**
 * @version $Revision$
 */
public class MyDummyBean {

    public String hello(String s) {
        return "Hello " + s;
    }

    public String hello(String s, String t) {
        return "Hello " + s + " and " + t;
    }

    @Handler
    public String bye(String s) {
        return "Bye " + s;
    }
    
    public String bar(String s) {
        return "String";
    }
    
    public String bar(Reader s) {
        return "Reader";
    }
    
    public String bar(InputStream s) {
        return "InputStream";
    }
}
