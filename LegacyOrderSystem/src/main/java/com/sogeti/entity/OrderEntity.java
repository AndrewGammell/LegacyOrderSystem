package com.sogeti.entity;

import java.util.Date;

public class OrderEntity {
	private int orderId;
	private Date dateOrdered;
	private Date dateRecieved;
	private String status;
	private String createdStaffId;
	private Date createdDate;
	private String updatedStaffId;
	private Date updatedDate;
	private DetailEntity details;

	public DetailEntity getDetails() {
		return details;
	}

	public void setDetails(DetailEntity details) {
		this.details = details;
	}

	public int getOrderId() {
		return orderId;
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

	public Date getDateRecieved() {
		return dateRecieved;
	}

	public void setDateRecieved(Date daterecieved) {
		this.dateRecieved = daterecieved;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
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
		return "OrderEntity [orderId=" + orderId + ", dateOrdered=" + dateOrdered + ", dateRecieved=" + dateRecieved
				+ ", status=" + status + ", createdStaffId=" + createdStaffId + ", createdDate=" + createdDate
				+ ", updatedStaffId=" + updatedStaffId + ", updatedDate=" + updatedDate + ", details=" + details + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + ((createdStaffId == null) ? 0 : createdStaffId.hashCode());
		result = prime * result + ((dateOrdered == null) ? 0 : dateOrdered.hashCode());
		result = prime * result + ((dateRecieved == null) ? 0 : dateRecieved.hashCode());
		result = prime * result + ((details == null) ? 0 : details.hashCode());
		result = prime * result + orderId;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((updatedDate == null) ? 0 : updatedDate.hashCode());
		result = prime * result + ((updatedStaffId == null) ? 0 : updatedStaffId.hashCode());
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
		OrderEntity other = (OrderEntity) obj;
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
		if (dateOrdered == null) {
			if (other.dateOrdered != null) {
				return false;
			}
		} else if (!dateOrdered.equals(other.dateOrdered)) {
			return false;
		}
		if (dateRecieved == null) {
			if (other.dateRecieved != null) {
				return false;
			}
		} else if (!dateRecieved.equals(other.dateRecieved)) {
			return false;
		}
		if (details == null) {
			if (other.details != null) {
				return false;
			}
		} else if (!details.equals(other.details)) {
			return false;
		}
		if (orderId != other.orderId) {
			return false;
		}
		if (status == null) {
			if (other.status != null) {
				return false;
			}
		} else if (!status.equals(other.status)) {
			return false;
		}
		if (updatedDate == null) {
			if (other.updatedDate != null) {
				return false;
			}
		} else if (!updatedDate.equals(other.updatedDate)) {
			return false;
		}
		if (updatedStaffId == null) {
			if (other.updatedStaffId != null) {
				return false;
			}
		} else if (!updatedStaffId.equals(other.updatedStaffId)) {
			return false;
		}
		return true;
	}

}
