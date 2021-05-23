package com.example.demo.services;

import java.util.Optional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.exceptions.SalespersonException;
import com.example.demo.models.Order;
import com.example.demo.models.Salesperson;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.repositories.SalespersonRepository;

@Service
public class SalespersonService {

	@Autowired
	SalespersonRepository sr;

	@Autowired
	OrderRepository or;

	public Iterable<Salesperson> getAllSalespeople() {
		return sr.findAll();
	}

	public void deleteSalesPerson(int id) {
		sr.deleteById(id);
	}

	public Salesperson getOneSalesperson(int id) {
		Optional<Salesperson> sp = sr.findById(id);
		if (sp.isPresent()) {
			return sp.get();
		} else {
			return null;
		}
		// return sr.findById(id);
	}

	public void delete(String spid) {
		Optional<Salesperson> sp = sr.findAllBySpid(spid);
		int id = sp.get().getId();
		if (sp.isPresent()) {
			List<Order> osp = or.findByOrderSalesperson(sp.get());
			if (osp.size() > 0) {
				throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
						"Salesperson: " + spid + " can't be deleted. He/she has orders");
			} else
				sr.deleteById(id);
		}

	}

	public Salesperson addSalesperson(Salesperson salesperson) {
		if (salesperson.getName() == null || salesperson.getSpid() == null) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"addsalesperson.sp.name: name must be provided, addsalesperson.sp.id: id must be provided");
		}
		String spid = salesperson.getSpid();
		Optional<Salesperson> sperson = sr.findAllBySpid(spid);
		if (sperson.isPresent()) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
					"salesperson: " + sperson.get().getSpid() + " already exists");
		} else {
			Salesperson sp = sr.save(salesperson);
			return sp;
		}
	}

	public Salesperson updateSalesperson(Salesperson person, String spid) {
		if (person.getSpid() != null) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"update.sp.spid: spid must not be provided ");
		}
		if (person.getName() == null) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"update.sp.sname: sname must be provided ");
		}
		Optional<Salesperson> sperson = sr.findAllBySpid(spid);
		if (sperson.isPresent()) {
			sperson.get().setName(person.getName());
			Salesperson sp = sr.save(sperson.get());
			return sp;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "salesperson spid:" + spid + "not found");
		}

	}

	public void save(Salesperson sp) throws SalespersonException {
		try {
			sr.save(sp);
		} catch (DataIntegrityViolationException e) {
			throw new SalespersonException("Salesperson " + sp.getSpid() + " already exists");
		}
	}

	public Optional<Salesperson> findAllBySpid(String spid) {
		return sr.findAllBySpid(spid);
	}

}
