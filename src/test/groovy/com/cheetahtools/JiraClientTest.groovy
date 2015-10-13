package com.cheetahtools

import com.cheetahtools.exceptions.InvalidDataException
import spock.lang.Specification


class JiraClientTest extends Specification {

    AbstractJiraApiTool mockJiraApiTool = Mock()
    IssueType mockBugsIssueType = Mock()
    IssueType mockStoriesIssueType = Mock()
    Issue mockIssue = Mock()
    JiraApiClient client = new JiraApiClient()
    
    def setup(){
        client.jiraTool = mockJiraApiTool
    }
    def  "getEstimates() should traverse complete path (all issueTypes and all issues)"() {
        given:
            int estimate = 3
            def mockBugsIssueTypeResult = ["1","2"]
            def mockStoriesIssueTypeResult = ["3","4","5"]
            1 * mockBugsIssueType.getIssues() >>  mockBugsIssueTypeResult
            1 * mockStoriesIssueType.getIssues() >>  mockStoriesIssueTypeResult
            1 * mockJiraApiTool.getType("bugs") >> mockBugsIssueType
            1 * mockJiraApiTool.getType("stories") >> mockStoriesIssueType
             5 * mockJiraApiTool.getIssue(_ as String) >> mockIssue
             5 * mockIssue.getEstimate() >> estimate

        when:
           Map<String, Integer> result = client.getEstimates("bugs,stories")

        then:
            result == ["bugs": (mockBugsIssueTypeResult.size() * estimate),
                             "stories": (mockStoriesIssueTypeResult.size() * estimate)]
    }

    def  "getEstimates() should throw exception, when invalid issueType is specified"() {
        given:
        1 * mockJiraApiTool.getType("invalidIssueType") >> { throw new InvalidDataException("Invalid issueType") }

        when:
        client.getEstimates("invalidIssueType")

        then:
       thrown(InvalidDataException.class)
    }

    def  "getEstimates() should throw exception, when invalid issue  is found"() {
        given:
        IssueType mockTypeWithInvalidIssue = Mock()
        def mockInvalidIssueResult = ["10","20"]
        1 * mockTypeWithInvalidIssue.getIssues() >>  mockInvalidIssueResult
        mockJiraApiTool.getType("typeWithInvalidIssue") >> mockTypeWithInvalidIssue
        mockJiraApiTool.getIssue(_ as String) >>  { throw new InvalidDataException("Invalid issueType")}

        when:
        client.getEstimates("typeWithInvalidIssue")

        then:
        thrown(InvalidDataException.class)
    }
}
