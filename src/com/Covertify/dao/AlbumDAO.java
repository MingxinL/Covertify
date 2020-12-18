package com.Covertify.dao;

import org.hibernate.HibernateException;

import com.Covertify.hibernate.entity.Album;
import com.Covertify.hibernate.entity.Customer;

public class AlbumDAO extends DAO {
	
	public void saveAlbums(Album album) {
	
		 try {
			 	beginTransaction();
	            getSession().save(album);
	            commit();
	         
	        } catch (HibernateException e) {
	            getSession().getTransaction().rollback();
	        } finally {
	            getSession().close();
	        }       
	    
		
		
	}
	
	public boolean Exist(String albumId) {
		Album tempAlbum = getAlbums(albumId);
		if (tempAlbum == null) {
			return false;
		}
		return true;
	}
	
	public Album getAlbums(String albumId) {
		//return getSession().get(Album.class,albumId);
		Album album = null;
		 try {
			 	beginTransaction();
	            album = getSession().find(Album.class, albumId);
	            commit();
	        } catch (HibernateException e) {
	            getSession().getTransaction().rollback();
	        } finally {
	            getSession().close();
	        }       
	        return album;
		
		
	}
	
	public void AddTime(Album album) {
		

		
		 try {
			 	beginTransaction();
	           
	            album.setTime(album.getTime()+1);
	            getSession().update(album);
	            commit();
	        } catch (HibernateException e) {
	            getSession().getTransaction().rollback();
	        } finally {
	            getSession().close();
	        }       
	      
		
		
		
	}
//	
	public void ReduceTime(String albumId) {
		
		
		Album tempAlbum = getAlbums(albumId);
		 try {
			 beginTransaction();
	            
	            if(tempAlbum.getTime()>1) {
	    			tempAlbum.setTime(tempAlbum.getTime()-1);
	    		}
	            getSession().update(tempAlbum);
	            commit();
	           
	        } catch (HibernateException e) {
	            getSession().getTransaction().rollback();
	        } finally {
	            getSession().close();
	        }       
	      
	}

//	
	public void addCustomer(Album album, Customer customer) {
		

		 try {
			 	beginTransaction();
			 	
	            album.addCustomer(customer);	
			 	//customer.a
	            System.out.println(" album.addCustomer(customer);	");
	            getSession().update(album);
	            commit();
	        } catch (HibernateException e) {
	            getSession().getTransaction().rollback();
	        } finally {
	            getSession().close();
	        }       
	      
		
	}

	public void deleteAlbum(String albumId) {
		
		Album tempAlbum = getAlbums(albumId);
		 try {
			 beginTransaction();
				
				getSession().delete(tempAlbum);
				commit();
	        } catch (HibernateException e) {
	            getSession().getTransaction().rollback();
	        } finally {
	            getSession().close();
	        }  
		
	}
	
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
	
	

	
}
