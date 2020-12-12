package com.Covertify.hibernate.demo;



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.Covertify.hibernate.entity.Album;
import com.Covertify.hibernate.entity.Customer;



public class CreateCustomerAndAlbums {

	public static void main(String[] args) {

		// create session factory
		SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")
								.addAnnotatedClass(Album.class)
								.addAnnotatedClass(Customer.class)
								.buildSessionFactory();
		
		// create session
		Session session = factory.getCurrentSession();
		
		try {			
			
			// start a transaction
			session.beginTransaction();
			
			//add
						
//			// create a album
//			Album tempAlbum = new Album("hsdiwediw","Pacman","https://www.google.com/search?q=image&tbm=isch&ved=2ahUKEwjcp6-Z4cbtAhVLnlkKHaGIAE4Q2-cCegQIABAA&oq=image&gs_lcp=CgNpbWcQAzIHCAAQsQMQQzIHCAAQsQMQQzIFCAAQsQMyAggAMgIIADIICAAQsQMQgwEyBQgAELEDMggIABCxAxCDATICCAAyCAgAELEDEIMBOgQIIxAnOgcIIxDqAhAnOgQIABBDUP4yWLtJYKZPaAFwAHgAgAF2iAHmBJIBAzIuNJgBAKABAaoBC2d3cy13aXotaW1nsAEKwAEB&sclient=img&ei=adXTX5zPKcu85gKhkYLwBA&bih=864&biw=1533#imgrc=QWWLMK52zYSopM");
//						
//			// save the album
//			System.out.println("\nSaving the album...");
//			session.save(tempAlbum);
//			System.out.println("Saved the album: " + tempAlbum);
//			
//			// create the customers
//	
//			Customer tempCustomer1 = new Customer("ssss8888mmm", "Micy", "https://www.google.com/search?q=image&tbm=isch&ved=2ahUKEwjcp6-Z4cbtAhVLnlkKHaGIAE4Q2-cCegQIABAA&oq=image&gs_lcp=CgNpbWcQAzIHCAAQsQMQQzIHCAAQsQMQQzIFCAAQsQMyAggAMgIIADIICAAQsQMQgwEyBQgAELEDMggIABCxAxCDATICCAAyCAgAELEDEIMBOgQIIxAnOgcIIxDqAhAnOgQIABBDUP4yWLtJYKZPaAFwAHgAgAF2iAHmBJIBAzIuNJgBAKABAaoBC2d3cy13aXotaW1nsAEKwAEB&sclient=img&ei=adXTX5zPKcu85gKhkYLwBA&bih=864&biw=1533#imgrc=YztiWQNtDk0NQM");
//			Customer tempCustomer2 = new Customer("sss43i2dhkd", "Yan", "https://www.google.com/search?q=image&tbm=isch&ved=2ahUKEwjcp6-Z4cbtAhVLnlkKHaGIAE4Q2-cCegQIABAA&oq=image&gs_lcp=CgNpbWcQAzIHCAAQsQMQQzIHCAAQsQMQQzIFCAAQsQMyAggAMgIIADIICAAQsQMQgwEyBQgAELEDMggIABCxAxCDATICCAAyCAgAELEDEIMBOgQIIxAnOgcIIxDqAhAnOgQIABBDUP4yWLtJYKZPaAFwAHgAgAF2iAHmBJIBAzIuNJgBAKABAaoBC2d3cy13aXotaW1nsAEKwAEB&sclient=img&ei=adXTX5zPKcu85gKhkYLwBA&bih=864&biw=1533#imgrc=YztiWQNtDk0NQM");
//						
//			// add customers to the album
//			tempAlbum.addCustomer(tempCustomer1);
//			tempAlbum.addCustomer(tempCustomer2);
//			
//			// save the customers
//			System.out.println("\nSaving customers ...");
//			session.save(tempCustomer1);
//			session.save(tempCustomer2);
//			System.out.println("Saved customers: " + tempAlbum.getCustomers());
			

			
			//check
			
			// get the customer Micy from database
			String customerId = "ff8080817653a0b5017653a0b6d20001";
			Customer tempCustomer = session.get(Customer.class, customerId);
//			
			System.out.println("\nLoad customer" + tempCustomer);
			System.out.println("\n Album" + tempCustomer.getAlbums());

			
			
//			commit transaction
			session.getTransaction().commit();
			
			System.out.println("Done!");
		}
		finally {
			
			// add clean up code
			session.close();
			
			factory.close();
		}
	}

}