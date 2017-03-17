import java.util.HashMap;
import java.util.Map;

/*Contains the instruments along with the number of units for each instrument ID*/
public class Portfolio {

    private Map<Instrument, Integer> instruments = new HashMap<Instrument, Integer>();

    public Portfolio(Map<Instrument, Integer> instruments) {
        this.instruments = instruments;
    }

    Portfolio() {}

    Map<Instrument, Integer> getInstruments() {
        return instruments;
    }

    /*Detecting the short position by checking to see if the portfolio already owns the instrument before selling.
    * No. of units bought should be greater than or equal to the no. of units sold for a particular instrument */
    private boolean isItShortPosition(String instrumentId, int noOfUnits) {
        for (Instrument instrument :
                instruments.keySet()) {
            if(instrument.getInstrumentId().equals(instrumentId)) {
                if(instrument.getBuyOrSell().equals(BuyOrSell.BUY)) {
                    int ownedAssetCnt = instruments.get(instrument);
                    if(ownedAssetCnt < noOfUnits) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /*If the portfolio already contains the instrument then we increase the units*/
    void addInstruments(Instrument instrument, int noOfUnits) {
        boolean isItShortPosition = false;
       if(instrument.getBuyOrSell().equals(BuyOrSell.SELL)) {
           isItShortPosition = isItShortPosition(instrument.getInstrumentId(), noOfUnits);
       }
       if(isItShortPosition) {
           return;
       }
        if(!instruments.containsKey(instrument)) {
            instruments.put(instrument, noOfUnits);
        }
        else {
           int total = instruments.get(instrument) + noOfUnits;
           instruments.put(instrument, total);
        }
    }

    void removeAllInstruments(){
        instruments.clear();
    }

    void removeInstruments(Instrument instrument) throws EmptyPortfolioException {
        if(instruments.size() == 0){
            throw new EmptyPortfolioException("Portfolio is Empty");
        }
        if(instruments.containsKey(instrument)) {
            instruments.remove(instrument);
        }
    }
}
