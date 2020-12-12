package com.Covertify.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Covertify.hibernate.entity.Album;
import com.Covertify.hibernate.entity.Customer;



@Repository
public class CustomerDao{

	// need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public List<Customer> getCustomers() {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
				
		// create a query
		Query<Customer> theQuery = 
				currentSession.createQuery("from Customer", Customer.class);
		
		// execute query and get result list
		List<Customer> customers = theQuery.getResultList();
				
		// return the results		
		return customers;
	}
	
//	@Transactional
//	public List<Album> getCustomersAlbum() {
//		
//		String customerId = "ff8080817653a0b5017653a0b6d20001";
//		// get the current hibernate session
//		Session currentSession = sessionFactory.getCurrentSession();
//				
//		Query<Customer> theQuery = 
//				currentSession.createQuery("from Customer c where c.id = ? ");
//		theQuery.setString(0,customerId);
//		
//		// execute query and get result list
//		List<Customer> customers = theQuery.getResultList();
//				
//		// return the results		
//		return customers;
//	}
}
