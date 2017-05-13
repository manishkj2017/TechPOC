package shop.core.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import shop.core.enums.PetTypes;

@Entity
public class Pet implements Serializable, Comparable<Pet>{

	private static final long serialVersionUID = 1L;

	public Pet(){
		
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String name;
	private BigDecimal price;
	private PetTypes type;
	private int tag;
	private int orderNumber;
	private BigDecimal askPrice;
	private int customerNumber;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public PetTypes getType() {
		return type;
	}

	public void setType(PetTypes type) {
		this.type = type;
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

	public BigDecimal getAskPrice() {
		return askPrice;
	}

	public void setAskPrice(BigDecimal askPrice) {
		this.askPrice = askPrice;
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
		result = prime * result
				+ ((askPrice == null) ? 0 : askPrice.hashCode());
		result = prime * result + customerNumber;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + orderNumber;
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + tag;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		if (askPrice == null) {
			if (other.askPrice != null)
				return false;
		} else if (!askPrice.equals(other.askPrice))
			return false;
		if (customerNumber != other.customerNumber)
			return false;
		if (id != other.id)
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
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pet [id=" + id + ", name=" + name + ", price=" + price
				+ ", type=" + type + ", tag=" + tag + ", orderNumber="
				+ orderNumber + ", askPrice=" + askPrice + ", customerNumber="
				+ customerNumber + "]";
	}
	
	
	
}
