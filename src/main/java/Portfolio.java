import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*Contains the currentPortfolio along with the number of units for each instrument*/
public class Portfolio {

    private Map<Instrument, Integer> currentPortfolio = new HashMap<Instrument, Integer>();

    public Portfolio(Map<Instrument, Integer> currentPortfolio) {
        this.currentPortfolio = currentPortfolio;
    }

    Portfolio() {}

    Map<Instrument, Integer> getCurrentPortfolio() {
        return currentPortfolio;
    }

    Set<Instrument> getInstruments() {return currentPortfolio.keySet();}

    /*If the portfolio already contains the instrument then we increase the units*/
    void addInstruments(Instrument newInstrument, int noOfUnits) {
        boolean shortPosition = false;
       if(newInstrument.getCurrentPosition().equals(HoldingPosition.SELL)) {
           shortPosition = isShortPosition(newInstrument, noOfUnits);
       }
       if(shortPosition) {
           System.out.println("Short Position detected for Instrument " + newInstrument.toString()
                   + " Order Count is " + noOfUnits);
           return;
       }
        if(!currentPortfolio.containsKey(newInstrument)) {
            currentPortfolio.put(newInstrument, noOfUnits);
        }
        else {
           int total = countTotalUnits(newInstrument, noOfUnits);
           currentPortfolio.put(newInstrument, total);
        }
    }

    /*Detecting the short position by checking to see if the portfolio already owns the instrument before selling.
    * No. of units bought should be greater than or equal to the no. of units sold for a particular instrument */
    private boolean isShortPosition(Instrument newInstrument, int noOfUnits) {
        if(! currentPortfolio.containsKey(newInstrument)) {
            return true;
        }
        for (Instrument instrKey:
                currentPortfolio.keySet()) {
            if(instrKey.equals(newInstrument)) {
                if(instrKey.getCurrentPosition().equals(HoldingPosition.BUY)
                        || instrKey.getCurrentPosition().equals(HoldingPosition.NEUTRAL)) {
                    int ownedAssetCnt = currentPortfolio.get(instrKey);
                    if(ownedAssetCnt < noOfUnits) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private int countTotalUnits(Instrument instrument, int noOfUnits) {
        int currentAmount = currentPortfolio.get(instrument);
        int totalUnits;
        if(instrument.getCurrentPosition().equals(HoldingPosition.BUY)) {
            return currentAmount + noOfUnits;
        }
        else  {
            totalUnits = currentAmount - noOfUnits;
        }
        for (Instrument key :
                currentPortfolio.keySet()) {
            if(key.equals(instrument)) {
                if(totalUnits == 0) {
                    key.setCurrentPosition(HoldingPosition.NEUTRAL);
                }
                else {
                    key.setCurrentPosition(HoldingPosition.BUY);
                }
            }
        }
        return totalUnits;
    }

    void removeAllInstruments(){
        currentPortfolio.clear();
    }

    void removeInstruments(Instrument instrument) throws EmptyPortfolioException {
        if(currentPortfolio.size() == 0){
            throw new EmptyPortfolioException("Portfolio is Empty");
        }
        if(currentPortfolio.containsKey(instrument)) {
            currentPortfolio.remove(instrument);
        }
    }
}
