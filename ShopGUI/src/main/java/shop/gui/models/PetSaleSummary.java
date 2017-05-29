package shop.gui.models;

import java.math.BigDecimal;

public class PetSaleSummary {
	
	private String petType;
	private Integer totalSold;
	private BigDecimal revenue;
	private BigDecimal profitLoss;
	private Integer available;
	
	public String getPetType() {
		return petType;
	}
	public void setPetType(String petType) {
		this.petType = petType;
	}
	public Integer getTotalSold() {
		return totalSold;
	}
	public void setTotalSold(Integer totalSold) {
		this.totalSold = totalSold;
	}
	public BigDecimal getRevenue() {
		return revenue;
	}
	public void setRevenue(BigDecimal revenue) {
		this.revenue = revenue;
	}
	public BigDecimal getProfitLoss() {
		return profitLoss;
	}
	public void setProfitLoss(BigDecimal profitLoss) {
		this.profitLoss = profitLoss;
	}
	public Integer getAvailable() {
		return available;
	}
	public void setAvailable(Integer available) {
		this.available = available;
	}
	
	
	
}
