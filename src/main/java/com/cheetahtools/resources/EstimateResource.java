package com.cheetahtools.resources;

import com.cheetahtools.JiraApiClient;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.charset.Charset;
import java.util.Map;


@Component("estimateResource")
@Path("estimates")
@Produces({MediaType.APPLICATION_JSON })
public class EstimateResource {

    @Autowired
    JiraApiClient client ;

    @GET
    @Path("/")
    public Response getEstimates(@QueryParam("types") String types) throws Exception {
        try {
            JSONObject resultJson = new JSONObject();
            Map<String, Integer> estimates = client.getEstimates(types);
            estimates.forEach((key,value) -> resultJson.put(key, value));
            return Response.status(Response.Status.OK)
                    .entity(resultJson.toJSONString())
                    .build();
        }
        catch(Exception ex) {
            return  Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": \"issue/issue type not found\"}").build();
        }
    }

}
