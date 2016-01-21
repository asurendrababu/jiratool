package com.cheetahtools.resources;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStreamReader;


@Component("infoResource")
@Path("info")
@Produces({MediaType.APPLICATION_JSON })
public class InfoResource {

    public static final String INFO_JSON = "/info.json";
    private JSONParser parser = new JSONParser();

    @GET
    @Path("/")
    public Response getInfo() throws Exception {
        return Response.status(Response.Status.OK)
                .entity(getBuildInfo())
                .build();
    }

    private String getBuildInfo() throws Exception {
        JSONObject responseJson = (JSONObject) parser.parse(new InputStreamReader(getClass().getResourceAsStream(INFO_JSON)));
        return responseJson.toString();
    }


}
