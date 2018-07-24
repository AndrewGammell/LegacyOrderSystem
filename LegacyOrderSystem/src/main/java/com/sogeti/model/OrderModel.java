package com.sogeti.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "orders", uniqueConstraints = { @UniqueConstraint(columnNames = { "orderId" }) })
public class OrderModel {

	@Id
	@Column(name = "orderId", nullable = false, length = 11)
	private int orderId;

	@Column(name = "dateOrdered", nullable = false)
	private Date dateOrdered;

	@Column(name = "dateReceived", nullable = true)
	private Date dateReceived;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private Status status;

	@Column(name = "createdStaffId", nullable = false, length = 11)
	private String createdStaffId;

	@Column(name = "createdDate", nullable = false)
	private Date createdDate;

	@Column(name = "updatedStaffId", nullable = true, length = 11)
	private String updatedStaffId;

	@Column(name = "updatedDate", nullable = true)
	private Date updatedDate;

	@Column(name = "customerId", nullable = false, length = 11)
	private int customerId;

	public enum Status {
		SHIPPED, RECEIVED, CANCELLED
	}

	public int getOrderId() {
		return orderId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Date getDateOrdered() {
		return dateOrdered;
	}

	public void setDateOrdered(Date dateOrdered) {
		this.dateOrdered = dateOrdered;
	}

	public Date getDateReceived() {
		return dateReceived;
	}

	public void setDateReceived(Date dateReceived) {
		this.dateReceived = dateReceived;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
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
		return "OrderEntity [orderId=" + orderId + ", dateOrdered=" + dateOrdered + ", dateReceived=" + dateReceived
				+ ", status=" + status + ", createdStaffId=" + createdStaffId + ", createdDate=" + createdDate
				+ ", updatedStaffId=" + updatedStaffId + ", updatedDate=" + updatedDate + "]";
	}

	@Override

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + ((createdStaffId == null) ? 0 : createdStaffId.hashCode());
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
		OrderModel other = (OrderModel) obj;
		if (createdDate == null) {
			if (other.createdDate != null) {
				return false;
			}
		} else if (!createdDate.equals(other.createdDate)) {
			return false;
		}
		if (createdStaffId == null) {
			if (other.createdStaffId != null) {
				return false;
			}
		} else if (!createdStaffId.equals(other.createdStaffId)) {
			return false;
		}
		if (orderId != other.orderId) {
			return false;
		}
		return true;
	}

}
