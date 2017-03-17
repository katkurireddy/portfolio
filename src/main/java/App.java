import java.math.BigDecimal;
import java.util.Map;

public class App {

    private static double BOND_TRANS_RATE = 0.01;
    private static double STOCK_TRANS_RATE = 5;
    private static double CASH_TRANS_RATE = 0;

    private static Instrument instrument1 = new Instrument("1ty6", InstrumentType.BOND, HoldingPosition.BUY,0.50,"SGP","02 Jan 2017",100.25,97.2);
    private static Instrument instrument2 = new Instrument("1ty6", InstrumentType.BOND, HoldingPosition.SELL,0.50,"SGP","02 Jan 2017",100.25, 97.2);
    private static Instrument instrument3 = new Instrument("1ty6", InstrumentType.BOND, HoldingPosition.SELL,0.50,"SGP","02 Jan 2017",100.25, 97.2);
    private static Instrument instrument4 = new Instrument("1ty6", InstrumentType.BOND, HoldingPosition.BUY,0.50,"SGP","02 Jan 2017",100.25, 97.2);
    private static Instrument instrument5 = new Instrument("9iu3", InstrumentType.CASH, HoldingPosition.BUY,1.00,"USD","15 Jan 2017",10.25, 10.34);
    private static Instrument instrument6 = new Instrument("8ek3", InstrumentType.STOCK, HoldingPosition.BUY,0.10,"GBP","17 Jan 2017",10.00, 10.57);
    private static Instrument instrument7 = new Instrument("0sd4", InstrumentType.BOND, HoldingPosition.BUY,0.60,"AUD","22 Jan 2017",100.25, 100.43);
    private static Instrument instrument8 = new Instrument("2gh4", InstrumentType.STOCK, HoldingPosition.BUY,1.50,"EUR","19 Jan 2017",150.5, 124.6);
    private static Instrument instrument9 = new Instrument("9iu3", InstrumentType.CASH, HoldingPosition.SELL,1.00,"USD","15 Jan 2017",10.25, 10.34);
    private static Instrument instrument10 = new Instrument("2gh4", InstrumentType.STOCK, HoldingPosition.SELL,1.50,"EUR","19 Jan 2017",150.5, 124.6);
    private static Instrument instrument11 = new Instrument("3lu6", InstrumentType.CASH, HoldingPosition.BUY,1.22,"AED","29 Jan 2017",150.5, 147.2);
    private static Instrument instrument12 = new Instrument("1kl", InstrumentType.STOCK, HoldingPosition.BUY,6.00,"INR","02 Jan 2017",120.5, 122.4);

    private static void printMap(Map<Instrument, Double> map) {
        for (Instrument key :
                map.keySet()) {
            System.out.println(key.toString() + " Pnl = " + BigDecimal.valueOf(map.get(key)).setScale(2, BigDecimal.ROUND_CEILING));
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
        portfolio.addInstruments(instrument2, 50);
        portfolio.addInstruments(instrument3, 50);
        portfolio.addInstruments(instrument4, 200);
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
        BigDecimal pValue = BigDecimal.valueOf(pCalc.calcPortfolioValue())
                                        .setScale(2, BigDecimal.ROUND_CEILING);
        System.out.println(pValue);

        printHeader("Value of all Trades Executed on 29 Jan 2017");
        BigDecimal pDayVal = BigDecimal.valueOf(pCalc.calcTradesExecutedByDay("29 Jan 2017"))
                                        .setScale(2, BigDecimal.ROUND_CEILING);
        System.out.println(pDayVal);

        printHeader("Pnl for Instrument ID 1ty6");
        BigDecimal pnlForTrade = BigDecimal.valueOf(pCalc.calcPnLForInstrument("1ty6"))
                .setScale(2, BigDecimal.ROUND_CEILING);
        System.out.println(pnlForTrade);

        printHeader("PnL For all Trades");
        Map<Instrument, Double> pnlMap = pCalc.calcPnLForAllTradesInPortfolio();
        printMap(pnlMap);

        printHeader("PnL For Trades executed on 02 Jan 2017");
        Map<Instrument, Double> pnlMapforDay = pCalc.calcPnlForDay("02 Jan 2017");
        printMap(pnlMapforDay);

        printHeader("PnL Realised");
        Map<Instrument, Double> pnlRealised = pCalc.calcPnLRealisedOrUnRealised("16 Mar 2017", true);
        printMap(pnlRealised);

        printHeader("PnL UnRealised");
        Map<Instrument, Double> pnlUnRealised = pCalc.calcPnLRealisedOrUnRealised("16 Mar 2017", false);
        printMap(pnlUnRealised);
    }
}
