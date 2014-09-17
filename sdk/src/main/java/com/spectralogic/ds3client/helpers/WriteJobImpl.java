/*
 * ******************************************************************************
 *   Copyright 2014 Spectra Logic Corporation. All Rights Reserved.
 *   Licensed under the Apache License, Version 2.0 (the "License"). You may not use
 *   this file except in compliance with the License. A copy of the License is located at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   or in the "license" file accompanying this file.
 *   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 *   CONDITIONS OF ANY KIND, either express or implied. See the License for the
 *   specific language governing permissions and limitations under the License.
 * ****************************************************************************
 */

package com.spectralogic.ds3client.helpers;

import com.spectralogic.ds3client.Ds3Client;
import com.spectralogic.ds3client.commands.PutObjectRequest;
import com.spectralogic.ds3client.helpers.Ds3ClientHelpers.ObjectChannelBuilder;
import com.spectralogic.ds3client.models.bulk.BulkObject;
import com.spectralogic.ds3client.models.bulk.Objects;

import java.io.IOException;
import java.security.SignatureException;
import java.util.UUID;

class WriteJobImpl extends JobImpl {
    public WriteJobImpl(
            final Ds3ClientFactory clientFactory,
            final UUID jobId,
            final String bucketName,
            final Iterable<? extends Objects> objectLists) {
        super(clientFactory, jobId, bucketName, objectLists);
    }

    @Override
    protected void transferItem(
            final Ds3Client client,
            final UUID jobId,
            final String bucketName,
            final BulkObject ds3Object,
            final ObjectChannelBuilder transferrer) throws SignatureException, IOException {
        client
            .putObject(new PutObjectRequest(
                bucketName,
                ds3Object.getName(),
                jobId,
                ds3Object.getLength(),
                ds3Object.getOffset(),
                transferrer.buildChannel(ds3Object.getName())
            ));
    }
}