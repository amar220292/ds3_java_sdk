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

// This code is auto-generated, do not modify
package com.spectralogic.ds3client.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.String;

public class BuildInformation {

    // Variables
    @JsonProperty("Branch")
    private String branch;

    @JsonProperty("Revision")
    private String revision;

    @JsonProperty("Version")
    private String version;

    // Constructor
    public BuildInformation(final String branch, final String revision, final String version) {
        this.branch = branch;
        this.revision = revision;
        this.version = version;
    }

    // Getters and Setters
    
    public String getBranch() {
        return this.branch;
    }

    public void setBranch(final String branch) {
        this.branch = branch;
    }


    public String getRevision() {
        return this.revision;
    }

    public void setRevision(final String revision) {
        this.revision = revision;
    }


    public String getVersion() {
        return this.version;
    }

    public void setVersion(final String version) {
        this.version = version;
    }

}