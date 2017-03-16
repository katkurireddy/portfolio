import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import java.util.HashMap;
import java.util.Map;

public class PortfolioCalculator {

    private Portfolio portfolio;
    private double bondTransRate;
    private double stockTransRate;
    private double cashTransRate;

    public PortfolioCalculator(Portfolio portfolio, double bondTransRate, double stockTransRate,
                               double cashTransCost) {
        this.portfolio = portfolio;
        this.bondTransRate = bondTransRate;
        this.stockTransRate = stockTransRate;
        this.cashTransRate = cashTransCost;
    }

    public Double calcBuyOrSellTradesExecutedbyDay(String execDate, BuyOrSell buyOrSell) throws EmptyPortfolioException {
        if(portfolio.getTrades().size() == 0){
            throw new EmptyPortfolioException("Portfolio is Empty");
        }
        DateTime execDateFormatted;
        try {
            execDateFormatted = DateTime.parse(execDate, DateTimeFormat.forPattern("dd MMM yyy"));
        }
        catch (Exception e){
            throw new IllegalArgumentException("Given Date is not in the Expected Format dd MMM yyy");
        }
        double sum = 0.0;
        int noOfTrades = 0;
        for (Trade trade : portfolio.getTrades()) {
            if(trade.getBuyOrSell() == buyOrSell
                    && trade.getExecutionDate().isEqual(execDateFormatted)) {
                noOfTrades++;
                sum += trade.getAgreedFx() * trade.getCurrentPricePerUnit() * trade.getUnits();
            }
        }
        if(noOfTrades == 0){
            return Double.NaN;
        }
        return sum;
    }

    public Double calcPortfolioValue() throws EmptyPortfolioException {
        if(portfolio.getTrades().size() == 0){
            throw new EmptyPortfolioException("Portfolio is Empty");
        }
        double sum = 0;
        for (Trade trade : portfolio.getTrades()) {
            sum += trade.getAgreedFx() * trade.getCurrentPricePerUnit() * trade.getUnits();
        }
        return sum;
    }

    private double getTransactionRate(SecurityType securityType){
        if(securityType == SecurityType.BOND) {
            return bondTransRate;
        }
        else if(securityType == SecurityType.STOCK) {
            return stockTransRate;
        }
        else return cashTransRate;
    }


    /*Returns Double.MinValue if given tradeID not found in the Portfolio*/
    public Double calcPnLforTradeID(String tradeID){
        for (Trade trade :
                portfolio.getTrades()) {
            if (trade.getTradeId().equals(tradeID)) {
                double transRate = getTransactionRate(trade.getSecurityType());
                return (trade.getUnits()
                        * trade.getAgreedFx()
                        * (trade.getCurrentPricePerUnit() - trade.getPurchasedPricePerUnit()))
                        + trade.getTransactionCost(transRate);
            }
        }
        return Double.NaN;
    }

    public Map<Trade, Double> calcPnLForAllTradesInPortfolio() throws EmptyPortfolioException {
        if(portfolio.getTrades().size() == 0){
            throw new EmptyPortfolioException("Portfolio is Empty");
        }
        Map<Trade, Double> pnlMap = new HashMap<Trade, Double>();
        for (Trade trade :
                portfolio.getTrades()) {
            double transRate = getTransactionRate(trade.getSecurityType());
            Double value = (trade.getUnits()
                    * trade.getAgreedFx()
                    * (trade.getCurrentPricePerUnit() - trade.getPurchasedPricePerUnit()))
                    + trade.getTransactionCost(transRate);
            pnlMap.put(trade,value);
        }
        return pnlMap;
    }

    public Map<Trade, Double> calcPnlForDay(String executionDate) throws EmptyPortfolioException {
        if(portfolio.getTrades().size() == 0){
            throw new EmptyPortfolioException("Portfolio is Empty");
        }
        DateTime pnlDay;
        try {
            pnlDay = DateTime.parse(executionDate, DateTimeFormat.forPattern("dd MMM yyy"));
        }
        catch (Exception e){
            throw new IllegalArgumentException("Given Date is not in the Expected Format dd MMM yyy");
        }
        Map<Trade, Double> pnlMap = new HashMap<Trade, Double>();
        for (Trade trade :
                portfolio.getTrades()) {
            if (trade.getExecutionDate().isEqual(pnlDay)) {
                double transRate = getTransactionRate(trade.getSecurityType());
                Double value = (trade.getUnits()
                        * trade.getAgreedFx()
                        * (trade.getCurrentPricePerUnit() - trade.getPurchasedPricePerUnit()))
                        + trade.getTransactionCost(transRate);
                pnlMap.put(trade, value);
            }
        }
        return pnlMap;
    }

    public Map<Trade, Double> calcPnLRealisedOrUnRealised(String executionDate, boolean realised)
                                throws EmptyPortfolioException {
        if(portfolio.getTrades().size() == 0){
            throw new EmptyPortfolioException("Portfolio is Empty");
        }
        DateTime pnlDay;
        try {
             pnlDay = DateTime.parse(executionDate, DateTimeFormat.forPattern("dd MMM yyy"));
        }
        catch (Exception e){
            throw new IllegalArgumentException("Given Date is not in the Expected Format dd MMM yyy");
        }
        Map<Trade, Double> pnlMap = new HashMap<Trade, Double>();
        if(realised) {
            for (Trade trade :
                    portfolio.getTrades()) {
                if (trade.getExecutionDate().isBefore(pnlDay) || trade.getExecutionDate().equals(pnlDay) ) {
                    double transRate = getTransactionRate(trade.getSecurityType());
                    Double value = (trade.getUnits()
                            * trade.getAgreedFx()
                            * (trade.getCurrentPricePerUnit() - trade.getPurchasedPricePerUnit()))
                            + trade.getTransactionCost(transRate);
                    pnlMap.put(trade, value);
                }
            }
        }
        else {
            for (Trade trade :
                    portfolio.getTrades()) {
                if (trade.getExecutionDate().isAfter(pnlDay)) {
                    double transRate = getTransactionRate(trade.getSecurityType());
                    Double value = (trade.getUnits()
                            * trade.getAgreedFx()
                            * (trade.getCurrentPricePerUnit() - trade.getPurchasedPricePerUnit()))
                            + trade.getTransactionCost(transRate);
                    pnlMap.put(trade, value);
                }
            }
        }
        return pnlMap;
    }

}
