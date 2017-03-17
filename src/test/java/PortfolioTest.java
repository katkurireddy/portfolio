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
        Instrument instrument =  new Instrument("1ty6", InstrumentType.BOND, HoldingPosition.BUY,0.50,"SGP","02 Jan 2017",100.25,97.2);
        exception.expect(EmptyPortfolioException.class);
        exception.expectMessage("Portfolio is Empty");
        port.removeInstruments(instrument);
    }

    @Test
    public void addInstrumentsToEmptyPortfolioTest() {
        Portfolio port = new Portfolio();
        Instrument instrument =  new Instrument("1ty6", InstrumentType.BOND, HoldingPosition.BUY,0.50,"SGP","02 Jan 2017",100.25,97.2);
        port.addInstruments(instrument, 200);
        int expectedSize = 1;
        assertEquals(expectedSize, port.getCurrentPortfolio().size());
    }

    @Test
    public void removeInstrumentsFromPortfolioWithSingleTradeTest() throws EmptyPortfolioException {
        Portfolio port = new Portfolio();
        Instrument instrument =  new Instrument("1ty6", InstrumentType.BOND, HoldingPosition.BUY,0.50,"SGP","02 Jan 2017",100.25,97.2);
        port.addInstruments(instrument, 200);
        port.removeInstruments(instrument);
        int expectedSize = 0;
        assertEquals(expectedSize, port.getCurrentPortfolio().size());
    }

    @Test
    public void addDuplicateInstrumentsToPortfolio() {
        Portfolio port = new Portfolio();
        Instrument instrument1 =  new Instrument("1ty6", InstrumentType.BOND, HoldingPosition.BUY,0.50,"SGP","02 Jan 2017",100.25,97.2);
        Instrument instrument2 =  new Instrument("1ty6", InstrumentType.BOND, HoldingPosition.BUY,0.50,"SGP","02 Jan 2017",100.25,97.2);
        port.addInstruments(instrument1, 100);
        port.addInstruments(instrument2, 200);
        int expectedCount = 300;
        assertEquals(expectedCount, port.getCurrentPortfolio().get(instrument1).intValue());
    }

    @Test
    public void removeAllInstrumentsFromPortfolio() {
        Portfolio port = new Portfolio();
        Instrument instrument1 =  new Instrument("1ty6", InstrumentType.BOND, HoldingPosition.BUY,0.50,"SGP","02 Jan 2017",100.25,97.2);
        Instrument instrument2 = new Instrument("1yu5", InstrumentType.CASH, HoldingPosition.SELL,0.22,"AED","07 Jan 2017",150.5, 153.2);
        Instrument instrument3 = new Instrument("3ty2", InstrumentType.STOCK, HoldingPosition.BUY,0.50,"INR","08 Jan 2017",150.25, 178.2);
        port.addInstruments(instrument1, 100);
        port.addInstruments(instrument2, 200);
        port.addInstruments(instrument3, 100);
        port.removeAllInstruments();
        int expectedSize = 0;
        assertEquals(expectedSize, port.getCurrentPortfolio().size());
    }

    @Test
    public void addShortPositionsToPortfolio() {
        Portfolio port = new Portfolio();
        Instrument instrument1 =  new Instrument("1ty6", InstrumentType.BOND, HoldingPosition.SELL,0.50,"SGP","02 Jan 2017",100.25,97.2);
        Instrument instrument2 = new Instrument("1yu5", InstrumentType.CASH, HoldingPosition.SELL,0.22,"AED","07 Jan 2017",150.5, 153.2);
        Instrument instrument3 = new Instrument("3ty2", InstrumentType.STOCK, HoldingPosition.SELL,0.50,"INR","08 Jan 2017",150.25, 178.2);
        port.addInstruments(instrument1, 100);
        port.addInstruments(instrument2, 200);
        port.addInstruments(instrument3, 100);
        int expectedSize = 0;
        assertEquals(expectedSize, port.getCurrentPortfolio().size());
    }

    @Test
    public void addNeutralPositionsToPortfoliotoCheckInstrumentCountTest() {
        Portfolio port = new Portfolio();
        Instrument instrument1 =  new Instrument("1ty6", InstrumentType.BOND, HoldingPosition.BUY,0.50,"SGP","02 Jan 2017",100.25,97.2);
        Instrument instrument2 = new Instrument("1ty6", InstrumentType.BOND, HoldingPosition.SELL,0.50,"SGP","02 Jan 2017",100.25,97.2);
        Instrument instrument3 = new Instrument("1ty6", InstrumentType.BOND, HoldingPosition.SELL,0.50,"SGP","02 Jan 2017",100.25,97.2);
        port.addInstruments(instrument1, 100);
        port.addInstruments(instrument2, 50);
        port.addInstruments(instrument3, 50);
        int expected = 1;
        assertEquals(expected, port.getInstruments().size());
    }

    @Test
    public void addNeutralPositionsToPortfolio() {
        Portfolio port = new Portfolio();
        Instrument instrument1 =  new Instrument("1ty6", InstrumentType.BOND, HoldingPosition.BUY,0.50,"SGP","02 Jan 2017",100.25,97.2);
        Instrument instrument2 = new Instrument("1ty6", InstrumentType.BOND, HoldingPosition.SELL,0.50,"SGP","02 Jan 2017",100.25,97.2);
        Instrument instrument3 = new Instrument("1ty6", InstrumentType.BOND, HoldingPosition.SELL,0.50,"SGP","02 Jan 2017",100.25,97.2);
        port.addInstruments(instrument1, 100);
        port.addInstruments(instrument2, 50);
        port.addInstruments(instrument3, 50);
        HoldingPosition expected = HoldingPosition.NEUTRAL;
        HoldingPosition actual = null;
        for (Instrument ins :
                port.getInstruments()) {
            actual = ins.getCurrentPosition();
        }
        assertEquals(expected, actual);
    }

}
