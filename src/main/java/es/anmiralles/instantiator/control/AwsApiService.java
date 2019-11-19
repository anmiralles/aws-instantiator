package es.anmiralles.instantiator.control;

import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.*;

import javax.ejb.Stateless;

@Stateless
public class AwsApiService {

    /**
     * Method for describing an aws instance
     * @param instanceId
     * @return instance data
     */
    public DescribeInstancesResponse describeAwsInstance(String instanceId) {
        Ec2Client ec2 = Ec2Client.create();
        DescribeInstancesResponse instancesResponse = null;

        try{
            DescribeInstancesRequest describeInstancesRequest = DescribeInstancesRequest.builder().instanceIds(instanceId).build();

            instancesResponse = ec2.describeInstances(describeInstancesRequest);
        } catch (Ec2Exception e) {
            e.printStackTrace();
        } finally {
            ec2.close();
        }
        return instancesResponse;
    }

    /**
     * Method for creating an aws instance
     * @return running instance
     */
    public RunInstancesResponse createAwsInstance() {
        Ec2Client ec2 = Ec2Client.create();
        RunInstancesResponse instancesResponse = null;

        try {
            RunInstancesRequest run_request = RunInstancesRequest.builder()
                    .imageId("ami-0ec852340933f4f48")
                    .instanceType(InstanceType.T2_MICRO)
                    .maxCount(1)
                    .minCount(1)
                    .build();

            instancesResponse = ec2.runInstances(run_request);
        } catch (Ec2Exception e) {
            e.printStackTrace();
        } finally {
            ec2.close();
        }
        return instancesResponse;
    }

    /**
     * Method for tagging an aws instance request
     * @param awsInstance
     * @param awsName
     */
    public void createTag(software.amazon.awssdk.services.ec2.model.Instance awsInstance, String awsName) {
        Ec2Client ec2 = Ec2Client.create();

        try {
            Tag tag = Tag.builder()
                    .key("Name")
                    .value(awsName)
                    .build();

            CreateTagsRequest tag_request = CreateTagsRequest.builder()
                    .resources(awsInstance.instanceId())
                    .tags(tag)
                    .build();

            ec2.createTags(tag_request);

        } catch (Ec2Exception e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            ec2.close();
        }
    }

    /**
     * Method for deleting an aws instance
     * @param instanceId
     */
    public void deleteAwsInstance(String instanceId) {
        Ec2Client ec2 = Ec2Client.create();

        try {
            StopInstancesRequest request = StopInstancesRequest.builder()
                    .instanceIds(instanceId).build();

            ec2.stopInstances(request);

        } catch (Ec2Exception e) {
            e.printStackTrace();
        } finally {
            ec2.close();
        }
    }
}
