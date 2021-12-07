package com.sayildiz.kbe.calculator.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Price {
    BigDecimal gross;
    BigDecimal vat;
    BigDecimal net;

    public Price(BigDecimal gross, BigDecimal vat, BigDecimal net) {
        this.gross = gross;
        this.vat = vat;
        this.net = net;
    }

    public BigDecimal getGross() {
        return gross;
    }

    public void setGross(BigDecimal gross) {
        this.gross = gross;
    }

    public BigDecimal getVat() {
        return vat;
    }

    public void setVat(BigDecimal vat) {
        this.vat = vat;
    }

    public BigDecimal getNet() {
        return net;
    }

    public void setNet(BigDecimal net) {
        this.net = net;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return Objects.equals(gross, price.gross) && Objects.equals(vat, price.vat) && Objects.equals(net, price.net);
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
