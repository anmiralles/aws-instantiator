package es.anmiralles.instantiator.boundary;

import es.anmiralles.instantiator.control.AwsApiService;
import es.anmiralles.instantiator.control.InstanceService;
import es.anmiralles.instantiator.entity.Instance;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesResponse;
import software.amazon.awssdk.services.ec2.model.RunInstancesResponse;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotEmpty;

@Named
@RequestScoped
public class InstanceCreationBean {

    @NotEmpty
    private String awsUser;

    @NotEmpty
    private String awsSecret;

    @NotEmpty
    private String awsName;

    @Inject
    private InstanceService instanceService;

    @Inject
    private AwsApiService awsApiService;

    /**
     * View Controller for creating an AWS instance
     * @return navigation
     */
    public String createInstance() {

        String instanceId;
        String instanceDns = "";
        String instanceStatus = "";
        String instanceLink;
        software.amazon.awssdk.services.ec2.model.Instance awsInstance = null;
        FacesContext context = FacesContext.getCurrentInstance();
        DescribeInstancesResponse describeInstancesResponse;

        try {
            // Export env variables for managing credentials
            System.setProperty("aws.accessKeyId", awsUser);
            System.setProperty("aws.secretAccessKey", awsSecret);
            System.setProperty("AWS_PROFILE", "default");
            System.setProperty("aws.region", "eu-west-1");

            // Run aws instance
            RunInstancesResponse response = awsApiService.createAwsInstance();

            // Checking aws instance status and public dns
            awsInstance = response.instances().get(0);
            instanceId = awsInstance.instanceId();

            while(instanceDns.equals("") && !instanceStatus.equals("running")) {
                describeInstancesResponse = awsApiService.describeAwsInstance(instanceId);
                instanceDns = describeInstancesResponse.reservations().get(0).instances().get(0).publicDnsName();
                instanceStatus = describeInstancesResponse.reservations().get(0).instances().get(0).state().nameAsString();

                System.out.println(instanceDns + " " + instanceStatus);
            }

            // Tag aws instance
            awsApiService.createTag(awsInstance, awsName);

            // Add instance to instances list
            instanceLink = "https://" + instanceDns;
            Instance instance = new Instance(awsInstance.instanceId(), awsUser, awsSecret, awsName, instanceLink);
            instanceService.addInstance(instance);

            // Message creation
            context.addMessage(null, new FacesMessage("Process successful", "Instance up and running:  " + instance.getInstanceId()));
            context.getExternalContext().getFlash().setKeepMessages(true);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return "/instances.xhtml?faces-redirect=true";
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

    public String getAwsName() {
        return awsName;
    }

    public void setAwsName(String awsName) {
        this.awsName = awsName;
    }
}
