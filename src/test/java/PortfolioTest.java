import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.assertEquals;

public class PortfolioTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void removeInstrumentsFromEmptyPortfolioTest() throws EmptyPortfolioException {
        Portfolio port = new Portfolio();
        Instrument instrument =  new Instrument("1ty6",SecurityType.BOND,BuyOrSell.BUY,0.50,"SGP","02 Jan 2016",100.25,97.2);
        exception.expect(EmptyPortfolioException.class);
        exception.expectMessage("Portfolio is Empty");
        port.removeInstruments(instrument);
    }

    @Test
    public void addInstrumentsToEmptyPortfolioTest() {
        Portfolio port = new Portfolio();
        Instrument instrument =  new Instrument("1ty6",SecurityType.BOND,BuyOrSell.BUY,0.50,"SGP","02 Jan 2016",100.25,97.2);
        port.addInstruments(instrument, 200);
        int expectedSize = 1;
        assertEquals(expectedSize, port.getInstruments().size());
    }

    @Test
    public void removeInstrumentsFromPortfolioWithSingleTradeTest() throws EmptyPortfolioException {
        Portfolio port = new Portfolio();
        Instrument instrument =  new Instrument("1ty6",SecurityType.BOND,BuyOrSell.BUY,0.50,"SGP","02 Jan 2016",100.25,97.2);
        port.addInstruments(instrument, 200);
        port.removeInstruments(instrument);
        int expectedSize = 0;
        assertEquals(expectedSize, port.getInstruments().size());
    }

    @Test
    public void addDuplicateInstrumentsToPortfolio() {
        Portfolio port = new Portfolio();
        Instrument instrument1 =  new Instrument("1ty6",SecurityType.BOND,BuyOrSell.BUY,0.50,"SGP","02 Jan 2016",100.25,97.2);
        Instrument instrument2 =  new Instrument("1ty6",SecurityType.BOND,BuyOrSell.BUY,0.50,"SGP","02 Jan 2016",100.25,97.2);
        port.addInstruments(instrument1, 100);
        port.addInstruments(instrument2, 200);
        int expectedCount = 300;
        assertEquals(expectedCount, port.getInstruments().get(instrument1).intValue());
    }

    @Test
    public void removeAllInstrumentsFromPortfolio() {
        Portfolio port = new Portfolio();
        Instrument instrument1 =  new Instrument("1ty6",SecurityType.BOND,BuyOrSell.BUY,0.50,"SGP","02 Jan 2016",100.25,97.2);
        Instrument instrument2 = new Instrument("1yu5",SecurityType.CASH,BuyOrSell.SELL,0.22,"AED","07 Jan 2016",150.5, 153.2);
        Instrument instrument3 = new Instrument("3ty2",SecurityType.STOCK,BuyOrSell.BUY,0.50,"INR","08 Jan 2016",150.25, 178.2);
        port.addInstruments(instrument1, 100);
        port.addInstruments(instrument2, 200);
        port.addInstruments(instrument3, 100);
        port.removeAllInstruments();
        int expectedSize = 0;
        assertEquals(expectedSize, port.getInstruments().size());
    }

}
