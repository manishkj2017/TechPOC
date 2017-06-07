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

import shop.core.domain.PetOrder;

@Entity
@Table(name="PetOrder")
@NamedQueries({
	@NamedQuery(name=PetOrderEntity.FIND_PET_ORDER_SUMMARY, 
			query="select o.petType, o.orderSource, o.status, o.statusReason, count(o) "
					+ "from PetOrderEntity o group by o.petType, o.orderSource, o.status, o.statusReason")
					
})
public class PetOrderEntity implements Serializable, Comparable<PetOrderEntity>{

	
	private static final long serialVersionUID = 1L;
	public static final String FIND_PET_ORDER_SUMMARY = "findPetOrderSummary";

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private BigDecimal bidPrice;
	private String petType;
	private String status;
	private String customerName;
	private int customerNumber;
	private int orderNumber;
	private String statusReason;
	private String orderSource;
	private int petTag;
	
	public PetOrderEntity() {
		
	}
	
	public PetOrderEntity(PetOrder order) {
		this.setBidPrice(order.getBidPrice());
		this.setCustomerName(order.getCustomerName());
		this.setCustomerNumber(order.getCustomerNumber());
		this.setOrderNumber(order.getOrderNumber());
		this.setOrderSource(order.getOrderSource());
		this.setPetTag(order.getPetTag());
		this.setPetType(order.getPetType());
		this.setStatus(order.getStatus());
		this.setStatusReason(order.getStatusReason());
	}

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getBidPrice() {
		return bidPrice;
	}

	public void setBidPrice(BigDecimal bidPrice) {
		this.bidPrice = bidPrice;
	}

	public String getPetType() {
		return petType;
	}

	public void setPetType(String petType) {
		this.petType = petType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public int getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(int customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getStatusReason() {
		return statusReason;
	}

	public void setStatusReason(String statusReason) {
		this.statusReason = statusReason;
	}

	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

	public int getPetTag() {
		return petTag;
	}

	public void setPetTag(int petTag) {
		this.petTag = petTag;
	}
	
	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}
	

	
	@Override
	public int compareTo(PetOrderEntity o) {
		if(o.getOrderNumber() > this.getOrderNumber())
			return -1;
		
		else if(o.getOrderNumber() < this.getOrderNumber())
			return 1;
		
		else
			return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bidPrice == null) ? 0 : bidPrice.hashCode());
		result = prime * result
				+ ((customerName == null) ? 0 : customerName.hashCode());
		result = prime * result + customerNumber;
		result = prime * result + id;
		result = prime * result + orderNumber;
		result = prime * result
				+ ((orderSource == null) ? 0 : orderSource.hashCode());
		result = prime * result + petTag;
		result = prime * result + ((petType == null) ? 0 : petType.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result
				+ ((statusReason == null) ? 0 : statusReason.hashCode());
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
		PetOrderEntity other = (PetOrderEntity) obj;
		if (bidPrice == null) {
			if (other.bidPrice != null)
				return false;
		} else if (!bidPrice.equals(other.bidPrice))
			return false;
		if (customerName == null) {
			if (other.customerName != null)
				return false;
		} else if (!customerName.equals(other.customerName))
			return false;
		if (customerNumber != other.customerNumber)
			return false;
		if (id != other.id)
			return false;
		if (orderNumber != other.orderNumber)
			return false;
		if (orderSource == null) {
			if (other.orderSource != null)
				return false;
		} else if (!orderSource.equals(other.orderSource))
			return false;
		if (petTag != other.petTag)
			return false;
		if (petType == null) {
			if (other.petType != null)
				return false;
		} else if (!petType.equals(other.petType))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (statusReason == null) {
			if (other.statusReason != null)
				return false;
		} else if (!statusReason.equals(other.statusReason))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PetOrder [id=" + id + ", bidPrice=" + bidPrice + ", petType="
				+ petType + ", status=" + status + ", customerName="
				+ customerName + ", customerNumber=" + customerNumber
				+ ", orderNumber=" + orderNumber + ", statusReason="
				+ statusReason + ", orderSource=" + orderSource + ", petTag="
				+ petTag + "]";
	}
	
	
}
