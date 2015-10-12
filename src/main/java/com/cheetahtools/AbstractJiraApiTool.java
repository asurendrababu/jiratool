package com.cheetahtools;

import com.cheetahtools.exceptions.*;
import org.springframework.stereotype.Component;

public interface AbstractJiraApiTool {
    public IssueType getType(String subType) throws InvalidDataException;
    public Issue getIssue(String issueId) throws InvalidDataException;

}
