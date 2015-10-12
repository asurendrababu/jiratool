package com.cheetahtools

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
    def  "make sure complete path is traversed"() {
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
}
