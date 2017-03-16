import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class Trade {
    private String tradeId;
    private SecurityType securityType;
    private BuyOrSell buyOrSell;
    private double agreedFx;
    private String currency;
    private DateTime executionDate;
    private int units;
    private double currentPricePerUnit;
    private double purchasedPricePerUnit;

    public Trade(String tradeId, SecurityType securityType, BuyOrSell buyOrSell, Double agreedFx, String currency, String executionDate,
                 int units, double currentPricePerUnit, double purchasedPricePerUnit) {
        this.tradeId = tradeId;
        this.securityType = securityType;
        this.buyOrSell = buyOrSell;
        this.agreedFx = agreedFx;
        this.currency = currency;
        this.executionDate = DateTime.parse(executionDate, DateTimeFormat.forPattern("dd MMM yyy"));
        this.units = units;
        this.currentPricePerUnit = currentPricePerUnit;
        this.purchasedPricePerUnit = purchasedPricePerUnit;
    }

    public String getTradeId(){
        return tradeId;
    }

    public SecurityType getSecurityType(){
        return securityType;
    }

    public BuyOrSell getBuyOrSell() {
        return buyOrSell;
    }

    public Double getAgreedFx() {
        return agreedFx;
    }

    public String getCurrency() {
        return currency;
    }

    public DateTime getExecutionDate() {
        return executionDate;
    }

    public int getUnits() {
        return units;
    }

    public Double getCurrentPricePerUnit() {
        return currentPricePerUnit;
    }

    public Double getPurchasedPricePerUnit() {
        return purchasedPricePerUnit;
    }

    public double getTransactionCost(double transRate) {
        if(securityType == SecurityType.BOND) {
            return (this.currentPricePerUnit - this.purchasedPricePerUnit)
                    * this.units
                    *this.agreedFx
                    * transRate;
        }
        return transRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trade)) return false;

        Trade trade = (Trade) o;

        if (getUnits() != trade.getUnits()) return false;
        if (!getTradeId().equals(trade.getTradeId())) return false;
        if (getSecurityType() != trade.getSecurityType()) return false;
        if (getBuyOrSell() != trade.getBuyOrSell()) return false;
        if (!getAgreedFx().equals(trade.getAgreedFx())) return false;
        if (!getCurrency().equals(trade.getCurrency())) return false;
        if (!getExecutionDate().equals(trade.getExecutionDate())) return false;
        if (!getCurrentPricePerUnit().equals(trade.getCurrentPricePerUnit())) return false;
        return getPurchasedPricePerUnit().equals(trade.getPurchasedPricePerUnit());
    }

    @Override
    public String toString() {
        return "Trade{" +
                "tradeId='" + tradeId + '\'' +
                ", securityType=" + securityType +
                ", buyOrSell=" + buyOrSell +
                ", executionDate=" + executionDate +
                ", currentPricePerUnit=" + currentPricePerUnit +
                ", purchasedPricePerUnit=" + purchasedPricePerUnit +
                '}';
    }

    @Override
    public int hashCode() {
        int result = getTradeId().hashCode();
        result = 31 * result + getSecurityType().hashCode();
        result = 31 * result + getBuyOrSell().hashCode();
        result = 31 * result + getAgreedFx().hashCode();
        result = 31 * result + getCurrency().hashCode();
        result = 31 * result + getExecutionDate().hashCode();
        result = 31 * result + getUnits();
        result = 31 * result + getCurrentPricePerUnit().hashCode();
        result = 31 * result + getPurchasedPricePerUnit().hashCode();
        return result;
    }
}

