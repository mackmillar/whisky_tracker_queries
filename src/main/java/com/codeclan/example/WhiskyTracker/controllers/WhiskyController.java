package com.codeclan.example.WhiskyTracker.controllers;

import com.codeclan.example.WhiskyTracker.models.Distillery;
import com.codeclan.example.WhiskyTracker.models.Whisky;
import com.codeclan.example.WhiskyTracker.repositories.DistilleryRepository;
import com.codeclan.example.WhiskyTracker.repositories.WhiskyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class WhiskyController {

    @Autowired
    WhiskyRepository whiskyRepository;

    @Autowired
    DistilleryRepository distilleryRepository;

    @GetMapping(value = "/whiskies")
    public ResponseEntity<List<Whisky>> findWhiskysFilteredByYear(
            @RequestParam (name = "year", required = false) Integer year) {
        if (year != null) {
            return new ResponseEntity<>(whiskyRepository.findWhiskyByYear(year), HttpStatus.OK);
        }
        return new ResponseEntity<>(whiskyRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/whiskies/distilleries/{id}")
    public ResponseEntity findWhiskyByAgeAndDistillery(
            @PathVariable Long id,
            @RequestParam(name = "age", required = false) Integer age){
        if (age != null && id != null) {
            Optional<Distillery> foundDistilleryOptional = distilleryRepository.findById(id);
            if (!foundDistilleryOptional.isPresent()){
                return new ResponseEntity(id, HttpStatus.NOT_FOUND);
            } else {
                Distillery foundDistillery = foundDistilleryOptional.get();
                return new ResponseEntity(whiskyRepository.findWhiskyByAgeAndDistillery(age, foundDistillery), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(whiskyRepository.findAll(), HttpStatus.OK);
        }
    }

}
