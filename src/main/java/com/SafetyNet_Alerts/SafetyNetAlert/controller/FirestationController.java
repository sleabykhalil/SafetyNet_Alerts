package com.SafetyNet_Alerts.SafetyNetAlert.controller;

import com.SafetyNet_Alerts.SafetyNetAlert.dto.HouseDto;
import com.SafetyNet_Alerts.SafetyNetAlert.dto.PeopleWithAgeCatDto;
import com.SafetyNet_Alerts.SafetyNetAlert.dto.PeopleWithSpecificAgeDto;
import com.SafetyNet_Alerts.SafetyNetAlert.model.Firestation;
import com.SafetyNet_Alerts.SafetyNetAlert.service.FirestationService;
import com.SafetyNet_Alerts.SafetyNetAlert.service.URLsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FirestationController {

    @Autowired
    FirestationService firestationService;

    @Autowired
    URLsService urLsService;

    /**
     * Get  for all Firestations
     *
     * @return list of firestation
     */
    @Operation(summary = "Get all firestation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found data in data source",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Firestation.class)))}),
            @ApiResponse(responseCode = "400", description = "Data not Found")})

    @GetMapping(value = "/firestations")
    public List<Firestation> getAllFirestations() {

        return firestationService.getAllFirestation();
    }

    /**
     * Add new firestation , json string passed in body
     *
     * @param firestation data to add
     * @return firestation after add
     */

    @Operation(summary = "Add new firestation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Firestation added successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Firestation.class))}),
            @ApiResponse(responseCode = "400", description = "Firestation already exist")})

    @PostMapping(value = "/firestation", produces = "application/json", consumes = "application/json")
    public Firestation addFirestation(@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Firestation to add",
            required = true,
            content = @Content(schema = @Schema(implementation = Firestation.class)))
                                              Firestation firestation) {
        return firestationService.saveFirestation(firestation);
    }

    /**
     * Update firestation ,new data passed throw body
     *
     * @param address     firestation adress to be updated
     * @param firestation new data
     * @return firestation after update
     */
    @Operation(summary = "update address correspond to firestation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "firestation updated successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Firestation.class))}),
            @ApiResponse(responseCode = "400", description = "Firestation assigned to this address already exist")})

    @PutMapping(value = "/firestation")
    public Firestation updateFirestation(@RequestParam @Parameter(description = "Address corresponding to firestation to update",
            required = true) String address,
                                         @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Firestation to add",
                                                 required = true,
                                                 content = @Content(schema = @Schema(implementation = Firestation.class)))
                                                 Firestation firestation) {

        return firestationService.updateFirestation(address, firestation);
    }

    /**
     * Delete firestation , address must pass in url
     *
     * @param address adress of firestation to be deleted
     * @return true if delete
     */
    @Operation(summary = "Delete address and correspond stationNumber to firestation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "firestation deleted successfully",
                    content = {@Content(mediaType = "true",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400", description = "Firestation not found")})

    @DeleteMapping(value = "/firestation")
    public boolean deletaFirestation(@RequestParam @Parameter(description = "Address corresponding to firestation to delete",
            required = true) String address) {
        return firestationService.deleteFirestation(address);
    }


    //URLs
    @Operation(summary = "Get list of people correspond to firestation stationNumber with age counter for children and adults")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Station number founded in data source, " +
                    "but empty list can be return if no person lived in corresponded address",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PeopleWithAgeCatDto.class))}),
            @ApiResponse(responseCode = "400", description = "station number not found in data source")})

    @GetMapping(value = "/firestation")
    public PeopleWithAgeCatDto getPersonWithAgeCatDto(@RequestParam @Parameter(description = "Firestation number used to search",
            required = true) String station_number) {
        return urLsService.getListOfPersonCoveredByFireStation(station_number);
    }

    @Operation(summary = "Get list of people with medical history for each one lives on address and the corresponding firestation number.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address founded in data source, " +
                    "but empty list can be return if no person lived in corresponded address",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PeopleWithSpecificAgeDto.class))}),
            @ApiResponse(responseCode = "400", description = "Address not found in data source")})

    @GetMapping(value = "/fire")
    public PeopleWithSpecificAgeDto getPeopleWithSpecificAgeDto(@RequestParam @Parameter(description = "Address used to search by it",
            required = true) String address) {
        return urLsService.getPeopleListServedByFirestationNumberByAddress(address);
    }

    @Operation(summary = "Get list of houses and for each house all people who lives in it with medical history for each one.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of houses with people lives in it with medical records, " +
                    "but empty list can be return if no person lived in corresponded address",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = HouseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Station number not found in data source")})

    @GetMapping(value = "/flood/stations")
    public HouseDto getHousesWithListOfPeopleWithSpecificAgeDto(@RequestParam @Parameter(description = "List of station number used to search by them",
            required = true) List<String> stations) {
        return urLsService.getHousesByStationNumber(stations);
    }
}
