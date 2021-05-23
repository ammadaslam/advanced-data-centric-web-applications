package com.example.demo.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.exceptions.OrderException;
import com.example.demo.exceptions.SalespersonException;
import com.example.demo.exceptions.UpdateSalespersonException;
import com.example.demo.models.Order;
import com.example.demo.models.Salesperson;
import com.example.demo.services.SalespersonService;
import com.example.demo.validations.OrderPOSTValidations;
import com.example.demo.validations.SalespersonPOSTValidations;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

@RestController
@Validated
public class SalespersonController {

	@Autowired
	SalespersonService ss;

	@GetMapping(path = "api/salespeople")
	@CrossOrigin(origins = "http://localhost:4200")
	public Iterable<Salesperson> getSalespeople() {
		return ss.getAllSalespeople();
	}

	@GetMapping(path = "api/salespeople/{spid}")
	public Optional<Salesperson> findBySPID(@PathVariable String spid) {
		Optional<Salesperson> sp = ss.findAllBySpid(spid);
		if (sp.isPresent()) {
			return ss.findAllBySpid(spid);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Salesperson: " + spid + " not found!");
		}

	}

	@DeleteMapping(path = "api/salespeople/{spid}")
	public void deleteSalesPerson(@PathVariable String spid) {
		ss.delete(spid);
	}

	@Validated(SalespersonPOSTValidations.class)
	@PostMapping(path = "api/salespeople")
	public void addSalesperson2(@Valid @RequestBody Salesperson sp) throws SalespersonException {
		try {
			ss.save(sp);
		} catch (SalespersonException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());

		}
	}

	@PutMapping("api/salespeople/{spid}")
	public Salesperson updateSalesPerson(@RequestBody Salesperson sp, @PathVariable String spid)
			throws UpdateSalespersonException {
		if (spid == null) {
			throw new UpdateSalespersonException("Could not update salespreson!");
		}
		Salesperson salesperson = ss.updateSalesperson(sp, spid);
		return salesperson;

	}

	@PutMapping(path = "salespeople/{spid}")
	public Optional<Salesperson> updateSalesperson(@PathVariable String spid) {
		System.out.print(spid);
		return ss.findAllBySpid(spid);

	}

}
