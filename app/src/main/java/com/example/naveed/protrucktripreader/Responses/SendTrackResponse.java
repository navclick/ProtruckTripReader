package com.example.naveed.protrucktripreader.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendTrackResponse {

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
    private Integer value;

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

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

}
