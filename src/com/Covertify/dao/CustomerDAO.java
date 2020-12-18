package com.Covertify.dao;
import java.util.List;

import org.hibernate.HibernateException;
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
	
	public int saveCustomer(Customer customer) {
//		begin();
//		getSession().save(customer);
//		commit();
		int result = 0;
        try {
        	beginTransaction();
            getSession().save(customer);
            commit();
            result = 1;
        } catch (HibernateException e) {
            getSession().getTransaction().rollback();
        } finally {
            getSession().close();
        }       
        return result;
		
		
	}
	
	
	public boolean Exist(String customerId) {
		Customer tempCustomer = getCustomers(customerId);
		if (tempCustomer == null) {
			return false;
		}
		return true;
	}
	
	public Customer getCustomers(String customerId) {
		//return getSession().get(Customer.class,customerId);
		
		Customer customer = null;
		 try {
			 	beginTransaction();
	            customer = getSession().find(Customer.class, customerId);
	            commit();
	        } catch (HibernateException e) {
	            getSession().getTransaction().rollback();
	        } finally {
	            getSession().close();
	        }       
	        return customer;
	}
	
	public List<Album> getAlbums(String customerId) {
		//Customer tempCustomer = getSession().get(Customer.class,customerId);
		//return tempCustomer.getAlbums();
		
		Customer customer = getCustomers(customerId);
		return customer.getAlbums();
		
		
		
	}
	
//	public int deleteAlbums(String customerId, String albumId) {
//		int result = 0;
//		AlbumDAO adao = new AlbumDAO();
//        Album tempAlbum = adao.getAlbums(albumId);
// 		Customer tempCustomer = getCustomers(customerId);
//		 try {
//			 	beginTransaction();   
//	    		tempAlbum.deleteCustomers(tempCustomer);
//	    		getSession().update(tempAlbum);
//	            commit();
//	            result = 1;
//	        } catch (HibernateException e) {
//	            getSession().getTransaction().rollback();
//	        } finally {
//	            getSession().close();
//	        }       
//	        return result;
//		
//	}
	
	public int deleteAlbums(Album album, Customer customer) {
		int result = 0;
		
		 try {
			 	beginTransaction();   
	    		album.deleteCustomers(customer.getId());
	            System.out.println("album.deleteCustomers(customer);");
	    		getSession().update(album);
	            commit();
	            result = 1;
	        } catch (HibernateException e) {
	            getSession().getTransaction().rollback();
	        } finally {
	            getSession().close();
	        }       
	        return result;
		
	}
	
	public void deleteAlbums(String customerId, String albumId) {
		
		  Album tempAlbum = getSession().get(Album.class,albumId);
		  Customer tempCustomer = getSession().get(Customer.class,customerId);
		  
		  commit();
		  
		  
		  try {
			 	beginTransaction();   
			 	tempAlbum.deleteCustomers(tempCustomer.getId());
	    		getSession().update(tempAlbum);
	            commit();
	    
	        } catch (HibernateException e) {
	            getSession().getTransaction().rollback();
	        } finally {
	            getSession().close();
	        }       
	    
		 }
}
