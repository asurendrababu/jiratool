package com.cheetahtools.resources;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStreamReader;
import java.nio.charset.Charset;


@Component("issueResource")
@Path("issues")
@Produces({MediaType.APPLICATION_JSON })
public class IssueResource {

    public String basePath = "/sampleResponses/";
    private JSONParser parser = new JSONParser();

    @GET
    @Path("/{issueId}")
    public Response getIssue(@PathParam("issueId") String issueId) throws Exception {
        try {
            String info = ((JSONObject) parser.parse(new InputStreamReader(getClass().getResourceAsStream(basePath + "issues/issue-" + issueId + ".json")))).toJSONString();
            return Response.status(Response.Status.OK)
                    .entity(info)
                    .build();
        }
        catch(Exception ex) {
            return  Response.status(Response.Status.NOT_FOUND).entity("{\"error\": \"issue not found\"}").build();
        }
    }

}
