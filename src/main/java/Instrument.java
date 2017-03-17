import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class Instrument {
    private String instrumentId;
    private InstrumentType instrumentType;
    private HoldingPosition currentPosition;
    private double fxRate;
    private String currency;
    private DateTime executionDate;
    private double currentPrice;
    private double purchasedPrice;

    Instrument(String instrumentId, InstrumentType instrumentType, HoldingPosition currentPosition,
               Double fxRate, String currency, String executionDate,
               double currentPrice, double purchasedPrice) {
        this.instrumentId = instrumentId;
        this.instrumentType = instrumentType;
        this.currentPosition = currentPosition;
        this.fxRate = fxRate;
        this.currency = currency;
        this.executionDate = DateTime.parse(executionDate, DateTimeFormat.forPattern("dd MMM yyy"));
        this.currentPrice = currentPrice;
        this.purchasedPrice = purchasedPrice;
    }

    String getInstrumentId(){
        return instrumentId;
    }

    InstrumentType getInstrumentType(){
        return instrumentType;
    }

    HoldingPosition getCurrentPosition() {
        return currentPosition;
    }

    Double getFxRate() {
        return fxRate;
    }

    String getCurrency() {
        return currency;
    }

    DateTime getExecutionDate() {
        return executionDate;
    }

    Double getCurrentPrice() {
        return currentPrice;
    }

    Double getPurchasedPrice() {
        return purchasedPrice;
    }

    double getTransactionCost(double transRate, int noOfUnits) {
        if(instrumentType == InstrumentType.BOND) {
            return (this.currentPrice - this.purchasedPrice)
                    * this.fxRate
                    * noOfUnits
                    * transRate;
        }
        return transRate;
    }

    public void setCurrentPosition(HoldingPosition currentPosition) {
        this.currentPosition = currentPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Instrument)) return false;

        Instrument instrument = (Instrument) o;
        if (!getInstrumentId().equals(instrument.getInstrumentId())) return false;
        if (getInstrumentType() != instrument.getInstrumentType()) return false;
        if (!getFxRate().equals(instrument.getFxRate())) return false;
        if (!getCurrency().equals(instrument.getCurrency())) return false;
        if (!getExecutionDate().equals(instrument.getExecutionDate())) return false;
        if (!getCurrentPrice().equals(instrument.getCurrentPrice())) return false;
        return getPurchasedPrice().equals(instrument.getPurchasedPrice());
    }

    @Override
    public String toString() {
        return "Instrument{" +
                "instrumentId='" + instrumentId + '\'' +
                ", instrumentType=" + instrumentType +
                ", currentPosition=" + currentPosition +
                ", executionDate=" + executionDate +
                ", currentPrice=" + currentPrice +
                ", purchasedPrice=" + purchasedPrice +
                '}';
    }

    @Override
    public int hashCode() {
        int result = getInstrumentId().hashCode();
        result = 31 * result + getInstrumentType().hashCode();
        result = 31 * result + getFxRate().hashCode();
        result = 31 * result + getCurrency().hashCode();
        result = 31 * result + getExecutionDate().hashCode();
        result = 31 * result + getCurrentPrice().hashCode();
        result = 31 * result + getPurchasedPrice().hashCode();
        return result;
    }
}

