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

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.common.collect.Lists;
import com.spectralogic.ds3client.Ds3Client;
import com.spectralogic.ds3client.commands.*;
import com.spectralogic.ds3client.models.*;
import com.spectralogic.ds3client.serializer.XmlProcessingException;

class Ds3ClientHelpersImpl extends Ds3ClientHelpers {

    private static final int DEFAULT_MAX_KEYS = 1000;
    private static final String JOB_TYPE_PUT = "PUT";
    private static final String JOB_TYPE_GET = "GET";
    private final Ds3Client client;

    Ds3ClientHelpersImpl(final Ds3Client client) {
        this.client = client;
    }

    @Override
    public Ds3ClientHelpers.WriteJob startWriteJob(final String bucket, final Iterable<Ds3Object> objectsToWrite)
            throws SignatureException, IOException, XmlProcessingException {
        try(final BulkPutResponse prime = this.client.bulkPut(new BulkPutRequest(bucket, Lists.newArrayList(objectsToWrite)))) {
            final MasterObjectList result = prime.getResult();
            return new WriteJobImpl(new Ds3ClientFactoryImpl(this.client), result.getJobId(), bucket, result.getObjects());
        }
    }

    @Override
    public Ds3ClientHelpers.ReadJob startReadJob(final String bucket, final Iterable<Ds3Object> objectsToRead)
            throws SignatureException, IOException, XmlProcessingException {
        try(final BulkGetResponse prime = this.client.bulkGet(new BulkGetRequest(bucket, Lists.newArrayList(objectsToRead)))) {
            final MasterObjectList result = prime.getResult();
            return new ReadJobImpl(new Ds3ClientFactoryImpl(this.client), result.getJobId(), bucket, result.getObjects());
        }
    }

    @Override
    public Ds3ClientHelpers.ReadJob startReadAllJob(final String bucket)
            throws SignatureException, IOException, XmlProcessingException {
        final Iterable<Contents> contentsList = this.listObjects(bucket);

        final List<Ds3Object> ds3Objects = new ArrayList<>();
        for (final Contents contents : contentsList) {
            ds3Objects.add(new Ds3Object(contents.getKey()));
        }

        return this.startReadJob(bucket, ds3Objects);
    }

    @Override
    public WriteJob recoverWriteJob(final UUID jobId) throws SignatureException, IOException, XmlProcessingException, JobRecoveryException {
        try (final GetJobResponse job = this.client.getJob(new GetJobRequest(jobId))) {
            final JobInfo jobInfo = job.getJobInfo();
            checkJobType(JOB_TYPE_PUT, jobInfo.getRequestType());
            return new WriteJobImpl(
                new Ds3ClientFactoryImpl(this.client),
                jobInfo.getJobId(),
                jobInfo.getBucketName(),
                job.getObjectsList()
            );
        }
    }

    @Override
    public ReadJob recoverReadJob(final UUID jobId) throws SignatureException, IOException, XmlProcessingException, JobRecoveryException {
        try (final GetJobResponse job = this.client.getJob(new GetJobRequest(jobId))) {
            final JobInfo jobInfo = job.getJobInfo();
            checkJobType(JOB_TYPE_GET, jobInfo.getRequestType());
            return new ReadJobImpl(
                new Ds3ClientFactoryImpl(this.client),
                jobInfo.getJobId(),
                jobInfo.getBucketName(),
                convertFromJobObjectsList(job.getObjectsList())
            );
        }
    }

    private static List<Objects> convertFromJobObjectsList(final List<JobObjects> jobObjectsList) {
        final List<Objects> objectsList = new ArrayList<>(jobObjectsList.size());
        for (final JobObjects jobObjects : jobObjectsList) {
            objectsList.add(convertFromJobObjects(jobObjects));
        }
        return objectsList;
    }

    private static Objects convertFromJobObjects(final JobObjects jobObjects) {
        final Objects objects = new Objects();
        objects.setServerId(jobObjects.getServerId());
        objects.setObject(concat(jobObjects.getObjectsInCache(), jobObjects.getObject()));
        return objects;
    }
    
    @SafeVarargs
    private static <T> List<T> concat(final List<T>... lists) {
        final List<T> result = new ArrayList<>();
        for (final List<T> list : lists) {
            result.addAll(list);
        }
        return result;
    }

    private static void checkJobType(final String expectedJobType, final String actualJobType) throws JobRecoveryException {
        if (!actualJobType.equals(expectedJobType)) {
            throw new JobRecoveryException(expectedJobType, actualJobType);
        }
    }

    @Override
    public void ensureBucketExists(final String bucket) throws IOException, SignatureException {
        try (final HeadBucketResponse response = this.client.headBucket(new HeadBucketRequest(bucket))) {
            if (response.getStatus() == HeadBucketResponse.Status.DOESNTEXIST) {
                this.client.putBucket(new PutBucketRequest(bucket)).close();
            }
        }
    }

    @Override
    public Iterable<Contents> listObjects(final String bucket) throws SignatureException, IOException {
        return this.listObjects(bucket, null);
    }

    @Override
    public Iterable<Contents> listObjects(final String bucket, final String keyPrefix) throws SignatureException, IOException {
        return this.listObjects(bucket, keyPrefix, Integer.MAX_VALUE);
    }

    @Override
    public Iterable<Contents> listObjects(final String bucket, final String keyPrefix, final int maxKeys) throws SignatureException, IOException {
        final List<Contents> contentList = new ArrayList<>();

        int remainingKeys = maxKeys;
        boolean isTruncated = false;
        String marker = null;

        do {
            final GetBucketRequest request = new GetBucketRequest(bucket);
            request.withMaxKeys(Math.min(remainingKeys, DEFAULT_MAX_KEYS));
            if (keyPrefix != null) {
                request.withPrefix(keyPrefix);
            }
            if (isTruncated) {
                request.withNextMarker(marker);
            }

            try (final GetBucketResponse response = this.client.getBucket(request)) {
                final ListBucketResult result = response.getResult();

                isTruncated = result.isTruncated();
                marker = result.getNextMarker();
                remainingKeys -= result.getContentsList().size();

                for (final Contents contents : result.getContentsList()) {
                    contentList.add(contents);
                }
            }
        } while (isTruncated && remainingKeys > 0);

        return contentList;
    }

    @Override
    public Iterable<Ds3Object> listObjectsForDirectory(final Path directory) throws IOException {
        final List<Ds3Object> objects = new ArrayList<>();
        Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
                objects.add(new Ds3Object(directory.relativize(file).toString(), Files.size(file)));
                return FileVisitResult.CONTINUE;
            }
        });
        return objects;
    }
}