package com.SafetyNet_Alerts.SafetyNetAlert.controller;

import com.SafetyNet_Alerts.SafetyNetAlert.model.MedicalRecord;
import com.SafetyNet_Alerts.SafetyNetAlert.service.MedicalRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class MedicalRecordController {

    @Autowired
    MedicalRecordService medicalRecordService;

    /**
     * Get  for all Medical records
     *
     * @return list of medical records
     */

    @Operation(summary = "Get all medicalRecords")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found data in data source",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = MedicalRecord.class)))}),
            @ApiResponse(responseCode = "400", description = "Data not Found")})

    @GetMapping(value = "/medicalRecords")
    public List<MedicalRecord> getAllMedialRecords() {
        log.debug("Get all medical records data");
        return medicalRecordService.getAllMedicalRecords();
    }

    /**
     * Add new MedicalRecord json string passed in body
     *
     * @param medicalRecord to add
     * @return medicalRecord after added
     */

    @Operation(summary = "Add new medical record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medical recorded added successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MedicalRecord.class))}),
            @ApiResponse(responseCode = "400", description = "Medical record already exist")})

    @PostMapping(value = "/medicalRecord")
    public MedicalRecord addMedicalRecord(@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Medical record to add",
            required = true,
            content = @Content(schema = @Schema(implementation = MedicalRecord.class)))
                                                  MedicalRecord medicalRecord) {
        log.debug("Add/save new medical record {}", medicalRecord.toString());
        return medicalRecordService.saveMedicalRecord(medicalRecord);
    }

    /**
     * Update medicalRecord new medicalRecord passed in body
     *
     * @param firstName     passed in url
     * @param lastName      passed in url
     * @param medicalRecord new information passed in body
     * @return medicalRecord after update
     */
    @Operation(summary = "update medical record correspond to first name and last name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "medical record updated successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MedicalRecord.class))}),
            @ApiResponse(responseCode = "400", description = "Medical record assigned to this first name and last name already exist")})

    @PutMapping(value = "/medicalRecord")
    public MedicalRecord updateMedicalRecord(@RequestParam @Parameter(description = "First name to update",
            required = true) String firstName,
                                             @RequestParam @Parameter(description = "Last name to update",
                                                     required = true) String lastName,
                                             @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Medical record to add",
                                                     required = true,
                                                     content = @Content(schema = @Schema(implementation = MedicalRecord.class)))
                                                     MedicalRecord medicalRecord) {
        log.debug("update medical record for {}=[first name] {}=[last name] {}=[new data]", firstName, lastName, medicalRecord.toString());
        return medicalRecordService.updateMedicalRecord(firstName, lastName, medicalRecord);
    }

    /**
     * Delete medicalRecord , name must pass in url
     *
     * @param firstName passed in url
     * @param lastName  passed in url
     * @return true if medicalRecord deleted
     */
    @Operation(summary = "Delete medical report from data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medical report deleted successfully",
                    content = {@Content(mediaType = "true",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400", description = "Medical report not found")})


    @DeleteMapping(value = "/medicalRecord")
    public boolean deletePerson(@RequestParam @Parameter(description = "First name of person to delete his medical record",
            required = true) String firstName,
                                @RequestParam @Parameter(description = "Last name of person to delete his medical record",
                                        required = true) String lastName) {

        log.debug("Delete medical record for {}=[first name] {}=[last name]", firstName, lastName);
        return medicalRecordService.deleteMedicalRecord(firstName, lastName);
    }

}
