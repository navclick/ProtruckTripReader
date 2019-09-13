package com.example.naveed.protrucktripreader.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VehicleResponse {
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
    private Value value;

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

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;


    }


    public class Value {

        @SerializedName("Id")
        @Expose
        private Integer id;
        @SerializedName("RegNumber")
        @Expose
        private String regNumber;
        @SerializedName("Type")
        @Expose
        private Integer type;
        @SerializedName("TypeName")
        @Expose
        private Object typeName;
        @SerializedName("Manufacturer")
        @Expose
        private String manufacturer;
        @SerializedName("Model")
        @Expose
        private String model;
        @SerializedName("Capacity")
        @Expose
        private Integer capacity;
        @SerializedName("Unit")
        @Expose
        private Integer unit;
        @SerializedName("UnitName")
        @Expose
        private Object unitName;
        @SerializedName("Status")
        @Expose
        private Integer status;
        @SerializedName("StatusName")
        @Expose
        private Object statusName;
        @SerializedName("CreatedOn")
        @Expose
        private String createdOn;
        @SerializedName("EcomID")
        @Expose
        private Integer ecomID;
        @SerializedName("DeviceID")
        @Expose
        private String deviceID;
        @SerializedName("IsContractorVehicle")
        @Expose
        private Object isContractorVehicle;
        @SerializedName("ContractorId")
        @Expose
        private Object contractorId;
        @SerializedName("ContractorName")
        @Expose
        private Object contractorName;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getRegNumber() {
            return regNumber;
        }

        public void setRegNumber(String regNumber) {
            this.regNumber = regNumber;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Object getTypeName() {
            return typeName;
        }

        public void setTypeName(Object typeName) {
            this.typeName = typeName;
        }

        public String getManufacturer() {
            return manufacturer;
        }

        public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public Integer getCapacity() {
            return capacity;
        }

        public void setCapacity(Integer capacity) {
            this.capacity = capacity;
        }

        public Integer getUnit() {
            return unit;
        }

        public void setUnit(Integer unit) {
            this.unit = unit;
        }

        public Object getUnitName() {
            return unitName;
        }

        public void setUnitName(Object unitName) {
            this.unitName = unitName;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Object getStatusName() {
            return statusName;
        }

        public void setStatusName(Object statusName) {
            this.statusName = statusName;
        }

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }

        public Integer getEcomID() {
            return ecomID;
        }

        public void setEcomID(Integer ecomID) {
            this.ecomID = ecomID;
        }

        public String getDeviceID() {
            return deviceID;
        }

        public void setDeviceID(String deviceID) {
            this.deviceID = deviceID;
        }

        public Object getIsContractorVehicle() {
            return isContractorVehicle;
        }

        public void setIsContractorVehicle(Object isContractorVehicle) {
            this.isContractorVehicle = isContractorVehicle;
        }

        public Object getContractorId() {
            return contractorId;
        }

        public void setContractorId(Object contractorId) {
            this.contractorId = contractorId;
        }

        public Object getContractorName() {
            return contractorName;
        }

        public void setContractorName(Object contractorName) {
            this.contractorName = contractorName;
        }

    }


}
