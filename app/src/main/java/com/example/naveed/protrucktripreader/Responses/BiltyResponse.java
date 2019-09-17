package com.example.naveed.protrucktripreader.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BiltyResponse {

    @SerializedName("Code")
    @Expose
    private Integer code;
    @SerializedName("IsError")
    @Expose
    private Boolean isError;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Value")
    @Expose
    private List<Value> value = null;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Boolean getIsError() {
        return isError;
    }

    public void setIsError(Boolean isError) {
        this.isError = isError;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Value> getValue() {
        return value;
    }

    public void setValue(List<Value> value) {
        this.value = value;
    }

    public class Value {

        @SerializedName("BiltyNo")
        @Expose
        private Integer biltyNo;
        @SerializedName("VehicleId")
        @Expose
        private Integer vehicleId;
        @SerializedName("VehicleReg")
        @Expose
        private String vehicleReg;
        @SerializedName("SenderParty")
        @Expose
        private Integer senderParty;
        @SerializedName("SenderPartyName")
        @Expose
        private Object senderPartyName;
        @SerializedName("PaymentCode")
        @Expose
        private Integer paymentCode;
        @SerializedName("DriverId")
        @Expose
        private Integer driverId;
        @SerializedName("DriverName")
        @Expose
        private String driverName;
        @SerializedName("DOs")
        @Expose
        private Object dOs;
        @SerializedName("Weight")
        @Expose
        private Integer weight;
        @SerializedName("UnitId")
        @Expose
        private Object unitId;
        @SerializedName("Unit")
        @Expose
        private Object unit;
        @SerializedName("Rate")
        @Expose
        private Integer rate;
        @SerializedName("TotalAmount")
        @Expose
        private Integer totalAmount;
        @SerializedName("PaidAmount")
        @Expose
        private Integer paidAmount;
        @SerializedName("RemainingAmount")
        @Expose
        private Integer remainingAmount;
        @SerializedName("Qty")
        @Expose
        private Integer qty;
        @SerializedName("Address")
        @Expose
        private String address;
        @SerializedName("AddressEnglish")
        @Expose
        private String addressEnglish;
        @SerializedName("BiltyDate")
        @Expose
        private String biltyDate;
        @SerializedName("CreateDate")
        @Expose
        private String createDate;
        @SerializedName("Status")
        @Expose
        private Object status;
        @SerializedName("PartyId")
        @Expose
        private Integer partyId;
        @SerializedName("PartyName")
        @Expose
        private String partyName;
        @SerializedName("Contractno")
        @Expose
        private Integer contractno;
        @SerializedName("GoodTypeId")
        @Expose
        private Integer goodTypeId;
        @SerializedName("GoodTypeName")
        @Expose
        private String goodTypeName;

        public Integer getBiltyNo() {
            return biltyNo;
        }

        public void setBiltyNo(Integer biltyNo) {
            this.biltyNo = biltyNo;
        }

        public Integer getVehicleId() {
            return vehicleId;
        }

        public void setVehicleId(Integer vehicleId) {
            this.vehicleId = vehicleId;
        }

        public String getVehicleReg() {
            return vehicleReg;
        }

        public void setVehicleReg(String vehicleReg) {
            this.vehicleReg = vehicleReg;
        }

        public Integer getSenderParty() {
            return senderParty;
        }

        public void setSenderParty(Integer senderParty) {
            this.senderParty = senderParty;
        }

        public Object getSenderPartyName() {
            return senderPartyName;
        }

        public void setSenderPartyName(Object senderPartyName) {
            this.senderPartyName = senderPartyName;
        }

        public Integer getPaymentCode() {
            return paymentCode;
        }

        public void setPaymentCode(Integer paymentCode) {
            this.paymentCode = paymentCode;
        }

        public Integer getDriverId() {
            return driverId;
        }

        public void setDriverId(Integer driverId) {
            this.driverId = driverId;
        }

        public String getDriverName() {
            return driverName;
        }

        public void setDriverName(String driverName) {
            this.driverName = driverName;
        }

        public Object getDOs() {
            return dOs;
        }

        public void setDOs(Object dOs) {
            this.dOs = dOs;
        }

        public Integer getWeight() {
            return weight;
        }

        public void setWeight(Integer weight) {
            this.weight = weight;
        }

        public Object getUnitId() {
            return unitId;
        }

        public void setUnitId(Object unitId) {
            this.unitId = unitId;
        }

        public Object getUnit() {
            return unit;
        }

        public void setUnit(Object unit) {
            this.unit = unit;
        }

        public Integer getRate() {
            return rate;
        }

        public void setRate(Integer rate) {
            this.rate = rate;
        }

        public Integer getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(Integer totalAmount) {
            this.totalAmount = totalAmount;
        }

        public Integer getPaidAmount() {
            return paidAmount;
        }

        public void setPaidAmount(Integer paidAmount) {
            this.paidAmount = paidAmount;
        }

        public Integer getRemainingAmount() {
            return remainingAmount;
        }

        public void setRemainingAmount(Integer remainingAmount) {
            this.remainingAmount = remainingAmount;
        }

        public Integer getQty() {
            return qty;
        }

        public void setQty(Integer qty) {
            this.qty = qty;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAddressEnglish() {
            return addressEnglish;
        }

        public void setAddressEnglish(String addressEnglish) {
            this.addressEnglish = addressEnglish;
        }

        public String getBiltyDate() {
            return biltyDate;
        }

        public void setBiltyDate(String biltyDate) {
            this.biltyDate = biltyDate;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public Integer getPartyId() {
            return partyId;
        }

        public void setPartyId(Integer partyId) {
            this.partyId = partyId;
        }

        public String getPartyName() {
            return partyName;
        }

        public void setPartyName(String partyName) {
            this.partyName = partyName;
        }

        public Integer getContractno() {
            return contractno;
        }

        public void setContractno(Integer contractno) {
            this.contractno = contractno;
        }

        public Integer getGoodTypeId() {
            return goodTypeId;
        }

        public void setGoodTypeId(Integer goodTypeId) {
            this.goodTypeId = goodTypeId;
        }

        public String getGoodTypeName() {
            return goodTypeName;
        }

        public void setGoodTypeName(String goodTypeName) {
            this.goodTypeName = goodTypeName;
        }

    }

}
