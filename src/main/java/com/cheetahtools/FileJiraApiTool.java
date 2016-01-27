package com.cheetahtools;

import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.cheetahtools.exceptions.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;

@Component(value = "fileJiraApiTool")
public class FileJiraApiTool implements AbstractJiraApiTool {
    public String basePath = "/sampleResponses/";
    private JSONParser parser = new JSONParser();

    public IssueType getType(String subType) throws InvalidDataException {
        try {
           IssueType issueType = new IssueType((JSONObject) parser.parse(new InputStreamReader(getClass().getResourceAsStream(basePath + "issuetypes/" + subType + ".json"))));
            return issueType;
        }
        catch (Exception ex) {
            throw new InvalidDataException(" Invalid issue type: " + subType);
        }
    }
    public Issue getIssue(String issueId)  throws InvalidDataException {
        try {
            Issue issue = new Issue((JSONObject) parser.parse(new InputStreamReader(getClass().getResourceAsStream(basePath + "issues/issue-" + issueId + ".json"))));
            return issue;
        }
        catch (Exception ex) {
            throw new InvalidDataException(" Invalid issue: " + issueId);
        }
    }

}
