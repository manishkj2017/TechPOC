package shop.core.domain;

import java.io.Serializable;
import java.math.BigDecimal;


public class Pet implements Serializable, Comparable<Pet>{

	private static final long serialVersionUID = 1L;

	public Pet(){
		
	}
	
	private String name;
	private BigDecimal price;
	private int tag;
	private int orderNumber;
	
	private int customerNumber;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public int getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(int customerNumber) {
		this.customerNumber = customerNumber;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int compareTo(Pet o) {
		if(o.getTag() > this.getTag())
			return -1;
		
		else if(o.getTag() < this.getTag())
			return 1;
		
		else
			return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + customerNumber;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + orderNumber;
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + tag;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pet other = (Pet) obj;
		if (customerNumber != other.customerNumber)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (orderNumber != other.orderNumber)
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (tag != other.tag)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pet [name=" + name + ", price=" + price
				+ ", tag=" + tag + ", orderNumber=" + orderNumber
				+ ", customerNumber=" + customerNumber + "]";
	}
	

	
	
	
}
