import java.math.BigDecimal;
import java.util.Map;

public class App {

    private static double BOND_TRANS_RATE = 0.01;
    private static double STOCK_TRANS_RATE = 5;
    private static double CASH_TRANS_RATE = 0;

    private static Instrument instrument1 = new Instrument("1ty6",SecurityType.BOND,BuyOrSell.BUY,0.50,"SGP","02 Jan 2016",100.25,97.2);
    private static Instrument instrument2 = new Instrument("1yu5",SecurityType.CASH,BuyOrSell.SELL,0.22,"AED","07 Jan 2016",150.5, 153.2);
    private static Instrument instrument3 = new Instrument("3ty2",SecurityType.STOCK,BuyOrSell.BUY,0.50,"INR","08 Jan 2016",150.25, 178.2);
    private static Instrument instrument4 = new Instrument("1ty6",SecurityType.BOND,BuyOrSell.SELL,1.22,"SAR","09 Jan 2016",160.5, 159.09);
    private static Instrument instrument5 = new Instrument("9iu3",SecurityType.CASH,BuyOrSell.BUY,1.00,"USD","15 Jan 2016",10.25, 10.34);
    private static Instrument instrument6 = new Instrument("8ek3",SecurityType.STOCK,BuyOrSell.SELL,0.10,"GBP","17 Jan 2016",10.00, 10.57);
    private static Instrument instrument7 = new Instrument("0sd4",SecurityType.BOND,BuyOrSell.BUY,0.60,"AUD","22 Jan 2016",100.25, 100.43);
    private static Instrument instrument8 = new Instrument("2gh4",SecurityType.STOCK,BuyOrSell.SELL,1.50,"EUR","19 Jan 2016",150.5, 124.6);
    private static Instrument instrument9 = new Instrument("1hj5",SecurityType.CASH,BuyOrSell.BUY,2.50,"SGP","12 Jan 2016",100.25, 100.00);
    private static Instrument instrument10 = new Instrument("2lp5",SecurityType.BOND,BuyOrSell.SELL,6.22,"JPY","25 Jan 2016",150.5, 145.6);
    private static Instrument instrument11 = new Instrument("3lu6",SecurityType.CASH,BuyOrSell.BUY,1.22,"AED","29 Jan 2016",150.5, 147.2);
    private static Instrument instrument12 = new Instrument("1kl",SecurityType.STOCK,BuyOrSell.SELL,6.00,"INR","25 Mar 2016",120.5, 122.4);

    private static void printMap(Map<Instrument, Double> map) {
        for (Instrument key :
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
        portfolio.addInstruments(instrument1, 100);
        portfolio.addInstruments(instrument2, 20);
        portfolio.addInstruments(instrument3, 38);
        portfolio.addInstruments(instrument4, 2);
        portfolio.addInstruments(instrument5, 334);
        portfolio.addInstruments(instrument6, 100);
        portfolio.addInstruments(instrument7, 68);
        portfolio.addInstruments(instrument8, 200);
        portfolio.addInstruments(instrument9, 150);
        portfolio.addInstruments(instrument10, 10);
        portfolio.addInstruments(instrument11, 20);
        portfolio.addInstruments(instrument12, 30);

        PortfolioCalculator pCalc = new PortfolioCalculator(portfolio, BOND_TRANS_RATE, STOCK_TRANS_RATE, CASH_TRANS_RATE);

        printHeader("Portfolio Value");
        System.out.println(pCalc.calcPortfolioValue());

        printHeader("all Buy Trades Executed on a Given Date");
        System.out.println(pCalc.calcBuyOrSellTradesExecutedbyDay("12 Jan 2016", BuyOrSell.BUY));

        printHeader("all Sell Trades Executed on a Given Date");
        System.out.println(pCalc.calcBuyOrSellTradesExecutedbyDay("07 Jan 2016", BuyOrSell.SELL));

        printHeader("Pnl for Instrument ID");
        System.out.println(pCalc.calcPnlForDay("09 Jan 2016"));

        printHeader("PnL For all Trades");
        Map<Instrument, Double> pnlMap = pCalc.calcPnLForAllTradesInPortfolio();
        printMap(pnlMap);

        printHeader("PnL For Trades executed on a given day");
        Map<Instrument, Double> pnlMapforDay = pCalc.calcPnlForDay("22 Jan 2016");
        printMap(pnlMapforDay);

        printHeader("PnL Realised");
        Map<Instrument, Double> pnlRealised = pCalc.calcPnLRealisedOrUnRealised("16 Mar 2016", true);
        printMap(pnlRealised);

        printHeader("PnL UnRealised");
        Map<Instrument, Double> pnlUnRealised = pCalc.calcPnLRealisedOrUnRealised("16 Mar 2016", false);
        printMap(pnlUnRealised);
    }
}
