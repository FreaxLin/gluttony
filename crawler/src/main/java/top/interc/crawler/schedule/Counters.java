/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package top.interc.crawler.schedule;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


public class Counters {
    private static final Logger logger = LoggerFactory.getLogger(Counters.class);

    public static class ReservedCounterNames {
        public static final String SCHEDULED_PAGES = "Scheduled-Pages";
        public static final String PROCESSED_PAGES = "Processed-Pages";
    }

    private static final String DATABASE_NAME = "Statistics";

    protected final Object mutex = new Object();

    protected Map<String, Long> counterValues;

    public Counters() {

    }

    public long getValue(String name) {
        synchronized (mutex) {
            Long value = counterValues.get(name);
            if (value == null) {
                return 0;
            }
            return value;
        }
    }

    public void setValue(String name, long value) {

    }

    public void increment(String name) {
        increment(name, 1);
    }

    public void increment(String name, long addition) {
        synchronized (mutex) {
            long prevValue = getValue(name);
            setValue(name, prevValue + addition);
        }
    }

    public void close() {

    }
}