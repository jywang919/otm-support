package com.ori.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


//@RestController
//@CrossOrigin(origins = "http://localhost:4200") //not getting here
@RequestMapping(value = SineWaveController.URL)
public class SineWaveController {
    private static final Logger LOG =
            LoggerFactory.getLogger(SineWaveController.class);
    public static final String URL = "/r/sinewave";

    @Autowired
    private ISineWaveService sinceWaveService;

    /**
     * Handles REST requests of the form:
     * ../rest/v1/roads/{countyCode}/{roadType} where countyCode is an 3 numeric
     * chars: '000' or '910' and roadType is an up to 2 chars code
     *
     * @return ResponseEntity - a response wrapper object that contains: 1)
     *         response status 2) list of road resources with links to access
     *         each resource data
     *
     * @throws Exception
     */
//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @RequestMapping(method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseEntity<WrappedResponseResource> getCaseDetailsByCaseKey(
//    // @RequestParam("county_name") final String county,
//    // @RequestParam("file_number") final String fileNumber
//    ) throws Exception {
//
//        final WrappedResponseResource wrappedResponse =
//                new WrappedResponseResource();
//        HttpStatus httpStatus = HttpStatus.OK;
//
//        SineWaveResultsData caseDetails = null;
//
//        try {
//            caseDetails = sinceWaveService.calculate();
//        } catch (final Exception e) {
//            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
//            throw new CiprsRestRuntimeException(
//                    "sinceWaveService failed to calculate results");
//        }
//
//        final SineWaveResultsResourceAssembler assembler =
//                new SineWaveResultsResourceAssembler();
//
//        final SineWaveResultsResource resource = assembler.toResource(caseDetails);
//
//        wrappedResponse.setHttpStatus(httpStatus);
//        wrappedResponse.setData(resource);
//        addResourceLinks(wrappedResponse);
//
//        wrappedResponse.add(new Link(SineWaveController.URL)
//                .withRel(LinkRelation.UP.name()));
//        if (SineWaveController.LOG.isDebugEnabled()) {
//            SineWaveController.LOG.debug("wrappedResponse=" + wrappedResponse);
//        }
//        final ResponseEntity<WrappedResponseResource> response =
//                new ResponseEntity<>(wrappedResponse,
//                        wrappedResponse.getHttpStatus());
//        return response;
//    }


    /**
     * Handles REST requests of the form:
     * ../rest/v1/roads/{countyCode}/{roadType} where countyCode is an 3 numeric
     * chars: '000' or '910' and roadType is an up to 2 chars code
     *
     * @return ResponseEntity - a response wrapper object that contains: 1)
     *         response status 2) list of road resources with links to access
     *         each resource data
     *
     * @throws Exception
     */
    //@JsonInclude(JsonInclude.Include.NON_NULL)
    @RequestMapping(method = RequestMethod.GET)    
    public @ResponseBody SineWaveResultsData getSineWave(
     @RequestParam("amp") final String amplitude,
     @RequestParam("freq") final String frequency,
     @RequestParam("off") final String offset,
     @RequestParam("cnt") final String cnt
    ) throws Exception {

        @SuppressWarnings("unused")
		HttpStatus httpStatus = HttpStatus.OK;
        SineWaveData data = null;
        SineWaveResultsData sineWaveResult = null;
        try {
        	SineWaveController.LOG.debug(SineWaveController.URL+"?amp="+amplitude+"&freq="+frequency+"&off="+offset+"&cnt="+cnt); 
        	data = new SineWaveData(Float.parseFloat(amplitude),Float.parseFloat(frequency),Double.parseDouble(offset),Integer.parseInt(cnt));
//        	if (SineWaveController.LOG.isDebugEnabled()) {
            	SineWaveController.LOG.debug("SineWaveData=" + data);               
//            }
            sineWaveResult = sinceWaveService.calculate(data);
//            if (SineWaveController.LOG.isDebugEnabled()) {
            	SineWaveController.LOG.debug("sineWaveResult=" + sineWaveResult);               
//            }
        } catch (final Exception e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            throw new Exception(
                    "sinceWaveService failed to calculate results");
        }
        
        if (SineWaveController.LOG.isDebugEnabled()) {
            SineWaveController.LOG.debug("sineWaveResult=" + sineWaveResult.toString());
        }
//        final ResponseEntity<SineWaveResultsData> response =
//                new ResponseEntity<SineWaveResultsData>();
//        response.add(sineWaveResult);
//        return response;
        return sineWaveResult;
    }
    
}