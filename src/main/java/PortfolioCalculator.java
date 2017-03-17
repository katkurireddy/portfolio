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

    /*Returns Double.NaN if No Trades found in the Portfolio*/
    Double calcTradesExecutedByDay(String execDate) throws EmptyPortfolioException {
        if(portfolio.getCurrentPortfolio().size() == 0){
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
        for (Instrument instrument : portfolio.getCurrentPortfolio().keySet()) {
            if(instrument.getExecutionDate().isEqual(execDateFormatted)) {
                noOfTrades++;
                sum += instrument.getFxRate()
                        * instrument.getCurrentPrice()
                        * portfolio.getCurrentPortfolio().get(instrument);
            }
        }
        if(noOfTrades == 0){
            return Double.NaN;
        }
        return sum;
    }

    Double calcPortfolioValue() throws EmptyPortfolioException {
        if(portfolio.getCurrentPortfolio().size() == 0){
            throw new EmptyPortfolioException("Portfolio is Empty");
        }
        double sum = 0;
        for (Instrument instrKey : portfolio.getCurrentPortfolio().keySet()) {
            int noOfUnits = portfolio.getCurrentPortfolio().get(instrKey);
            sum += instrKey.getFxRate()
                    * instrKey.getCurrentPrice()
                    * noOfUnits;
        }
        return sum;
    }

    private double getTransactionRate(InstrumentType instrumentType){
        if(instrumentType == InstrumentType.BOND) {
            return bondTransRate;
        }
        else if(instrumentType == InstrumentType.STOCK) {
            return stockTransRate;
        }
        else return cashTransRate;
    }

    private double getSum(Instrument instrument) {
        double transRate = getTransactionRate(instrument.getInstrumentType());
        int noOfUnits = portfolio.getCurrentPortfolio().get(instrument);
        return ((noOfUnits
                * instrument.getFxRate()
                * (instrument.getCurrentPrice() - instrument.getPurchasedPrice()))
                + (instrument.getTransactionCost(transRate, noOfUnits)));
    }


    /*Returns Double.NaN if given tradeID not found in the Portfolio*/
    Double calcPnLForInstrument(String instrumentId){
        for (Instrument instrument :
                portfolio.getCurrentPortfolio().keySet()) {
            if (instrument.getInstrumentId().equals(instrumentId)) {
                return getSum(instrument);
            }
        }
        return Double.NaN;
    }

    Map<Instrument, Double> calcPnLForAllTradesInPortfolio() throws EmptyPortfolioException {
        if(portfolio.getCurrentPortfolio().size() == 0){
            throw new EmptyPortfolioException("Portfolio is Empty");
        }
        Map<Instrument, Double> pnlMap = new HashMap<Instrument, Double>();
        for (Instrument instrument :
                portfolio.getCurrentPortfolio().keySet()) {
            Double value = getSum(instrument);
            pnlMap.put(instrument,value);
        }
        return pnlMap;
    }

    Map<Instrument, Double> calcPnlForDay(String executionDate) throws EmptyPortfolioException {
        if(portfolio.getCurrentPortfolio().size() == 0){
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
                portfolio.getCurrentPortfolio().keySet()) {
            if (instrument.getExecutionDate().isEqual(pnlDay)) {
                Double value = getSum(instrument);
                pnlMap.put(instrument, value);
            }
        }
        return pnlMap;
    }

    Map<Instrument, Double> calcPnLRealisedOrUnRealised(String executionDate, boolean realised)
                                throws EmptyPortfolioException {
        if(portfolio.getCurrentPortfolio().size() == 0){
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
                    portfolio.getCurrentPortfolio().keySet()) {
                if (instrument.getExecutionDate().isBefore(pnlDay) || instrument.getExecutionDate().equals(pnlDay) ) {
                    Double value = getSum(instrument);
                    pnlMap.put(instrument, value);
                }
            }
        }
        else {
            for (Instrument instrument :
                    portfolio.getCurrentPortfolio().keySet()) {
                if (instrument.getExecutionDate().isAfter(pnlDay)) {
                    Double value = getSum(instrument);
                    pnlMap.put(instrument, value);
                }
            }
        }
        return pnlMap;
    }

}
