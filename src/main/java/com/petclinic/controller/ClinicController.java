package com.petclinic.controller;

import com.petclinic.model.Clinic;
import com.petclinic.model.Offer;
import com.petclinic.repository.ClinicRepository;
import com.petclinic.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Clinic controller.
 * <p>
 * Date: 2017-04-13
 *
 * @author Stanislau_Halauniou
 */
@RestController
@RequestMapping("/api/clinics")
class ClinicController {

    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private OfferRepository offerRepository;

    //TODO: add transaction, investigate cascading
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Clinic create(@RequestBody Clinic clinic) {
        Clinic createdClinic = clinicRepository.save(clinic);
        List<Offer> offers = clinic.getOffers();
        offers.stream().forEach(offer -> offer.setClinic(clinic));
        offerRepository.save(offers);
        clinic.setId(createdClinic.getId());
        return clinic;
    }

    //TODO: add transaction, investigate cascading
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Clinic update(@RequestBody Clinic clinic, @PathVariable String id) {
        Clinic clinicForUpdate = clinicRepository.findOne(id);
        offerRepository.delete(clinicForUpdate.getOffers());
        List<Offer> offers = clinic.getOffers();
        clinic.setId(id);
        offers.stream().forEach(offer -> offer.setClinic(clinic));
        offerRepository.save(offers);
        clinicRepository.save(clinic);
        return clinic;
    }

    //TODO: add transaction, investigate cascading
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String id) {
        Clinic clinicForDelete = clinicRepository.findOne(id);
        offerRepository.delete(clinicForDelete.getOffers());
        clinicRepository.delete(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Iterable<Clinic> getAll() {
        return clinicRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Clinic getClinicWithOffers(@PathVariable String id) {
        return clinicRepository.findOne(id);
    }

}
