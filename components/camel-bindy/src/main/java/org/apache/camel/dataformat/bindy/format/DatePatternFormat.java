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
package org.apache.camel.dataformat.bindy.format;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.camel.dataformat.bindy.PatternFormat;
import org.apache.camel.util.ObjectHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DatePatternFormat implements PatternFormat<Date> {

    private String pattern;
    private Locale locale = Locale.getDefault();

    public DatePatternFormat() {
    }

    public DatePatternFormat(String pattern, Locale locale) {
        this.pattern = pattern;
        this.locale = locale != null ? locale : Locale.getDefault();
    }

    public String format(Date object) throws Exception {
        ObjectHelper.notNull(this.pattern, "pattern");
        return this.getDateFormat().format(object);
    }

    public Date parse(String string) throws Exception {

        Date date;
        DateFormat df = this.getDateFormat();

        ObjectHelper.notNull(this.pattern, "pattern");

        // Check length of the string with date pattern
        // To avoid to parse a string date : 20090901-10:32:30 when
        // the pattern is yyyyMMdd

        if (string.length() <= this.pattern.length()) {

            // Force the parser to be strict in the syntax of the date to be
            // converted
            df.setLenient(false);
            date = df.parse(string);

            return date;

        } else {
            throw new FormatException("Date provided does not fit the pattern defined");
        }

    }

    protected java.text.DateFormat getDateFormat() {
        return new SimpleDateFormat(this.pattern, locale);
    }

    public String getPattern() {
        return pattern;
    }

    /**
     * Sets the pattern
     * 
     * @param pattern the pattern
     */
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}
