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
package org.apache.camel.component.jmx;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Builder for JMX endpoint URI's. Saves you from having to do the string concat'ing
 * and messing up the param names
 */
public class JMXUriBuilder {
    private Map<String, String> mQueryProps = new LinkedHashMap<String, String>();
    private String mServerName = "platform";

    public JMXUriBuilder() {
    }

    public JMXUriBuilder(String aServerName) {
        setServerName(aServerName);
    }

    public JMXUriBuilder withFormat(String aFormat) {
        addProperty("format", aFormat);
        return this;
    }

    public JMXUriBuilder withUser(String aFormat) {
        addProperty("user", aFormat);
        return this;
    }

    public JMXUriBuilder withPassword(String aFormat) {
        addProperty("password", aFormat);
        return this;
    }

    public JMXUriBuilder withObjectDomain(String aFormat) {
        addProperty("objectDomain", aFormat);
        return this;
    }

    public JMXUriBuilder withObjectName(String aFormat) {
        addProperty("objectName", aFormat);
        return this;
    }

    public JMXUriBuilder withNotificationFilter(String aFilter) {
        addProperty("notificationFilter", aFilter);
        return this;
    }

    public JMXUriBuilder withHandback(String aHandback) {
        addProperty("handback", aHandback);
        return this;
    }

    /**
     * Converts all of the values to params with the "key." prefix so the
     * component will pick up on them and set them on the endpoint. Alternatively,
     * you can pass in a reference to a Hashtable using the version of this
     * method that takes a single string.
     */
    public JMXUriBuilder withObjectProperties(Map<String, String> aPropertiesSansKeyPrefix) {
        for (Entry<String, String> entry : aPropertiesSansKeyPrefix.entrySet()) {
            addProperty("key." + entry.getKey(), entry.getValue());
        }
        return this;
    }

    /**
     * Your value should start with a hash mark since it's a reference to a value.
     * This method will add the hash mark if it's not present.
     */
    public JMXUriBuilder withObjectPropertiesReference(String aReferenceToHashtable) {
        if (aReferenceToHashtable.startsWith("#")) {
            addProperty("objectProperties", aReferenceToHashtable);
        } else {
            addProperty("objectProperties", "#" + aReferenceToHashtable);
        }
        return this;
    }

    protected void addProperty(String aName, String aValue) {
        mQueryProps.put(aName, aValue);
    }

    public String getServerName() {
        return mServerName;
    }

    public void setServerName(String aServerName) {
        mServerName = aServerName;
    }

    public JMXUriBuilder withServerName(String aServerName) {
        setServerName(aServerName);
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("jmx:").append(getServerName());
        if (!mQueryProps.isEmpty()) {
            sb.append('?');

            String delim = "";
            for (Entry<String, String> entry : mQueryProps.entrySet()) {
                sb.append(delim);
                sb.append(entry.getKey()).append('=').append(entry.getValue());
                delim = "&";
            }
        }
        return sb.toString();
    }

}
