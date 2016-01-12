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
import java.util.UUID;

public class User {

    // Variables
    @JsonProperty("AuthId")
    private String authId;

    @JsonProperty("DefaultDataPolicyId")
    private UUID defaultDataPolicyId;

    @JsonProperty("Id")
    private UUID id;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("SecretKey")
    private String secretKey;

    // Constructor
    public User(final String authId, final UUID defaultDataPolicyId, final UUID id, final String name, final String secretKey) {
        this.authId = authId;
        this.defaultDataPolicyId = defaultDataPolicyId;
        this.id = id;
        this.name = name;
        this.secretKey = secretKey;
    }

    // Getters and Setters
    
    public String getAuthId() {
        return this.authId;
    }

    public void setAuthId(final String authId) {
        this.authId = authId;
    }


    public UUID getDefaultDataPolicyId() {
        return this.defaultDataPolicyId;
    }

    public void setDefaultDataPolicyId(final UUID defaultDataPolicyId) {
        this.defaultDataPolicyId = defaultDataPolicyId;
    }


    public UUID getId() {
        return this.id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }


    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }


    public String getSecretKey() {
        return this.secretKey;
    }

    public void setSecretKey(final String secretKey) {
        this.secretKey = secretKey;
    }

}