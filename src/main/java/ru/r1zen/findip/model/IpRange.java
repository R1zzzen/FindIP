package ru.r1zen.findip.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class IpRange {
    private StringProperty ipRange;
    private StringProperty postalAddress;
    private StringProperty address;

    public IpRange() {
       this("", "", "");
    }

    public IpRange(String ipRange, String postalAddress, String address) {
        this.ipRange = new SimpleStringProperty(ipRange);
        this.postalAddress = new SimpleStringProperty(postalAddress);
        this.address =  new SimpleStringProperty(address);
    }



    public String getIpRange() {
        return ipRange.get();
    }

    public StringProperty ipRangeProperty() {
        return ipRange;
    }

    public String getPostalAddress() {
        return postalAddress.get();
    }

    public StringProperty postalAddressProperty() {
        return postalAddress;
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }
}
