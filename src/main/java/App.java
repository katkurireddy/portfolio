import java.math.BigDecimal;
import java.util.Map;

public class App {

    private static double BOND_TRANS_RATE = 0.01;
    private static double STOCK_TRANS_RATE = 5;
    private static double CASH_TRANS_RATE = 0;

    private static Trade trade1 = new Trade("1ty6",SecurityType.BOND,BuyOrSell.BUY,0.50,"SGP","02 Jan 2016",200,100.25,97.2);
    private static Trade trade2 = new Trade("1yu5",SecurityType.CASH,BuyOrSell.SELL,0.22,"AED","07 Jan 2016",450,150.5, 153.2);
    private static Trade trade3 = new Trade("3ty2",SecurityType.STOCK,BuyOrSell.BUY,0.50,"INR","08 Jan 2016",300,150.25, 178.2);
    private static Trade trade4 = new Trade("7ju2",SecurityType.BOND,BuyOrSell.SELL,1.22,"SAR","09 Jan 2016",450,160.5, 159.09);
    private static Trade trade5 = new Trade("9iu3",SecurityType.CASH,BuyOrSell.BUY,1.00,"USD","15 Jan 2016",200,10.25, 10.34);
    private static Trade trade6 = new Trade("8ek3",SecurityType.STOCK,BuyOrSell.SELL,0.10,"GBP","17 Jan 2016",100,10.00, 10.57);
    private static Trade trade7 = new Trade("0sd4",SecurityType.BOND,BuyOrSell.BUY,0.60,"AUD","22 Jan 2016",20,100.25, 100.43);
    private static Trade trade8 = new Trade("2gh4",SecurityType.STOCK,BuyOrSell.SELL,1.50,"EUR","19 Jan 2016",45,150.5, 124.6);
    private static Trade trade9 = new Trade("1hj5",SecurityType.CASH,BuyOrSell.BUY,2.50,"SGP","12 Jan 2016",2,100.25, 100.00);
    private static Trade trade10 = new Trade("2lp5",SecurityType.BOND,BuyOrSell.SELL,6.22,"JPY","25 Jan 2016",400,150.5, 145.6);
    private static Trade trade11 = new Trade("3lu6",SecurityType.CASH,BuyOrSell.BUY,1.22,"AED","29 Jan 2016",400,150.5, 147.2);
    private static Trade trade12 = new Trade("1kl",SecurityType.STOCK,BuyOrSell.SELL,6.00,"INR","25 Mar 2016",600,120.5, 122.4);

    private static void printMap(Map<Trade, Double> map) {
        for (Trade key :
                map.keySet()) {
            System.out.println(key.toString() + " Pnl = " + BigDecimal.valueOf(map.get(key)).setScale(4, BigDecimal.ROUND_CEILING));
        }
    }

    private static void printHeader(String heading){
        System.out.println();
        System.out.println("**************-------------------*******************");
        System.out.println(heading);
        System.out.println("**************-------------------*******************");
    }

    public static void main(String[] args) throws EmptyPortfolioException {

        Portfolio portfolio = new Portfolio();
        portfolio.addTradesToPortfolio(trade1);
        portfolio.addTradesToPortfolio(trade2);
        portfolio.addTradesToPortfolio(trade3);
        portfolio.addTradesToPortfolio(trade4);
        portfolio.addTradesToPortfolio(trade5);
        portfolio.addTradesToPortfolio(trade6);
        portfolio.addTradesToPortfolio(trade7);
        portfolio.addTradesToPortfolio(trade8);
        portfolio.addTradesToPortfolio(trade9);
        portfolio.addTradesToPortfolio(trade10);
        portfolio.addTradesToPortfolio(trade11);
        portfolio.addTradesToPortfolio(trade12);

        PortfolioCalculator pCalc = new PortfolioCalculator(portfolio, BOND_TRANS_RATE, STOCK_TRANS_RATE, CASH_TRANS_RATE);

        printHeader("Portfolio Value");
        System.out.println(pCalc.calcPortfolioValue());

        printHeader("all Buy Trades Executed on a Given Date");
        System.out.println(pCalc.calcBuyOrSellTradesExecutedbyDay("12 Jan 2016", BuyOrSell.BUY));

        printHeader("all Sell Trades Executed on a Given Date");
        System.out.println(pCalc.calcBuyOrSellTradesExecutedbyDay("08 Jan 2016", BuyOrSell.SELL));

        printHeader("Pnl for Trade ID");
        System.out.println(pCalc.calcPnlForDay("09 Jan 2016"));

        printHeader("PnL For all Trades");
        Map<Trade, Double> pnlMap = pCalc.calcPnLForAllTradesInPortfolio();
        printMap(pnlMap);

        printHeader("PnL For Trades executed on a given day");
        Map<Trade, Double> pnlMapforDay = pCalc.calcPnlForDay("22 Jan 2016");
        printMap(pnlMapforDay);

        printHeader("PnL Realised");
        Map<Trade, Double> pnlRealised = pCalc.calcPnLRealisedOrUnRealised("16 Mar 2016", true);
        printMap(pnlRealised);

        printHeader("PnL UnRealised");
        Map<Trade, Double> pnlUnRealised = pCalc.calcPnLRealisedOrUnRealised("16 Mar 2016", false);
        printMap(pnlUnRealised);

    }
}
