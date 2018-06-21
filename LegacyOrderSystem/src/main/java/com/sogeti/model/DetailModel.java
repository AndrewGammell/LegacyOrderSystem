package com.sogeti.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "orders_details", uniqueConstraints = { @UniqueConstraint(columnNames = { "order_id" }) })
public class DetailModel {

	@Id
	@Column(name = "order_id", nullable = false, unique = true, length = 11)
	private int orderId;

	@Column(name = "product_id", nullable = false, length = 11)
	private int productId;

	@Column(name = "quantity", nullable = false, length = 11)
	private int quantity;

	@Column(name = "unit_price", nullable = false, length = 11)
	private int unitPrice;

	@Column(name = "created_staff_id", nullable = false, length = 11)
	private String createdStaffId;

	@Column(name = "created_date", nullable = false)
	private Date createdDate;

	@Column(name = "updated_staff_id", nullable = true)
	private String updatedStaffId;

	@Column(name = "updated_date")
	private Date updatedDate;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int id) {
		orderId = id;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getCreatedStaffId() {
		return createdStaffId;
	}

	public void setCreatedStaffId(String createdStaffId) {
		this.createdStaffId = createdStaffId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedStaffId() {
		return updatedStaffId;
	}

	public void setUpdatedStaffId(String updatedStaffId) {
		this.updatedStaffId = updatedStaffId;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Override
	public String toString() {
		return "DetailEntity [OrderId=" + orderId + ", productId=" + productId + ", quantity=" + quantity
				+ ", unitPrice=" + unitPrice + ", createStaffId=" + createdStaffId + ", createdDate=" + createdDate
				+ ", updatedStaffId=" + updatedStaffId + ", updatedDate=" + updatedDate + "]";
	}

	@Override

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdStaffId == null) ? 0 : createdStaffId.hashCode());
		result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + orderId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		DetailModel other = (DetailModel) obj;
		if (createdStaffId == null) {
			if (other.createdStaffId != null) {
				return false;
			}
		} else if (!createdStaffId.equals(other.createdStaffId)) {
			return false;
		}
		if (createdDate == null) {
			if (other.createdDate != null) {
				return false;
			}
		} else if (!createdDate.equals(other.createdDate)) {
			return false;
		}
		if (orderId != other.orderId) {
			return false;
		}
		return true;
	}

}
