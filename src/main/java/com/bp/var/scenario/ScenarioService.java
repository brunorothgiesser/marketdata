package com.bp.var.scenario;

import com.bp.var.model.PriceObservation;
import com.bp.var.model.Scenario;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

/**
 * Scenario service end points (REST)
 * @author rothb1
 */
@Path("/scenario")
@Api
public class ScenarioService {

    private MarketDataManager mdm;
    private static final DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");

    public ScenarioService() throws Exception {
        mdm = MarketDataManager.getInstance();
    }

    @GET
    @Path("/official")
    @Produces(MediaType.TEXT_PLAIN)
    @ApiOperation(value = "Latest price observations from official market data source")
    public String getOfficialScenario(@Context UriInfo ui) {
        String requestID = generateRequestID();
        logRequest(ui, requestID);
        Scenario s = new Scenario(mdm.getLastBusinessDayPrices());
        String responseString = ResponseFormatter.format(s);
        logResponse(ui, requestID, responseString);
        return responseString;
    }

    @GET
    @Path("/historic/{date}")
    @Produces(MediaType.TEXT_PLAIN)
    @ApiOperation(value = "Price observations from official market data source as of the specified date")
    public String getOfficialScenarioAsOf(@PathParam("date") String d, @Context UriInfo ui) {
        String requestID = generateRequestID();
        logRequest(ui, requestID);
        Date date;
        try {
            date = dateFormat.parse(d);
        } catch (ParseException e) {
            return "Invalid date. Expected date format is dd-MM-yy";
        }
        Set<PriceObservation> priceObservations = mdm.getPrices(date);

        if (priceObservations == null) {
            return "No prices found for " + d;
        }

        Scenario scenario = new Scenario(priceObservations);
        String responseString = ResponseFormatter.format(scenario);
        logResponse(ui, requestID, responseString);
        return responseString;
    }

    @GET
    @Path("/random")
    @Produces(MediaType.TEXT_PLAIN)
    @ApiOperation(value = "Random market scenario based on historic prices")
    public String getRandomScenario(@QueryParam("horizon") int numberOfDaysInTimeHorizon, @Context UriInfo ui) {
        String requestID = generateRequestID();
        logRequest(ui, requestID);

        ScenarioGenerator scenarioGenerator = new ScenarioGenerator();
        Scenario scenario;
        try {
            scenario = scenarioGenerator.generateScenario(numberOfDaysInTimeHorizon);
        } catch (Exception e) {
            return "failed to generate scenario";
        }

        String responseString = ResponseFormatter.format(scenario);
        logResponse(ui, requestID, responseString);
        return responseString;
    }

    private void logRequest(UriInfo uri, String requestID) {
        System.out.println("{\"request_id\" : \"" + requestID + "\", \"message_type\" : \"http_request\", \"uri\" : \"" + uri.getAbsolutePath() +"\"}");
    }

    private void logResponse(UriInfo uri, String requestID, String responseString) {
        // no longer logging the response message because json output wasn't looking right in Splunk
        //System.out.println("{\"request_id\" : \"" + requestID + "\", \"message_type\" : \"http_response\", \"uri\" : \"" + uri.getAbsolutePath() +"\", \"response_message\" : \""+responseString+"\"}");
        System.out.println("{\"request_id\" : \"" + requestID + "\", \"message_type\" : \"http_response\", \"uri\" : \"" + uri.getAbsolutePath() +"\"}");
    }

    private String generateRequestID() {
        return UUID.randomUUID().toString();
    }

}
