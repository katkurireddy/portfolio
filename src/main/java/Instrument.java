import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class Instrument {
    private String instrumentId;
    private SecurityType securityType;
    private BuyOrSell buyOrSell;
    private double agreedFx;
    private String currency;
    private DateTime executionDate;
    private double currentPricePerUnit;
    private double purchasedPricePerUnit;

    Instrument(String instrumentId, SecurityType securityType, BuyOrSell buyOrSell,
               Double agreedFx, String currency, String executionDate,
               double currentPricePerUnit, double purchasedPricePerUnit) {
        this.instrumentId = instrumentId;
        this.securityType = securityType;
        this.buyOrSell = buyOrSell;
        this.agreedFx = agreedFx;
        this.currency = currency;
        this.executionDate = DateTime.parse(executionDate, DateTimeFormat.forPattern("dd MMM yyy"));
        this.currentPricePerUnit = currentPricePerUnit;
        this.purchasedPricePerUnit = purchasedPricePerUnit;
    }

    String getInstrumentId(){
        return instrumentId;
    }

    SecurityType getSecurityType(){
        return securityType;
    }

    BuyOrSell getBuyOrSell() {
        return buyOrSell;
    }

    Double getAgreedFx() {
        return agreedFx;
    }

    String getCurrency() {
        return currency;
    }

    DateTime getExecutionDate() {
        return executionDate;
    }

    Double getCurrentPricePerUnit() {
        return currentPricePerUnit;
    }

    Double getPurchasedPricePerUnit() {
        return purchasedPricePerUnit;
    }

    double getTransactionCost(double transRate, int noOfUnits) {
        if(securityType == SecurityType.BOND) {
            return (this.currentPricePerUnit - this.purchasedPricePerUnit)
                    * this.agreedFx
                    * noOfUnits
                    * transRate;
        }
        return transRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Instrument)) return false;

        Instrument instrument = (Instrument) o;
        if (!getInstrumentId().equals(instrument.getInstrumentId())) return false;
        if (getSecurityType() != instrument.getSecurityType()) return false;
        if (getBuyOrSell() != instrument.getBuyOrSell()) return false;
        if (!getAgreedFx().equals(instrument.getAgreedFx())) return false;
        if (!getCurrency().equals(instrument.getCurrency())) return false;
        if (!getExecutionDate().equals(instrument.getExecutionDate())) return false;
        if (!getCurrentPricePerUnit().equals(instrument.getCurrentPricePerUnit())) return false;
        return getPurchasedPricePerUnit().equals(instrument.getPurchasedPricePerUnit());
    }

    @Override
    public String toString() {
        return "Instrument{" +
                "instrumentId='" + instrumentId + '\'' +
                ", securityType=" + securityType +
                ", buyOrSell=" + buyOrSell +
                ", executionDate=" + executionDate +
                ", currentPricePerUnit=" + currentPricePerUnit +
                ", purchasedPricePerUnit=" + purchasedPricePerUnit +
                '}';
    }

    @Override
    public int hashCode() {
        int result = getInstrumentId().hashCode();
        result = 31 * result + getSecurityType().hashCode();
        result = 31 * result + getBuyOrSell().hashCode();
        result = 31 * result + getAgreedFx().hashCode();
        result = 31 * result + getCurrency().hashCode();
        result = 31 * result + getExecutionDate().hashCode();
        result = 31 * result + getCurrentPricePerUnit().hashCode();
        result = 31 * result + getPurchasedPricePerUnit().hashCode();
        return result;
    }
}

