package com.sogeti.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "orders", uniqueConstraints = { @UniqueConstraint(columnNames = { "orderId" }) })
public class OrderModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "orderId", updatable = false, nullable = false, length = 11)
	private int orderId;

	@Column(name = "dateOrdered", nullable = false)
	private Date dateOrdered;

	@Column(name = "dateReceived", nullable = true)
	private Date dateReceived;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", columnDefinition = "enum('SHIPPED','RECEIVED','CANCELLED')", nullable = false)
	private Status status;

	@Column(name = "createdStaffId", nullable = false, length = 11)
	private int createdStaffId;

	@Column(name = "createdDate", nullable = false)
	private Date createdDate;

	@Column(name = "updatedStaffId", nullable = true, length = 11)
	private int updatedStaffId;

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

	public int getCreatedStaffId() {
		return createdStaffId;
	}

	public void setCreatedStaffId(int createdStaffId) {
		this.createdStaffId = createdStaffId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public int getUpdatedStaffId() {
		return updatedStaffId;
	}

	public void setUpdatedStaffId(int updatedStaffId) {
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
		result = prime * result + createdStaffId;
		result = prime * result + customerId;
		result = prime * result + ((dateOrdered == null) ? 0 : dateOrdered.hashCode());
		result = prime * result + ((dateReceived == null) ? 0 : dateReceived.hashCode());
		result = prime * result + orderId;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((updatedDate == null) ? 0 : updatedDate.hashCode());
		result = prime * result + updatedStaffId;
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
		OrderModel other = (OrderModel) obj;
		if (createdDate == null) {
			if (other.createdDate != null)
				return false;
		} else if (!createdDate.equals(other.createdDate))
			return false;
		if (createdStaffId != other.createdStaffId)
			return false;
		if (customerId != other.customerId)
			return false;
		if (dateOrdered == null) {
			if (other.dateOrdered != null)
				return false;
		} else if (!dateOrdered.equals(other.dateOrdered))
			return false;
		if (dateReceived == null) {
			if (other.dateReceived != null)
				return false;
		} else if (!dateReceived.equals(other.dateReceived))
			return false;
		if (orderId != other.orderId)
			return false;
		if (status != other.status)
			return false;
		if (updatedDate == null) {
			if (other.updatedDate != null)
				return false;
		} else if (!updatedDate.equals(other.updatedDate))
			return false;
		if (updatedStaffId != other.updatedStaffId)
			return false;
		return true;
	}

}
