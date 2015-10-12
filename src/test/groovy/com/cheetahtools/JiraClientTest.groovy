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
            1 * mockBugsIssueType.getIssues() >> ["1","2"]
            1 * mockStoriesIssueType.getIssues() >> ["3","4","5"]
            1 * mockJiraApiTool.getType("bugs") >> mockBugsIssueType
            1 * mockJiraApiTool.getType("stories") >> mockStoriesIssueType
             5 * mockJiraApiTool.getIssue(_ as String) >> mockIssue
             5 * mockIssue.getEstimate() >> estimate

        when:
           int sum = client.getEstimates("bugs,stories")

        then:
            sum == 5 * estimate
    }
}
