package shop.core.domain;

import java.math.BigDecimal;

//This is a JPA projection approach to get a summary object which can be used for printing or GUI display
//it requires a constructor
public class PetSaleSummaryData implements Comparable<PetSaleSummaryData>{
	

			
	public PetSaleSummaryData(String petType, String orderSource, Long totalSold, Integer available, 
			BigDecimal revenue, BigDecimal profitLoss,
			Integer noOfRejectedOrders) {
		
		this.petType = petType;
		this.revenue = revenue;
		this.totalSold = totalSold;
		this.available = available;
		this.profitLoss = profitLoss;
		this.orderSource = orderSource;
		this.NoOfRejectedOrders = noOfRejectedOrders;
		
	}
	
	public PetSaleSummaryData(){
		this.setRevenue(BigDecimal.ZERO);
		this.setTotalSold(new Long(0));
	}
	
	private String petType;
	private BigDecimal revenue;
	private Long totalSold;
	private Integer available;
	private BigDecimal profitLoss;
	private String orderSource;
	private Integer NoOfRejectedOrders;
	private BigDecimal buyCost;
	private BigDecimal rejectionPenalty;

	public String getPetType() {
		return petType;
	}
	public void setPetType(String petType) {
		this.petType = petType;
	}
	public BigDecimal getRevenue() {
		return revenue;
	}
	public void setRevenue(BigDecimal revenue) {
		this.revenue = revenue;
	}
	public Long getTotalSold() {
		return totalSold;
	}
	public void setTotalSold(Long totalSold) {
		this.totalSold = totalSold;
	}
	public Integer getAvailable() {
		return available;
	}
	public void setAvailable(Integer available) {
		this.available = available;
	}
	public BigDecimal getProfitLoss() {
		return profitLoss;
	}
	public void setProfitLoss(BigDecimal profitLoss) {
		this.profitLoss = profitLoss;
	}
	public String getOrderSource() {
		return orderSource;
	}
	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}
	public Integer getNoOfRejectedOrders() {
		return NoOfRejectedOrders;
	}
	public void setNoOfRejectedOrders(Integer noOfRejectedOrders) {
		NoOfRejectedOrders = noOfRejectedOrders;
	}
	@Override
	public String toString() {
		return "PetSaleSummaryData [petType=" + petType + ", revenue="
				+ revenue + ", totalSold=" + totalSold + ", available="
				+ available + ", profitLoss=" + profitLoss + ", orderSource="
				+ orderSource + ", NoOfRejectedOrders=" + NoOfRejectedOrders
				+ "]";
	}
	@Override
	public int compareTo(PetSaleSummaryData o) {
		return o.getPetType().compareToIgnoreCase(this.getPetType());
	}
	public BigDecimal getBuyCost() {
		return buyCost;
	}
	public void setBuyCost(BigDecimal buyCost) {
		this.buyCost = buyCost;
	}
	public BigDecimal getRejectionPenalty() {
		return rejectionPenalty;
	}
	public void setRejectionPenalty(BigDecimal rejectionPenalty) {
		this.rejectionPenalty = rejectionPenalty;
	}
	
	
	
}
