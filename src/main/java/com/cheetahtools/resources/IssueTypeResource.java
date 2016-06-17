package com.cheetahtools.resources;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStreamReader;
import java.nio.charset.Charset;


@Component("issueTypesResource")
@Path("issuetypes")
@Produces({MediaType.APPLICATION_JSON })
public class IssueTypeResource {

    public String basePath = "/sampleResponses/";
    private JSONParser parser = new JSONParser();

    @Autowired
    IssueResource issueResource;

    @GET
    @Path("/{issueType}")
    public Response getIssueType(@PathParam("issueType") String issueType) throws Exception {
        try {
           String info = ((JSONObject) parser.parse(new InputStreamReader(getClass().getResourceAsStream(basePath + "issuetypes/" + issueType + ".json")))).toJSONString();
            return Response.status(Response.Status.OK)
                    .entity(info)
                    .build();
        }
        catch(Exception ex) {
            return  Response.status(Response.Status.NOT_FOUND).entity("{\"error\": \"issue type not found\"}").build();
        }
    }


    @Path("/{issueType}/issues")
    public IssueResource getIsssueTypesIssuesResource() throws Exception {
        return issueResource;
    }

}
