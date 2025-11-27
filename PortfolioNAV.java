import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class PortfolioNAV {


    public static void main(String[] args) {

        Map<String, Long> portfolio = new HashMap<>();
        portfolio.put("AAPL", 5000L);
        portfolio.put("MSFT", 4000L);
        portfolio.put("GOOGL", 3000L);
        portfolio.put("AMZN", 2500L);
        portfolio.put("NVDA", 6000L);
        portfolio.put("META", 2000L);
        portfolio.put("TSLA", 1500L);
        portfolio.put("UNH", 1000L);
        portfolio.put("JNJ", 2000L);
        portfolio.put("V", 1500L);
        portfolio.put("PG", 3000L);
        portfolio.put("HD", 1000L);
        portfolio.put("MA", 1200L);
        portfolio.put("JPM", 1800L);
        portfolio.put("AVGO", 800L);
        portfolio.put("XOM", 2500L);
        portfolio.put("CVX", 2000L);
        portfolio.put("PFE", 5000L);
        portfolio.put("ABBV", 2200L);


        Map<String, String> prices = new HashMap<>();
        prices.put("AAPL", "277.55");
        prices.put("MSFT", "485.50");
        prices.put("GOOGL", "319.95");
        prices.put("AMZN", "229.16");
        prices.put("NVDA", "180.26");
        prices.put("META", "633.61");
        prices.put("TSLA", "426.58");
        prices.put("UNH", "329.71");
        prices.put("JNJ", "207.56");
        prices.put("V", "333.79");
        prices.put("PG", "148.25");
        prices.put("HD", "355.47");
        prices.put("MA", "544.93");
        prices.put("JPM", "307.64");
        prices.put("AVGO", "397.57");
        prices.put("XOM", "114.77");
        prices.put("CVX", "149.51");
        prices.put("PFE", "25.71");
        prices.put("ABBV", "227.66");

        BigDecimal totalNAV = BigDecimal.ZERO;
        int scale = 2;

        for (Map.Entry<String, Long> entry : portfolio.entrySet()) {
            String ticker = entry.getKey();
            Long shares = entry.getValue();

            String priceStr = prices.get(ticker);
            if (null != priceStr ) {
                BigDecimal price = new BigDecimal(priceStr);
                BigDecimal value = price.multiply(BigDecimal.valueOf(shares));
                totalNAV = totalNAV.add(value);
            }
        }


        totalNAV = totalNAV.setScale(scale, BigDecimal.ROUND_HALF_UP);

        System.out.println("NAV for today is : $" + totalNAV);
    }
}
