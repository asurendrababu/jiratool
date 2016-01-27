package com.cheetahtools;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Issue {
    String id;
    String issuetype;
    String description;
    int  estimate;

    public Issue(JSONObject jsonIssue) throws ParseException {
        id = (String) jsonIssue.get("id");
        issuetype = (String) jsonIssue.get("issuetype");
        description = (String) jsonIssue.get("description");
        estimate = Integer.parseInt ((String) jsonIssue.get("estimate"));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIssuetype() {
        return issuetype;
    }

    public void setIssuetype(String issuetype) {
        this.issuetype = issuetype;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEstimate() {
        return estimate;
    }

    public void setEstimate(int estimate) {
        this.estimate = estimate;
    }
}
