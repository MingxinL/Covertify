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
	
	public void AddTime(String albumId) {
		
		begin();
		Album tempAlbum = getSession().get(Album.class,albumId);
		tempAlbum.setTime(tempAlbum.getTime()+1);
		commit();
	}
	
	public void ReduceTime(String albumId) {
		
		begin();
		Album tempAlbum = getSession().get(Album.class,albumId);
		if(tempAlbum.getTime()>1) {
			tempAlbum.setTime(tempAlbum.getTime()-1);
		}
		
		commit();
	}

	
	public void addCustomer(Album album, Customer customer) {
		begin();
		album.addCustomer(customer);	
		commit();
		
	}
	
	public void deleteAlbum(String albumId) {
		begin();
		Album tempAlbum = getSession().get(Album.class, albumId);
		getSession().delete(tempAlbum);
		commit();
	}
	
}
