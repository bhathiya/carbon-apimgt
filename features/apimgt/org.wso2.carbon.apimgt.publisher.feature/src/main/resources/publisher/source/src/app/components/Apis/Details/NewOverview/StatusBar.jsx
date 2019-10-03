/*
 * Copyright (c) 2019, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

import React from 'react';
import PropTypes from 'prop-types';
import Grid from '@material-ui/core/Grid';
import ThumbnailView from 'AppComponents/Apis/Listing/components/ImageGenerator/ThumbnailView';
import { isRestricted } from 'AppData/AuthManager';

/**
 *
 *
 * @param {*} props
 * @returns
 */
export default function StatusBar(props) {
    const { api } = props;
    return (
        <React.Fragment>
            <Grid container spacing={24}>
                <Grid item xs={12} md={2} lg={2}>
                    {/* Thumbnail */}
                    <ThumbnailView
                        api={api}
                        width={80}
                        height={80}
                        isEditable={!isRestricted(['apim:api_publish', 'apim:api_create'], api)}
                    />
                </Grid>
                <Grid item xs={12} md={6} lg={6}>
                    {/* Status */}
                </Grid>
            </Grid>
        </React.Fragment>
    );
}

StatusBar.propTypes = {
    api: PropTypes.shape({
        id: PropTypes.string,
    }).isRequired,
};