package com.cheetahtools;

import com.cheetahtools.exceptions.InvalidDataException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;

@Component(value = "webJiraApiTool")
public class WebJiraApiTool implements AbstractJiraApiTool {
    public String basePath = "http://localhost:8010/";
    private JSONParser parser = new JSONParser();
    private Client webClient = Client.create();

    public IssueType getType(String subType) throws InvalidDataException {
        try {
            String output = getData(basePath + "issuetypes/" + subType);
            IssueType issueType = new IssueType((JSONObject) parser.parse(output));
            return issueType;
        }
        catch (Exception ex) {
            System.out.println("Exception with getIssueType() " + subType + ", Message: "  + ex.toString() + "," + ex.getCause() + ", " + ex.getMessage());
            throw new InvalidDataException(" Invalid issue type: " + subType);
        }
    }
    public Issue getIssue(String issueId)  throws InvalidDataException {
        try {
            String output = getData(basePath + "issues/" + issueId );
            Issue issue = new Issue((JSONObject) parser.parse(output));
            return issue;
        }
        catch (Exception ex) {
            System.out.println("Exception with getIssue() " + issueId + ", Message: " + ex.getMessage());
            throw new InvalidDataException(" Invalid issue: " + issueId);
        }
    }
    private String getData(String path)throws InvalidDataException {
            WebResource webResource = webClient.resource(path);
            ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
            if (response.getStatus() != 200) {
                throw new InvalidDataException(" Failed to read from URL " + path + ", Failed with response code:  " + response.getStatus()) ;
            }
            return response.getEntity(String.class);
    }
}
