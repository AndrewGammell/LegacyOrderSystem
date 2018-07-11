package com.sogeti.entity;

import java.util.Date;

public class DetailEntity {
	private int		orderId;
	private int		productId;
	private int		quantity;
	private int		unitPrice;
	private String	createStaffId;
	private Date	createdDate;
	private String	updatedStaffId;
	private Date	updatedDate;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		orderId = orderId;
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

	public String getCreateStaffId() {
		return createStaffId;
	}

	public void setCreateStaffId(String createStaffId) {
		this.createStaffId = createStaffId;
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
				+ ", unitPrice=" + unitPrice + ", createStaffId=" + createStaffId + ", createdDate=" + createdDate
				+ ", updatedStaffId=" + updatedStaffId + ", updatedDate=" + updatedDate + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + orderId;
		result = prime * result + ((createStaffId == null) ? 0 : createStaffId.hashCode());
		result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + productId;
		result = prime * result + quantity;
		result = prime * result + unitPrice;
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
		DetailEntity other = (DetailEntity) obj;
		if (orderId != other.orderId) {
			return false;
		}
		if (createStaffId == null) {
			if (other.createStaffId != null) {
				return false;
			}
		} else if (!createStaffId.equals(other.createStaffId)) {
			return false;
		}
		if (createdDate == null) {
			if (other.createdDate != null) {
				return false;
			}
		} else if (!createdDate.equals(other.createdDate)) {
			return false;
		}
		if (productId != other.productId) {
			return false;
		}
		if (quantity != other.quantity) {
			return false;
		}
		if (unitPrice != other.unitPrice) {
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
