package es.anmiralles.instantiator.entity;

import java.util.UUID;

public class Instance {

    private String instanceId;
    private String awsUser;
    private String awsSecret;
    private String awsName;
    private String awsUrl;

    public Instance() {

    }

    public Instance(String instanceId, String awsUser, String awsSecret, String awsName, String awsUrl) {
        super();
        this.instanceId = instanceId;
        this.awsUser = awsUser;
        this.awsSecret = awsSecret;
        this.awsName = awsName;
        this.awsUrl = awsUrl;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getAwsUser() {
        return awsUser;
    }

    public void setAwsUser(String awsUser) {
        this.awsUser = awsUser;
    }

    public String getAwsSecret() {
        return awsSecret;
    }

    public void setAwsSecret(String awsSecret) {
        this.awsSecret = awsSecret;
    }

    public String getAwsUrl() {
        return awsUrl;
    }

    public void setAwsUrl(String awsUrl) {
        this.awsUrl = awsUrl;
    }

    public String getAwsName() {
        return awsName;
    }

    public void setAwsName(String awsName) {
        this.awsName = awsName;
    }
}
