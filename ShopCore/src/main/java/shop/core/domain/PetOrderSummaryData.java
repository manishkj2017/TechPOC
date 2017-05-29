package shop.core.domain;

public class PetOrderSummaryData {
	
	private String petType;
	private String orderSource;
	private String status;
	private String statusReason;
	private Integer noOfOrders;
	
	
	
	public PetOrderSummaryData(String petType, String orderSource,
			String status, String statusReason, Integer noOfOrders) {
		super();
		this.petType = petType;
		this.orderSource = orderSource;
		this.status = status;
		this.statusReason = statusReason;
		this.noOfOrders = noOfOrders;
	}
	
	public String getPetType() {
		return petType;
	}
	public void setPetType(String petType) {
		this.petType = petType;
	}
	public String getOrderSource() {
		return orderSource;
	}
	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusReason() {
		return statusReason;
	}
	public void setStatusReason(String statusReason) {
		this.statusReason = statusReason;
	}
	public Integer getNoOfOrders() {
		return noOfOrders;
	}
	public void setNoOfOrders(Integer noOfOrders) {
		this.noOfOrders = noOfOrders;
	}
	
	@Override
	public String toString() {
		return "PetOrderSummaryData [petType=" + petType + ", orderSource="
				+ orderSource + ", status=" + status + ", statusReason="
				+ statusReason + ", noOfOrders=" + noOfOrders + "]";
	}
	
	
	
}
