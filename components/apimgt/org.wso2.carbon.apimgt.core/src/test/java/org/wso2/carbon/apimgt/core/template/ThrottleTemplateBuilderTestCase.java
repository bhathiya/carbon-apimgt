/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.apimgt.core.template;


import com.google.common.io.Files;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.carbon.apimgt.core.SampleTestObjectCreator;
import org.wso2.carbon.apimgt.core.models.policy.ApplicationPolicy;
import org.wso2.carbon.apimgt.core.models.policy.CustomPolicy;
import org.wso2.carbon.apimgt.core.models.policy.SubscriptionPolicy;

import java.io.File;

/**
 * Test cases for API Throttle policy template builder.
 */

public class ThrottleTemplateBuilderTestCase {

    @BeforeClass
    void init() {
        File temp = Files.createTempDir();
        temp.deleteOnExit();
        System.setProperty("gwHome", temp.getAbsolutePath());
        //Set the resource path, where contain composer test JS
        System.setProperty("carbon.home", new File("src/test/").getAbsolutePath());
    }

    @Test
    public void testSiddiQueryForApp() throws APITemplateException {
        ApplicationPolicy policy = SampleTestObjectCreator.createDefaultApplicationPolicy();
        ApplicationThrottlePolicyTemplateBuilder templateBuilder = new ApplicationThrottlePolicyTemplateBuilder(policy);
        String siddhiQuery = templateBuilder.getThrottlePolicyForAppLevel(policy);
        String sampleQuery = SampleTestObjectCreator.createDefaultSiddhiAppforAppPolicy();
        Assert.assertEquals(siddhiQuery, sampleQuery);
    }

    @Test
    public void testSiddhiQueryForSubscriptionPolicy() throws APITemplateException {
        SubscriptionPolicy policy = SampleTestObjectCreator.createDefaultSubscriptionPolicy();
        SubscriptionThrottlePolicyTemplateBuilder templateBuilder = new SubscriptionThrottlePolicyTemplateBuilder(
                policy);
        String siddhiQuery = templateBuilder.getThrottlePolicyForSubscriptionLevel(policy);
        String sampleQuery = SampleTestObjectCreator.createDefaultSiddhiAppforSubscriptionPolicy();
        Assert.assertEquals(siddhiQuery, sampleQuery);
    }

    @Test
    public void testSiddhiQueryForCustomPolicy() throws APITemplateException {
        CustomPolicy policy = SampleTestObjectCreator.createDefaultCustomPolicy();
        CustomThrottlePolicyTemplateBuilder templateBuilder = new CustomThrottlePolicyTemplateBuilder(
                policy);
        String siddhiQuery = templateBuilder.getThrottlePolicyTemplateForCustomPolicy(policy);
        String sampleQuery = SampleTestObjectCreator.createDefaultCustomPolicySiddhiApp();
        Assert.assertEquals(siddhiQuery, sampleQuery);
    }
}
