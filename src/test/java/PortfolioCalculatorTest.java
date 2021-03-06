import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.util.Map;
import static org.junit.Assert.assertEquals;

public class PortfolioCalculatorTest {
    private static Instrument instrument1 = new Instrument("1ty6", InstrumentType.BOND, HoldingPosition.BUY,0.50,"SGP","02 Jan 2017",100.25,97.2);
    private static Instrument instrument2 = new Instrument("1yu5", InstrumentType.CASH, HoldingPosition.BUY,0.22,"AED","02 Jan 2017",150.5, 153.2);
    private static Instrument instrument3 = new Instrument("3ty2", InstrumentType.STOCK, HoldingPosition.BUY,0.50,"INR","02 Jan 2017",150.25, 178.2);
    private static Instrument instrument4 = new Instrument("7ju2", InstrumentType.BOND, HoldingPosition.BUY,1.22,"SAR","02 Jan 2017",160.5, 159.09);
    private static Instrument instrument5 = new Instrument("9iu3", InstrumentType.CASH, HoldingPosition.BUY,1.00,"USD","15 Jan 2017",10.25, 10.34);
    private static Instrument instrument6 = new Instrument("8ek3", InstrumentType.STOCK, HoldingPosition.BUY,0.10,"GBP","17 Jan 2017",10.00, 10.57);
    private static Instrument instrument7 = new Instrument("0sd4", InstrumentType.BOND, HoldingPosition.BUY,0.60,"AUD","22 Jan 2017",100.25, 100.43);
    private static Instrument instrument8 = new Instrument("2gh4", InstrumentType.STOCK, HoldingPosition.BUY,1.50,"EUR","19 Jan 2017",150.5, 124.6);
    private static Instrument instrument9 = new Instrument("1hj5", InstrumentType.CASH, HoldingPosition.BUY,2.50,"SGP","12 Jan 2017",100.25, 100.00);
    private static Instrument instrument10 = new Instrument("2lp5", InstrumentType.BOND, HoldingPosition.BUY,6.22,"JPY","25 Jan 2017",150.5, 145.6);
    private static Instrument instrument11 = new Instrument("3lu6", InstrumentType.CASH, HoldingPosition.BUY,1.22,"AED","29 Jan 2017",150.5, 147.2);
    private static Instrument instrument12 = new Instrument("1kl", InstrumentType.STOCK, HoldingPosition.BUY,6.00,"INR","25 Mar 2017",120.5, 122.4);
    private static Portfolio portfolio = new Portfolio();
    private static PortfolioCalculator portfolioCalc;

    private static double BOND_TRANS_RATE = 0.01;
    private static double STOCK_TRANS_RATE = 5;
    private static double CASH_TRANS_RATE = 0;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void setUp() throws Exception {

        portfolio.addInstruments(instrument1,200);
        portfolio.addInstruments(instrument2, 450);
        portfolio.addInstruments(instrument3, 300);
        portfolio.addInstruments(instrument4, 450);
        portfolio.addInstruments(instrument5, 200);
        portfolio.addInstruments(instrument6, 100);
        portfolio.addInstruments(instrument7, 20);
        portfolio.addInstruments(instrument8, 45);
        portfolio.addInstruments(instrument9, 2);
        portfolio.addInstruments(instrument10, 400);
        portfolio.addInstruments(instrument11, 400);
        portfolio.addInstruments(instrument12, 600);

        portfolioCalc = new PortfolioCalculator(portfolio, BOND_TRANS_RATE, STOCK_TRANS_RATE, CASH_TRANS_RATE);
    }

    @Test
    public void calcPortfolioValueWithEmptyPortfolioTest() throws EmptyPortfolioException {
        Portfolio port = new Portfolio();
        PortfolioCalculator pCalc = new PortfolioCalculator(port, BOND_TRANS_RATE, STOCK_TRANS_RATE, CASH_TRANS_RATE);
        exception.expect(EmptyPortfolioException.class);
        exception.expectMessage("Portfolio is Empty");
        double actual = pCalc.calcPortfolioValue();
    }

    @Test
    public void calcPortfolioValuewithsingleTradeTest() throws EmptyPortfolioException {
        Portfolio port = new Portfolio();
        Instrument instrument = new Instrument("1ty6", InstrumentType.BOND, HoldingPosition.BUY,0.50,"SGP","02 Jan 2017",100.25,97.2);
        port.addInstruments(instrument,200);
        PortfolioCalculator pCalc = new PortfolioCalculator(port, BOND_TRANS_RATE, STOCK_TRANS_RATE, CASH_TRANS_RATE);
        double actual = pCalc.calcPortfolioValue();
        double expected = 10025.0;
        assertEquals(expected, actual,0);
    }

    @Test
    public void calcPortfolioValueWithMultipleTradesTest() throws EmptyPortfolioException {
        Portfolio port = new Portfolio();
        Instrument instrument = new Instrument("1ty6", InstrumentType.BOND, HoldingPosition.BUY,0.50,"SGP","02 Jan 2017",100.25,97.2);
        Instrument instrumentX = new Instrument("1yu5", InstrumentType.CASH, HoldingPosition.BUY,0.1,"AED","07 Jan 2017",100.5, 153.2);
        port.addInstruments(instrument, 200);
        port.addInstruments(instrumentX, 10);
        PortfolioCalculator pCalc = new PortfolioCalculator(port, BOND_TRANS_RATE, STOCK_TRANS_RATE, CASH_TRANS_RATE);
        double actual = pCalc.calcPortfolioValue();
        double expected = 10125.5;
        assertEquals(expected, actual,0);
    }

    @Test
    public void calcTradesExecutedByDayTest() throws EmptyPortfolioException {
        double actual = portfolioCalc.calcTradesExecutedByDay("02 Jan 2017");
        double expected = 135576.5;
        assertEquals(expected, actual,0);
    }

    @Test
    public void calcTradesExecutedbyAbsentDayTest() throws EmptyPortfolioException {
        double actual = portfolioCalc.calcTradesExecutedByDay("31 Jan 2017");
        double expected = Double.NaN;
        assertEquals(expected, actual,0);
    }

    @Test
    public void calcPnlForBondTradeIdTest() {
        double actual = portfolioCalc.calcPnLForInstrument("1ty6");
        double expected = 308;
        assertEquals(expected, actual,0.1);
    }

    @Test
    public void calcPnlForStockTradeIdTest() {
        double actual = portfolioCalc.calcPnLForInstrument("3ty2");
        double expected = -4187.5;
        assertEquals(expected, actual,0.1);
    }

    @Test
    public void calcPnlForCashTradeIdTest() {
        double actual = portfolioCalc.calcPnLForInstrument("1yu5");
        double expected = -267.3;
        assertEquals(expected, actual,0.1);
    }

    @Test
    public void calcPnlForAllTradesRealisedTest() throws EmptyPortfolioException {
        Map<Instrument, Double> actual = portfolioCalc.calcPnLRealisedOrUnRealised("19 Jan 2017", true);
        int expectedSize = 8;
        assertEquals(expectedSize, actual.size());
    }

    @Test
    public void calcPnlForAllTradesUnRealisedTest() throws EmptyPortfolioException {
        Map<Instrument, Double> actual = portfolioCalc.calcPnLRealisedOrUnRealised("19 Jan 2017", false);
        int expectedSize = 4;
        assertEquals(expectedSize, actual.size());
    }

    @Test
    public void calcPnlForAllTradesIllegalDateTest() throws EmptyPortfolioException {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Given Date is not in the Expected Format dd MMM yyy");
        Map<Instrument, Double> actual = portfolioCalc.calcPnLRealisedOrUnRealised("19 Jan 2017 20:00:00", false);
    }
}
