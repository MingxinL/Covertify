package com.Covertify.hibernate.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity(name="Customer")
@Table(name="customer")
public class Customer {

	@Id
//	@GeneratedValue(generator="system-uuid")
//	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(name="id")
	private String id;
	
	@Column(name="name")
	private String name;

	
	@Column(name="image")
	private String image;
	
	@Column(name="role")
	 private String role;
	 
	 public String getRole() {
	  return role;
	 }



	 public void setRole(String role) {
	  this.role = role;
	 }
	
	public Customer(String id, String name, String image) {
		super();
		this.id = id;
		this.name = name;
		this.image = image;
	}

	@ManyToMany(fetch=FetchType.EAGER,
			cascade= {CascadeType.PERSIST, CascadeType.MERGE,
			 CascadeType.DETACH, CascadeType.REFRESH})
	@JoinTable(
			name="customer_album",
			joinColumns=@JoinColumn(name="customer_id"),
			inverseJoinColumns=@JoinColumn(name="album_id")
			)	
	private List<Album> albums;

	
	public Customer() {
		
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	

	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getImage() {
		return image;
	}



	public void setImage(String image) {
		this.image = image;
	}



	public List<Album> getAlbums() {
		return albums;
	}

	public void setAlbums(List<Album> albums) {
		this.albums = albums;
	}



	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", image=" + image + "]";
	}

	

	
}





