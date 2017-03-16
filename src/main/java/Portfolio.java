import java.util.ArrayList;
import java.util.List;

public class Portfolio {

    private List<Trade> trades = new ArrayList<Trade>();

    public Portfolio(List<Trade> trades) {
        this.trades = trades;
    }

    public Portfolio() {}

    public List<Trade> getTrades() {
        return trades;
    }

    public void addTradesToPortfolio(Trade trade){
        if(! trades.contains(trade)) {
            trades.add(trade);
        }
    }

    public void removeAllTrades(){
        trades.clear();
    }

    public void removeTradesFromPortfolio(Trade trade) throws EmptyPortfolioException {
        if(trades.size() == 0){
            throw new EmptyPortfolioException("Portfolio is Empty");
        }
        if(trades.contains(trade)) {
            trades.remove(trade);
        }
    }
}
