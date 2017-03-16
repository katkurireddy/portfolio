import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class PortfolioCalculatorTest {
    private static Trade trade1 = new Trade("1ty6",SecurityType.BOND,BuyOrSell.BUY,0.50,"SGP","02 Jan 2016",200,100.25,97.2);
    private static Trade trade2 = new Trade("1yu5",SecurityType.CASH,BuyOrSell.SELL,0.22,"AED","02 Jan 2016",450,150.5, 153.2);
    private static Trade trade3 = new Trade("3ty2",SecurityType.STOCK,BuyOrSell.BUY,0.50,"INR","02 Jan 2016",300,150.25, 178.2);
    private static Trade trade4 = new Trade("7ju2",SecurityType.BOND,BuyOrSell.SELL,1.22,"SAR","02 Jan 2016",450,160.5, 159.09);
    private static Trade trade5 = new Trade("9iu3",SecurityType.CASH,BuyOrSell.BUY,1.00,"USD","15 Jan 2016",200,10.25, 10.34);
    private static Trade trade6 = new Trade("8ek3",SecurityType.STOCK,BuyOrSell.SELL,0.10,"GBP","17 Jan 2016",100,10.00, 10.57);
    private static Trade trade7 = new Trade("0sd4",SecurityType.BOND,BuyOrSell.BUY,0.60,"AUD","22 Jan 2016",20,100.25, 100.43);
    private static Trade trade8 = new Trade("2gh4",SecurityType.STOCK,BuyOrSell.SELL,1.50,"EUR","19 Jan 2016",45,150.5, 124.6);
    private static Trade trade9 = new Trade("1hj5",SecurityType.CASH,BuyOrSell.BUY,2.50,"SGP","12 Jan 2016",2,100.25, 100.00);
    private static Trade trade10 = new Trade("2lp5",SecurityType.BOND,BuyOrSell.SELL,6.22,"JPY","25 Jan 2016",400,150.5, 145.6);
    private static Trade trade11 = new Trade("3lu6",SecurityType.CASH,BuyOrSell.BUY,1.22,"AED","29 Jan 2016",400,150.5, 147.2);
    private static Trade trade12 = new Trade("1kl",SecurityType.STOCK,BuyOrSell.SELL,6.00,"INR","25 Mar 2016",600,120.5, 122.4);
    private static Portfolio portfolio;
    private static PortfolioCalculator portfolioCalc;

    private static double BOND_TRANS_RATE = 0.01;
    private static double STOCK_TRANS_RATE = 5;
    private static double CASH_TRANS_RATE = 0;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void setUp() throws Exception {
        List<Trade> trades = new ArrayList<Trade>();
        trades.add(trade1);
        trades.add(trade2);
        trades.add(trade3);
        trades.add(trade4);
        trades.add(trade5);
        trades.add(trade6);
        trades.add(trade7);
        trades.add(trade8);
        trades.add(trade9);
        trades.add(trade10);
        trades.add(trade11);
        trades.add(trade12);
        portfolio = new Portfolio(trades);
        portfolioCalc = new PortfolioCalculator(portfolio, BOND_TRANS_RATE, STOCK_TRANS_RATE, CASH_TRANS_RATE);
    }

    @Test
    public void calcPortfolioValuewithemptyPortfolioTest() throws EmptyPortfolioException {
        Portfolio port = new Portfolio();
        PortfolioCalculator pCalc = new PortfolioCalculator(port, BOND_TRANS_RATE, STOCK_TRANS_RATE, CASH_TRANS_RATE);
        exception.expect(EmptyPortfolioException.class);
        exception.expectMessage("Portfolio is Empty");
        double actual = pCalc.calcPortfolioValue();
    }

    @Test
    public void calcPortfolioValuewithsingleTradeTest() throws EmptyPortfolioException {
        Portfolio port = new Portfolio();
        Trade trade = new Trade("1ty6",SecurityType.BOND,BuyOrSell.BUY,0.50,"SGP","02 Jan 2016",200,100.25,97.2);
        port.addTradesToPortfolio(trade);
        PortfolioCalculator pCalc = new PortfolioCalculator(port, BOND_TRANS_RATE, STOCK_TRANS_RATE, CASH_TRANS_RATE);
        double actual = pCalc.calcPortfolioValue();
        double expected = 10025.0;
        assertEquals(expected, actual,0);
    }

    @Test
    public void calcPortfolioValuewithMultipleTradesTest() throws EmptyPortfolioException {
        Portfolio port = new Portfolio();
        Trade trade = new Trade("1ty6",SecurityType.BOND,BuyOrSell.BUY,0.50,"SGP","02 Jan 2016",200,100.25,97.2);
        Trade tradeX = new Trade("1yu5",SecurityType.CASH,BuyOrSell.SELL,0.1,"AED","07 Jan 2016",10,100.5, 153.2);
        port.addTradesToPortfolio(trade);
        port.addTradesToPortfolio(tradeX);
        PortfolioCalculator pCalc = new PortfolioCalculator(port, BOND_TRANS_RATE, STOCK_TRANS_RATE, CASH_TRANS_RATE);
        double actual = pCalc.calcPortfolioValue();
        double expected = 10125.5;
        assertEquals(expected, actual,0);
    }

    @Test
    public void calcBuyTradesExecutedbyDayTest() throws EmptyPortfolioException {
        double actual = portfolioCalc.calcBuyOrSellTradesExecutedbyDay("02 Jan 2016", BuyOrSell.BUY);
        double expected = 32562.5;
        assertEquals(expected, actual,0);
    }

    @Test
    public void calcSellTradesExecutedbyDayTest() throws EmptyPortfolioException {
        double actual = portfolioCalc.calcBuyOrSellTradesExecutedbyDay("02 Jan 2016", BuyOrSell.SELL);
        double expected = 103014.0;
        assertEquals(expected, actual,0);
    }

    @Test
    public void calcBuyOrSellTradesExecutedbyAbsentDayTest() throws EmptyPortfolioException {
        double actual = portfolioCalc.calcBuyOrSellTradesExecutedbyDay("31 Jan 2016", BuyOrSell.SELL);
        double expected = Double.NaN;
        assertEquals(expected, actual,0);
    }

    @Test
    public void calcPnlForBondTradeIdTest() {
        double actual = portfolioCalc.calcPnLforTradeID("1ty6");
        double expected = 308;
        assertEquals(expected, actual,0.1);
    }

    @Test
    public void calcPnlForStockTradeIdTest() {
        double actual = portfolioCalc.calcPnLforTradeID("3ty2");
        double expected = -4187.5;
        assertEquals(expected, actual,0.1);
    }

    @Test
    public void calcPnlForCashTradeIdTest() {
        double actual = portfolioCalc.calcPnLforTradeID("1yu5");
        double expected = -267.3;
        assertEquals(expected, actual,0.1);
    }

    @Test
    public void calcPnlForAllTradesRealisedTest() throws EmptyPortfolioException {
        Map<Trade, Double> actual = portfolioCalc.calcPnLRealisedOrUnRealised("19 Jan 2016", true);
        int expectedSize = 8;
        assertEquals(expectedSize, actual.size());
    }

    @Test
    public void calcPnlForAllTradesUnRealisedTest() throws EmptyPortfolioException {
        Map<Trade, Double> actual = portfolioCalc.calcPnLRealisedOrUnRealised("19 Jan 2016", false);
        int expectedSize = 4;
        assertEquals(expectedSize, actual.size());
    }

    @Test
    public void calcPnlForAllTradesIllegalDateTest() throws EmptyPortfolioException {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Given Date is not in the Expected Format dd MMM yyy");
        Map<Trade, Double> actual = portfolioCalc.calcPnLRealisedOrUnRealised("19 Jan 2016 20:00:00", false);
    }
}