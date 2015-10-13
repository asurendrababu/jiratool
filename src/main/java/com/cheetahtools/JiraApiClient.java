package com.cheetahtools;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;
import java.util.stream.Collectors;

import  com.cheetahtools.exceptions.*;
import org.springframework.stereotype.Component;

@Component
public class JiraApiClient {

   private AbstractJiraApiTool  jiraTool;

    public JiraApiClient() {
            jiraTool = new WebJiraApiTool();
    }
    public Map<String, Integer> getEstimates(String issueTypes) throws InvalidDataException {
        List<String> issuesTypesInput = Arrays.asList(issueTypes.split(","));
        Map<String, Integer> result = issuesTypesInput.stream()
                                                           .map(this::getEstimate)
                                                           .collect(HashMap::new, Map::putAll, Map::putAll);
        return result;
    }

    private Map<String, Integer> getEstimate(String type) throws InvalidDataException {
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
        String input = "bugs,stories";
        Map<String, Integer> result = client.getEstimates(input);
        System.out.println("Total estimate for [" + input + "] : ");
        result.forEach((key,value) -> System.out.println(key + " : " + value));
        System.out.println("");

/*      WebJiraApiTool webClient = new WebJiraApiTool();
        List<String> types = Arrays.asList("bugs,stories".split(","));
        types.forEach(type -> {
        int sum =  webClient.getType(type).getIssues().stream()
                .map(webClient::getIssue)
                .mapToInt(i -> i.getEstimate())
                .sum()   ;

        System.out.println("total estimate : " + sum);
        });*/
    }
}
