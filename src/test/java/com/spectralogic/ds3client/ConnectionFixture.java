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

package com.spectralogic.ds3client;

import com.spectralogic.ds3client.networking.ConnectionDetails;
import com.spectralogic.ds3client.models.Credentials;

public class ConnectionFixture {

    public static ConnectionDetails getConnection() {
        return getConnection(8080);
    }

    public static ConnectionDetails getConnection(final int port) {
        return ConnectionDetailsImpl.builder("localhost:" + port, new Credentials("id", "key")).build();
    }
}
