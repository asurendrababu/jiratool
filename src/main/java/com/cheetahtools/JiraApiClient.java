package com.cheetahtools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Arrays;
import java.util.List;

import  com.cheetahtools.exceptions.*;

public class JiraApiClient {

    @Autowired
    @Qualifier("FileJiraApiTool")
   private AbstractJiraApiTool  jiraTool;

    public JiraApiClient() {
        if (jiraTool == null) {
            jiraTool = new FileJiraApiTool();
        }
    }
    int getEstimates(String issueTypes) throws InvalidDataException {

        List<String> issuesTypesInput = Arrays.asList(issueTypes.split(","));
        int sum = issuesTypesInput.stream()
                                               .map(jiraTool::getType)
                                               .flatMap(issueType -> issueType.getIssues().stream())
                                               .map(jiraTool::getIssue)
                                               .mapToInt(issue -> issue.getEstimate())
                                               .sum();
        System.out.println("Total estimate for [" + issueTypes + "] : " + sum);
        return sum;
    }

    public static void main(String[] args) {
        JiraApiClient client = new JiraApiClient();

        client.getEstimates("bugs,stories");
        client.getEstimates("bugs");
        client.getEstimates("stories");
        client.getEstimates("stories,bugs");
    }
}
