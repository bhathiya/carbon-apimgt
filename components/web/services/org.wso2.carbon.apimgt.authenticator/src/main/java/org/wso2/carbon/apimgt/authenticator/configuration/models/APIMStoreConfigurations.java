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

package org.wso2.carbon.apimgt.authenticator.configuration.models;

import org.wso2.carbon.kernel.annotations.Configuration;
import org.wso2.carbon.kernel.annotations.Element;

/**
 * Class to hold store application configurations.
 */
@Configuration(namespace = "wso2.carbon.apimgt.store", description = "APIM Store Configuration Parameters")
public class APIMStoreConfigurations {
    @Element(description = "APIM Base URL")
    private String apimBaseUrl = "https://localhost:9292/";
    @Element(description = "SSO Enabled or not")
    private boolean ssoEnabled = true;

    public String getApimBaseUrl() {
        return apimBaseUrl;
    }

    public void setApimBaseUrl(String apimBaseUrl) {
        this.apimBaseUrl = apimBaseUrl;
    }

    public boolean isSsoEnabled() {
        return ssoEnabled;
    }

    public void setSsoEnabled(boolean ssoEnabled) {
        this.ssoEnabled = ssoEnabled;
    }
}
