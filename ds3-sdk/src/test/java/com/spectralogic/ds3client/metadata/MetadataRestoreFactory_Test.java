/*
 * ****************************************************************************
 *    Copyright 2014-2016 Spectra Logic Corporation. All Rights Reserved.
 *    Licensed under the Apache License, Version 2.0 (the "License"). You may not use
 *    this file except in compliance with the License. A copy of the License is located at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *    or in the "license" file accompanying this file.
 *    This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 *    CONDITIONS OF ANY KIND, either express or implied. See the License for the
 *    specific language governing permissions and limitations under the License.
 *  ****************************************************************************
 */

package com.spectralogic.ds3client.metadata;

import com.spectralogic.ds3client.metadata.interfaces.MetadataRestore;
import com.spectralogic.ds3client.metadata.interfaces.MetadataRestoreListener;
import com.spectralogic.ds3client.networking.Metadata;
import com.spectralogic.ds3client.utils.Platform;
import org.junit.Assume;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MetadataRestoreFactory_Test {
    @Test
    public void testThatRunningOnMacReturnsMacMetaDataRestore() {
        Assume.assumeTrue(System.getProperty("os.name").startsWith("Mac"));

        final Metadata metadata = null;
        final String filePath = ".";
        final MetadataRestoreListener metadataRestoreListener = null;

        final MetadataRestore metadataRestorer = new MetadataRestoreFactory().getOSSpecificMetadataRestore(metadata, filePath, metadataRestoreListener);

        assertEquals(MACMetadataRestore.class, metadataRestorer.getClass());
    }

    @Test
    public void testThatRunningOnWindowsReturnsWindowsMetaDataRestore() {
        Assume.assumeTrue(Platform.isWindows());

        final Metadata metadata = null;
        final String filePath = ".";
        final MetadataRestoreListener metadataRestoreListener = null;

        final MetadataRestore metadataRestorer = new MetadataRestoreFactory().getOSSpecificMetadataRestore(metadata, filePath, metadataRestoreListener);

        assertEquals(WindowsMetadataStore.class, metadataRestorer.getClass());
    }
}
