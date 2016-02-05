/*
 * ******************************************************************************
 *   Copyright 2014-2015 Spectra Logic Corporation. All Rights Reserved.
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

package com.spectralogic.ds3client.helpers.options;

import com.spectralogic.ds3client.models.BlobStoreTaskPriority;
import com.spectralogic.ds3client.models.ChecksumType;
import com.spectralogic.ds3client.models.WriteOptimization;

public class WriteJobOptions {
    private BlobStoreTaskPriority priority;
    private WriteOptimization writeOptimization;
    private int maxUploadSize;
    private ChecksumType.Type checksumType;

    private WriteJobOptions() {
        this.priority = null;
        this.writeOptimization = null;
        this.maxUploadSize = 0;
        this.checksumType = ChecksumType.Type.NONE;
    }

    public static WriteJobOptions create() {
        return new WriteJobOptions();
    }

    public WriteJobOptions withMaxUploadSize(final int maxUploadSize) {
        this.maxUploadSize = maxUploadSize;
        return this;
    }

    public long getMaxUploadSize() {
        return this.maxUploadSize;
    }

    public WriteJobOptions withWriteOptimization(final WriteOptimization writeOptimization) {
        this.writeOptimization = writeOptimization;
        return this;
    }

    public WriteOptimization getWriteOptimization() {
        return writeOptimization;
    }

    public void setWriteOptimization(final WriteOptimization writeOptimization) {
        this.writeOptimization = writeOptimization;
    }

    public WriteJobOptions withPriority(final BlobStoreTaskPriority priority) {
        this.priority = priority;
        return this;
    }

    public BlobStoreTaskPriority getPriority() {
        return priority;
    }

    public void setPriority(final BlobStoreTaskPriority priority) {
        this.priority = priority;
    }

    public WriteJobOptions withChecksumType(final ChecksumType.Type checksumType) {
        this.checksumType = checksumType;
        return this;
    }

    public ChecksumType.Type getChecksumType() {
        return checksumType;
    }

    public void setChecksumType(final ChecksumType.Type checksumType) {
        this.checksumType = checksumType;
    }
}
