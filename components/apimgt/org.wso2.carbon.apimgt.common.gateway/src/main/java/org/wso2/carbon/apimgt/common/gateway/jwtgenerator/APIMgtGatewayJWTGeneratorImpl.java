/*
 *  Copyright (c) 2021, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.apimgt.common.gateway.jwtgenerator;

import org.apache.commons.lang.StringUtils;
import org.wso2.carbon.apimgt.common.gateway.constants.JWTConstants;
import org.wso2.carbon.apimgt.common.gateway.dto.JWTConfigurationDto;
import org.wso2.carbon.apimgt.common.gateway.dto.JWTInfoDto;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * Default implementation of backend jwt generation
 */
public class APIMgtGatewayJWTGeneratorImpl extends AbstractAPIMgtGatewayJWTGenerator {

    @Override
    public Map<String, Object> populateStandardClaims(JWTInfoDto jwtInfoDto) {

        long currentTime = System.currentTimeMillis();
        long expireIn = currentTime + super.jwtConfigurationDto.getTTL() * 1000;
        String dialect = getDialectURI();
        Map<String, Object> claims = new HashMap<>();
        claims.put("iss", API_GATEWAY_ID);
        claims.put("exp", String.valueOf(expireIn));
        claims.put("iat", String.valueOf(currentTime));
        // dialect is either empty or '/' do not append a backslash. otherwise append a backslash '/'
        if (!"".equals(dialect) && !"/".equals(dialect)) {
            dialect = dialect + "/";
        }
        if (StringUtils.isNotEmpty(jwtInfoDto.getSubscriber())) {
            claims.put(dialect + "subscriber", jwtInfoDto.getSubscriber());
        }
        if (StringUtils.isNotEmpty(jwtInfoDto.getApplicationid())) {
            claims.put(dialect + "applicationid", jwtInfoDto.getApplicationid());
        }
        if (StringUtils.isNotEmpty(jwtInfoDto.getApplicationname())) {
            claims.put(dialect + "applicationname", jwtInfoDto.getApplicationname());
        }
        if (StringUtils.isNotEmpty(jwtInfoDto.getApplicationtier())) {
            claims.put(dialect + "applicationtier", jwtInfoDto.getApplicationtier());
        }
        if (StringUtils.isNotEmpty(jwtInfoDto.getApiName())) {
            claims.put(dialect + "apiname", jwtInfoDto.getApiName());
        }
        if (StringUtils.isNotEmpty(jwtInfoDto.getApicontext())) {
            claims.put(dialect + "apicontext", jwtInfoDto.getApicontext());
        }
        if (StringUtils.isNotEmpty(jwtInfoDto.getVersion())) {
            claims.put(dialect + "version", jwtInfoDto.getVersion());
        }
        if (StringUtils.isNotEmpty(jwtInfoDto.getSubscriptionTier())) {
            claims.put(dialect + "tier", jwtInfoDto.getSubscriptionTier());
        }
        if (StringUtils.isNotEmpty(jwtInfoDto.getKeytype())) {
            claims.put(dialect + "keytype", jwtInfoDto.getKeytype());
        } else {
            claims.put(dialect + "keytype", "PRODUCTION");
        }
        claims.put(dialect + "usertype", JWTConstants.AUTH_APPLICATION_USER_LEVEL_TOKEN);
        claims.put(dialect + "enduser", jwtInfoDto.getEnduser());
        claims.put(dialect + "enduserTenantId", String.valueOf(jwtInfoDto.getEndusertenantid()));
        claims.put(dialect + "applicationUUId", jwtInfoDto.getApplicationuuid());
        Map<String, String> appAttributes = jwtInfoDto.getAppAttributes();
        if (appAttributes != null && !appAttributes.isEmpty()) {
            claims.put(dialect + "applicationAttributes", appAttributes);
        }
        return claims;
    }

    @Override
    public Map<String, Object> populateCustomClaims(JWTInfoDto jwtInfoDto) {

        String[] restrictedClaims = {"iss", "sub", "aud", "exp", "nbf", "iat", "jti", "application", "tierInfo",
                "subscribedAPIs"};
        JWTConfigurationDto jwtConfigurationDto = super.jwtConfigurationDto;
        /*JWTConfigurationDto jwtConfigurationDto =
                ServiceReferenceHolder.getInstance().getAPIManagerConfiguration().getJwtConfigurationDto();*/
        Map<String, Object> claims = new HashMap<>();
        Set<String> jwtExcludedClaims = jwtConfigurationDto.getJWTExcludedClaims();
        jwtExcludedClaims.addAll(Arrays.asList(restrictedClaims));
        Map<String, Object> jwtToken = jwtInfoDto.getJwtValidationInfo().getClaims();
        if (jwtToken != null) {
            for (Map.Entry<String, Object> jwtClaimEntry : jwtToken.entrySet()) {
                if (!jwtExcludedClaims.contains(jwtClaimEntry.getKey())) {
                    claims.put(jwtClaimEntry.getKey(), jwtClaimEntry.getValue());
                }
            }
        }
        return claims;
    }
}

