package me.vkku.aws.ssm.runningjobs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementAsyncClient;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementAsyncClientBuilder;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClient;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import com.amazonaws.services.simplesystemsmanagement.model.GetCommandInvocationRequest;
import com.amazonaws.services.simplesystemsmanagement.model.GetCommandInvocationResult;
import com.amazonaws.services.simplesystemsmanagement.model.SendCommandRequest;
import com.amazonaws.services.simplesystemsmanagement.model.SendCommandResult;
import com.amazonaws.services.simplesystemsmanagement.model.Target;

import me.vkku.aws.ssm.runningjobs.constants.CommandConstants;

public class FindRunningJobs {

	 public static void main(String[] args) {
		 
		 	SendCommandResult sendCommandResult = null;

	        Region usWest2 = Region.getRegion(Regions.US_EAST_1);
	        AWSSimpleSystemsManagementClient systemsManagement = (AWSSimpleSystemsManagementClient) AWSSimpleSystemsManagementClientBuilder.defaultClient();
	        SendCommandRequest commandRequest = new SendCommandRequest();
	        commandRequest.setDocumentName(CommandConstants.DOCUMENT_NAME);
	        commandRequest.setTargets(Arrays.asList(new Target()
	        		.withKey(CommandConstants.MASTER_KEY)
	        		.withValues(CommandConstants.MASTER_VALUE)));
	        		
	        //commandRequest.setInstanceIds(Arrays.asList(CommandConstants.MASTER_INSTANCE_ID));
	        
	        //Execute Command on instance
	        sendCommandResult = systemsManagement.sendCommand(commandRequest);
	        
	        //Extract meta-info
	        String commandId = sendCommandResult.getCommand().getCommandId();
	        String instanceId = sendCommandResult.getCommand().getInstanceIds().get(0);
	        
	        //Get Output
	        GetCommandInvocationResult commandInvocationResult =  systemsManagement
	        		.getCommandInvocation(new GetCommandInvocationRequest()
	        		.withCommandId(commandId)
	        		.withInstanceId(instanceId));
	        
	        //Parse Output
	        String output = commandInvocationResult.getStandardOutputContent();
	        System.out.println(output);
	 }
}