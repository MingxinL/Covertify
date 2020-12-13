package com.Covertify.dao;

import com.Covertify.hibernate.entity.Album;
import com.Covertify.hibernate.entity.Customer;

public class AlbumDAO extends DAO {
	
	public void saveAlbums(Album album) {
		begin();
		getSession().save(album);
		commit();
	}
	
	public boolean Exist(String albumId) {
		Album tempAlbum = getSession().get(Album.class,albumId);
		if (tempAlbum == null) {
			return false;
		}
		return true;
	}
	
	public Album getAlbums(String albumId) {
		return getSession().get(Album.class,albumId);
	}
	

	
	public void addCustomer(Album album, Customer customer) {
		begin();
		album.addCustomer(customer);	
		commit();
		
	}
	
}
