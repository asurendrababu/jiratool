package com.cheetahtools;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class IssueType {
    String id;
    String name;
    List<String> issues;

    public IssueType(JSONObject jsonIssueType) throws ParseException {
        id = (String) jsonIssueType.get("id");
        name  = (String) jsonIssueType.get("name");
        issues = (List<String>) ((JSONArray) jsonIssueType.get("issues"))
                                                .stream()
                                                .map(s -> ((String) s).replaceAll("/issues/", ""))
                                                .collect(Collectors.toList());
    }
    public List<String> getIssues() {
        return issues;
    }

    public void setIssues(List<String> issues) {
        this.issues = issues;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
