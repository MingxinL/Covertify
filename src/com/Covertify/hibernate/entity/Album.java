package com.Covertify.hibernate.entity;

import java.util.ArrayList;
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

@Entity
@Table(name="album")
public class Album {

	// define our fields
	
	// define constructors
	
	// define getter setters
	
	// define tostring
	
	// annotate fields
	
	@Id
//	@GeneratedValue(generator="system-uuid")
//	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(name="id")
	private String id;
	
	@Column(name="name")
	private String name;

	
	@Column(name="image")
	private String image;
	
		
	@ManyToMany(fetch=FetchType.LAZY,
			cascade= {CascadeType.PERSIST, CascadeType.MERGE,
			 CascadeType.DETACH, CascadeType.REFRESH})
	@JoinTable(
			name="customer_album",
			joinColumns=@JoinColumn(name="album_id"),
			inverseJoinColumns=@JoinColumn(name="customer_id")
			)
	private List<Customer> customers;
	
	
	public Album() {
		
	}

	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	


	
	public Album(String id, String name, String image) {
		super();
		this.id = id;
		this.name = name;
		this.image = image;
	}



	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	// add a convenience method
	
	public void addCustomer(Customer theCustomer) {
		
		if (customers == null) {
			customers = new ArrayList<>();
		}
		
		customers.add(theCustomer);
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



	@Override
	public String toString() {
		return "Album [id=" + id + ", name=" + name + ", image=" + image + ", customers=" + customers + "]";
	}
	
	
	
	
}
