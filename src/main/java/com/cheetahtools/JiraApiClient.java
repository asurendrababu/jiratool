package com.cheetahtools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;
import java.util.stream.Collectors;

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
    Map<String, Integer> getEstimates(String issueTypes) throws InvalidDataException {

        List<String> issuesTypesInput = Arrays.asList(issueTypes.split(","));
        Map<String, Integer> result = issuesTypesInput.stream()
                                                           .map(this::getEstimate)
                                                           .collect(HashMap::new, Map::putAll, Map::putAll);
       System.out.println("Total estimate for [" + issueTypes + "] : ");
        result.forEach((key,value) -> System.out.println(key + " : " + value));
        return result;
    }

    Map<String, Integer> getEstimate(String type) throws InvalidDataException {
        Map<String, Integer> result = new HashMap<String, Integer>();
        int sum = jiraTool.getType(type).getIssues().stream()
                                       .map(jiraTool::getIssue)
                                       .mapToInt(issue -> issue.getEstimate())
                                       .sum();
        result.put(type, sum) ;
        return result;
    }

    public static void main(String[] args) {
        JiraApiClient client = new JiraApiClient();

        client.getEstimates("bugs,stories");
        client.getEstimates("bugs");
        client.getEstimates("stories");
        client.getEstimates("stories,bugs");
    }
}
