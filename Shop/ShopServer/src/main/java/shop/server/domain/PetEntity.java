package shop.server.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import shop.core.domain.Pet;


@Entity
@Table(name="Pet")
@NamedQueries({
	@NamedQuery(name=PetEntity.FIND_PET_SALE_SUMMARY, 
			query="select p.name, o.orderSource, count(p.tag), 0, sum(p.price), 0, 0"
					+ " from PetEntity p, PetOrderEntity o where p.tag = o.petTag group by p.name, o.orderSource")
					
})
public class PetEntity implements Serializable, Comparable<PetEntity>{

	private static final long serialVersionUID = 1L;
	public static final String FIND_PET_SALE_SUMMARY = "findPetSaleSummary";
	

	public PetEntity(){
		
	}
	
	public PetEntity(Pet pet){
	
		this.setName(pet.getName());
		this.setOrderNumber(pet.getOrderNumber());
		this.setCustomerNumber(pet.getCustomerNumber());
		this.setPrice(pet.getPrice());
		this.setTag(pet.getTag());
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String name;
	private BigDecimal price;
	private int tag;
	private int orderNumber;
	
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
	public int compareTo(PetEntity o) {
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
		result = prime * result + id;
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
		PetEntity other = (PetEntity) obj;
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
		return true;
	}

	@Override
	public String toString() {
		return "Pet [id=" + id + ", name=" + name + ", price=" + price
				+ ", tag=" + tag + ", orderNumber=" + orderNumber
				+ ", customerNumber=" + customerNumber + "]";
	}
	
	public Pet returnPet(){
		Pet pet = new Pet();
		pet.setName(this.getName());
		pet.setOrderNumber(this.getOrderNumber());
		pet.setCustomerNumber(this.getCustomerNumber());
		pet.setPrice(this.getPrice());
		pet.setTag(this.getTag());
		return pet;
	}
	

	
	
	
}
