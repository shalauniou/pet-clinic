package com.petclinic.controller;

import com.petclinic.model.ClinicService;
import com.petclinic.repository.ClinicServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Clinic Service controller.
 * <p/>
 * Copyright (C) 2016 copyright.com
 * <p/>
 * Date: 4/18/2017
 *
 * @author Stanislau Halauniou
 */
@RestController
@RequestMapping("/api/services")
public class ClinicServiceController {

    @Autowired
    private ClinicServiceRepository repository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Iterable<ClinicService> getAll() {
        return repository.findAll();
    }
}
