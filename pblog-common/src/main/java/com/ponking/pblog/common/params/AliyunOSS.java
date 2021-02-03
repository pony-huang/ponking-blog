package com.ponking.pblog.common.params;

/**
 * @author peng
 * @date 2020/10/18--14:47
 * @Des
 **/
public class AliyunOSS {

    private String endpoint;

    private String bucket;

    private String accessKeyId;

    private String accessKeySecret;

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return this.endpoint;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getBucket() {
        return this.bucket;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeyId() {
        return this.accessKeyId;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getAccessKeySecret() {
        return this.accessKeySecret;
    }
}
