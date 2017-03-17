import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import java.util.HashMap;
import java.util.Map;

class PortfolioCalculator {

    private Portfolio portfolio;
    private double bondTransRate;
    private double stockTransRate;
    private double cashTransRate;

    PortfolioCalculator(Portfolio portfolio, double bondTransRate, double stockTransRate,
                        double cashTransCost) {
        this.portfolio = portfolio;
        this.bondTransRate = bondTransRate;
        this.stockTransRate = stockTransRate;
        this.cashTransRate = cashTransCost;
    }

    Double calcBuyOrSellTradesExecutedbyDay(String execDate, BuyOrSell buyOrSell) throws EmptyPortfolioException {
        if(portfolio.getInstruments().size() == 0){
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
        for (Instrument instrument : portfolio.getInstruments().keySet()) {
            if(instrument.getBuyOrSell() == buyOrSell
                    && instrument.getExecutionDate().isEqual(execDateFormatted)) {
                noOfTrades++;
                sum += instrument.getAgreedFx()
                        * instrument.getCurrentPricePerUnit()
                        * portfolio.getInstruments().get(instrument);
            }
        }
        if(noOfTrades == 0){
            return Double.NaN;
        }
        return sum;
    }

    Double calcPortfolioValue() throws EmptyPortfolioException {
        if(portfolio.getInstruments().size() == 0){
            throw new EmptyPortfolioException("Portfolio is Empty");
        }
        double sum = 0;
        for (Instrument instrument : portfolio.getInstruments().keySet()) {
            sum += instrument.getAgreedFx()
                    * instrument.getCurrentPricePerUnit()
                    * portfolio.getInstruments().get(instrument);
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

    private double getSum(Instrument instrument) {
        double transRate = getTransactionRate(instrument.getSecurityType());
        int noOfUnits = portfolio.getInstruments().get(instrument);
        return ((noOfUnits
                * instrument.getAgreedFx()
                * (instrument.getCurrentPricePerUnit() - instrument.getPurchasedPricePerUnit()))
                + (instrument.getTransactionCost(transRate, noOfUnits)));
    }


    /*Returns Double.MinValue if given tradeID not found in the Portfolio*/
    Double calcPnLforTradeID(String tradeID){
        for (Instrument instrument :
                portfolio.getInstruments().keySet()) {
            if (instrument.getInstrumentId().equals(tradeID)) {
                return getSum(instrument);
            }
        }
        return Double.NaN;
    }

    Map<Instrument, Double> calcPnLForAllTradesInPortfolio() throws EmptyPortfolioException {
        if(portfolio.getInstruments().size() == 0){
            throw new EmptyPortfolioException("Portfolio is Empty");
        }
        Map<Instrument, Double> pnlMap = new HashMap<Instrument, Double>();
        for (Instrument instrument :
                portfolio.getInstruments().keySet()) {
            Double value = getSum(instrument);
            pnlMap.put(instrument,value);
        }
        return pnlMap;
    }

    Map<Instrument, Double> calcPnlForDay(String executionDate) throws EmptyPortfolioException {
        if(portfolio.getInstruments().size() == 0){
            throw new EmptyPortfolioException("Portfolio is Empty");
        }
        DateTime pnlDay;
        try {
            pnlDay = DateTime.parse(executionDate, DateTimeFormat.forPattern("dd MMM yyy"));
        }
        catch (Exception e){
            throw new IllegalArgumentException("Given Date is not in the Expected Format dd MMM yyy");
        }
        Map<Instrument, Double> pnlMap = new HashMap<Instrument, Double>();
        for (Instrument instrument :
                portfolio.getInstruments().keySet()) {
            if (instrument.getExecutionDate().isEqual(pnlDay)) {
                Double value = getSum(instrument);
                pnlMap.put(instrument, value);
            }
        }
        return pnlMap;
    }

    Map<Instrument, Double> calcPnLRealisedOrUnRealised(String executionDate, boolean realised)
                                throws EmptyPortfolioException {
        if(portfolio.getInstruments().size() == 0){
            throw new EmptyPortfolioException("Portfolio is Empty");
        }
        DateTime pnlDay;
        try {
             pnlDay = DateTime.parse(executionDate, DateTimeFormat.forPattern("dd MMM yyy"));
        }
        catch (Exception e){
            throw new IllegalArgumentException("Given Date is not in the Expected Format dd MMM yyy");
        }
        Map<Instrument, Double> pnlMap = new HashMap<Instrument, Double>();
        if(realised) {
            for (Instrument instrument :
                    portfolio.getInstruments().keySet()) {
                if (instrument.getExecutionDate().isBefore(pnlDay) || instrument.getExecutionDate().equals(pnlDay) ) {
                    Double value = getSum(instrument);
                    pnlMap.put(instrument, value);
                }
            }
        }
        else {
            for (Instrument instrument :
                    portfolio.getInstruments().keySet()) {
                if (instrument.getExecutionDate().isAfter(pnlDay)) {
                    Double value = getSum(instrument);
                    pnlMap.put(instrument, value);
                }
            }
        }
        return pnlMap;
    }

}
