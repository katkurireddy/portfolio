import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.assertEquals;

public class PortfolioTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void removeTradesFromEmptyPortfolioTest() throws EmptyPortfolioException {
        Portfolio port = new Portfolio();
        Trade trade =  new Trade("1ty6",SecurityType.BOND,BuyOrSell.BUY,0.50,"SGP","02 Jan 2016",200,100.25,97.2);
        exception.expect(EmptyPortfolioException.class);
        exception.expectMessage("Portfolio is Empty");
        port.removeTradesFromPortfolio(trade);
    }

    @Test
    public void addTradesToEmptyPortfolioTest() {
        Portfolio port = new Portfolio();
        Trade trade =  new Trade("1ty6",SecurityType.BOND,BuyOrSell.BUY,0.50,"SGP","02 Jan 2016",200,100.25,97.2);
        port.addTradesToPortfolio(trade);
        int expectedSize = 1;
        assertEquals(expectedSize, port.getTrades().size());
    }

    @Test
    public void removeTradesFromPortfolioWithSingleTradeTest() throws EmptyPortfolioException {
        Portfolio port = new Portfolio();
        Trade trade =  new Trade("1ty6",SecurityType.BOND,BuyOrSell.BUY,0.50,"SGP","02 Jan 2016",200,100.25,97.2);
        port.addTradesToPortfolio(trade);
        port.removeTradesFromPortfolio(trade);
        int expectedSize = 0;
        assertEquals(expectedSize, port.getTrades().size());
    }

    @Test
    public void addDuplicateTradesToPortfolio() {
        Portfolio port = new Portfolio();
        Trade trade1 =  new Trade("1ty6",SecurityType.BOND,BuyOrSell.BUY,0.50,"SGP","02 Jan 2016",200,100.25,97.2);
        Trade trade2 =  new Trade("1ty6",SecurityType.BOND,BuyOrSell.BUY,0.50,"SGP","02 Jan 2016",200,100.25,97.2);
        port.addTradesToPortfolio(trade1);
        port.addTradesToPortfolio(trade2);
        int expectedSize = 1;
        assertEquals(expectedSize, port.getTrades().size());
    }

    @Test
    public void addSameTradeMultipleTimesToPortfolio() {
        Portfolio port = new Portfolio();
        Trade trade1 =  new Trade("1ty6",SecurityType.BOND,BuyOrSell.BUY,0.50,"SGP","02 Jan 2016",200,100.25,97.2);
        port.addTradesToPortfolio(trade1);
        port.addTradesToPortfolio(trade1);
        port.addTradesToPortfolio(trade1);
        port.addTradesToPortfolio(trade1);
        int expectedSize = 1;
        assertEquals(expectedSize, port.getTrades().size());
    }

    @Test
    public void removeAllTradesFromPortfolio() {
        Portfolio port = new Portfolio();
        Trade trade1 =  new Trade("1ty6",SecurityType.BOND,BuyOrSell.BUY,0.50,"SGP","02 Jan 2016",200,100.25,97.2);
        Trade trade2 = new Trade("1yu5",SecurityType.CASH,BuyOrSell.SELL,0.22,"AED","07 Jan 2016",450,150.5, 153.2);
        Trade trade3 = new Trade("3ty2",SecurityType.STOCK,BuyOrSell.BUY,0.50,"INR","08 Jan 2016",300,150.25, 178.2);
        port.addTradesToPortfolio(trade1);
        port.addTradesToPortfolio(trade2);
        port.addTradesToPortfolio(trade3);
        port.removeAllTrades();
        int expectedSize = 0;
        assertEquals(expectedSize, port.getTrades().size());
    }

}
