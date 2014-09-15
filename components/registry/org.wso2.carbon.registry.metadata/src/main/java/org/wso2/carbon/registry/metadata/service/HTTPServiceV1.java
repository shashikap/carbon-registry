/*
 *  Copyright (c) 2005-2009, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */
package org.wso2.carbon.registry.metadata.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.registry.core.Registry;
import org.wso2.carbon.registry.metadata.Base;
import org.wso2.carbon.registry.metadata.Base1;
import org.wso2.carbon.registry.metadata.Base1;
import org.wso2.carbon.registry.metadata.Constants;
import org.wso2.carbon.registry.metadata.Util;
import org.wso2.carbon.registry.metadata.exception.MetadataException;
import org.wso2.carbon.registry.metadata.version.ServiceVersionV1;
import org.wso2.carbon.registry.metadata.version.ServiceVersionV1;
import org.wso2.carbon.registry.metadata.version.VersionBase;
import org.wso2.carbon.registry.metadata.version.VersionV1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HTTPServiceV1 extends Base {

//  Type specific attributes goes here
    public static final String OWNER = "owner";

//  Variables defined for the internal implementation
    private static final Log log = LogFactory.getLog(HTTPServiceV1.class);
    private static String mediaType = "vnd.wso2.service/http+xml;version=1";
    private static String versionMediaType = "vnd.wso2.version/service.http+xml;version=1";
    private ServiceVersionV1 baseVersion = null;

    private static String ROOT_STORAGE_PATH = new StringBuilder(Constants.BASE_STORAGE_PATH)
            .append(mediaType.split(";")[0].replaceAll("\\+", ".").replaceAll("\\.", "/"))
            .append("/v")
            .append(mediaType.split(";")[1].split("=")[1]).toString();


    public HTTPServiceV1(Registry registry, String name, VersionBase version) throws MetadataException {
        super(mediaType,versionMediaType,name, false, registry);
        version.setBaseUUID(uuid);
        version.setBaseName(name);
        baseVersion = (ServiceVersionV1) version;
    }

    public HTTPServiceV1(Registry registry, String name, String uuid, Map<String, List<String>> propertyBag, Map<String, List<String>> attributeMap) throws MetadataException {
        super(mediaType,versionMediaType,name, uuid, false, propertyBag,attributeMap, registry);
    }

    public ServiceVersionV1 newVersion(String key) throws MetadataException {
        ServiceVersionV1 v = new ServiceVersionV1(registry, key);
        v.setBaseUUID(uuid);
        v.setBaseName(name);
        return v;
    }

    public void setOwner(String owner) {
        List<String> value = new ArrayList<String>();
        value.add(owner);
        attributeMap.put(OWNER, value);
    }

    public String getOwner() {
        List<String> value = attributeMap.get(OWNER);
        return value != null ? value.get(0) : null;
    }

    public static void add(Registry registry, Base metadata) throws MetadataException {
        if (((HTTPServiceV1) metadata).baseVersion == null) {
            add(registry, metadata,
                    generateMetadataStoragePath(metadata.getName(), ROOT_STORAGE_PATH));
        } else {
            add(registry, metadata,
                    generateMetadataStoragePath(metadata.getName(), ROOT_STORAGE_PATH));
            ServiceVersionV1.add(registry, ((HTTPServiceV1) metadata).baseVersion);
        }
    }

    public static void update(Registry registry, Base metadata) throws MetadataException {
        update(registry, metadata,
                generateMetadataStoragePath(metadata.getName(), ROOT_STORAGE_PATH));
    }

    /**
     * @return all meta data instances and their children that denotes from this particular media type
     */
    public static HTTPServiceV1[] getAll(Registry registry) throws MetadataException {
        List<Base> list = getAll(registry, mediaType);
        return list.toArray(new HTTPServiceV1[list.size()]);
    }

    /**
     * Search all meta data instances of this particular type with the given search attributes
     *
     * @param criteria Key value map that has search attributes
     * @return
     */
    public static HTTPServiceV1[] find(Registry registry, Map<String, String> criteria) throws MetadataException {
        List<Base> list = find(registry, criteria, mediaType);
        return list.toArray(new HTTPServiceV1[list.size()]);
    }

    /**
     * Returns the meta data instance that can be identified by the given UUID
     *
     * @param uuid - UUID of the metadata insatnce
     * @return meta data from the UUID
     */
    public static HTTPServiceV1 get(Registry registry, String uuid) throws MetadataException {
        return (HTTPServiceV1) get(registry, uuid, mediaType);
    }

    private static String generateMetadataStoragePath(String name, String rootStoragePath) {
        return rootStoragePath + "/" + name;
    }

}
