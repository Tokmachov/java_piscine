package edu.school21.models;

import java.util.Objects;

public class Product {
	
	public Product(long id, String name, int price) {
		this.id = id;
		this.name = name;
		this.price = price;
	}
	
	public int getPrice() {
		return price;
	}
	
	public String getName() {
		return name;
	}

	public long getId() {
		return id;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Product product = (Product) o;
		return id == product.id && price == product.price && name.equals(product.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, price);
	}

	//	@Override
//	public boolean equals(Object o) {
//		if (o == this) {
//			return true;
//		}
//
//		if (!(o instanceof Product)) {
//			return false;
//		}
//
//		Product p = (Product) o;
//
//		return id == p.getId()
//				&& name.equals(p.getName())
//				&& price == p.getPrice();
//	}
	@Override
	public String toString() {
		return "id: " + getId() + " name: " + getName() + " price: " + getPrice();
	}
	private long id;
	private String name;
	private int price;
}