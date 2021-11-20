package com.sayildiz.kbe.calculator.model;

import java.util.Objects;

public class Price {
    double gross;
    double vat;
    double net;

    public Price(double gross, double vat, double net) {
        this.gross = gross;
        this.vat = vat;
        this.net = net;
    }

    public double getGross() {
        return gross;
    }

    public void setGross(double gross) {
        this.gross = gross;
    }

    public double getVat() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public double getNet() {
        return net;
    }

    public void setNet(double net) {
        this.net = net;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return Double.compare(price.gross, gross) == 0 && Double.compare(price.vat, vat) == 0 && Double.compare(price.net, net) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(gross, vat, net);
    }

    @Override
    public String toString() {
        return "Price{" +
                "gross=" + gross +
                ", tax=" + vat +
                ", net=" + net +
                '}';
    }

}
