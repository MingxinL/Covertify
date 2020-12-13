package com.Covertify.dao;
import java.util.List;

import org.hibernate.query.Query;

import com.Covertify.hibernate.entity.Album;
import com.Covertify.hibernate.entity.Customer;

public class CustomerDAO extends DAO {

//		public Customer checkLoginbefore(String CustomerId) {
//			
//			String hqlQuery = "FROM Customer WHERE id=: id";
//			Query query = getSession().createQuery(hqlQuery);
//			query.setString("id",CustomerId);
//			
//			return (Customer) query.uniqueResult();
//					
//		}
	
	public void saveCustomer(Customer customer) {
		begin();
		getSession().save(customer);
		commit();
	}
	
	
	public boolean Exist(String customerId) {
		Customer tempCustomer = getSession().get(Customer.class,customerId);
		if (tempCustomer == null) {
			return false;
		}
		return true;
	}
	
	public Customer getCustomers(String customerId) {
		return getSession().get(Customer.class,customerId);
	}
	
	public List<Album> getAlbums(String customerId) {
		Customer tempCustomer = getSession().get(Customer.class,customerId);
		return tempCustomer.getAlbums();
		
	}
	
	public void deleteAlbums(String customerId, String albumId) {
		begin();
		Album tempAlbum = getSession().get(Album.class,albumId);
		Customer tempCustomer = getSession().get(Customer.class,customerId);
		tempAlbum.deleteCustomers(tempCustomer);	
		commit();
		
	}
}
