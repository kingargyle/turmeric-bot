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
package org.apache.camel.component.quickfixj;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.FactoryBean;
import quickfix.Dictionary;
import quickfix.SessionID;
import quickfix.SessionSettings;

public class QuickfixjSettingsFactory implements FactoryBean<SessionSettings> {
    private Map<Object, Object> defaultSettings = Collections.emptyMap();
    private Map<SessionID, Map<Object, Object>> sessionSettings = Collections.emptyMap();

    public SessionSettings getObject() throws Exception {
        SessionSettings settings = new SessionSettings();
        settings.set(new Dictionary("defaults", defaultSettings));
        for (Map.Entry<SessionID, Map<Object, Object>> sessionSetting : sessionSettings.entrySet()) {
            settings.set(sessionSetting.getKey(), new Dictionary("session", sessionSetting.getValue()));
        }
        return settings;
    }

    public Class<?> getObjectType() {
        return SessionSettings.class;
    }

    public boolean isSingleton() {
        return true;
    }

    public void setDefaultSettings(Map<Object, Object> defaultSettings) {
        this.defaultSettings = defaultSettings;
    }

    public void setSessionSettings(Map<SessionID, Map<Object, Object>> sessionSettings) {
        this.sessionSettings = sessionSettings;
    }
}
