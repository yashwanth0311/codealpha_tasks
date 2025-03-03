import java.util.*;
class Stock {
    private String symbol;
    private double price;
    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }
    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public void updatePrice() {
        this.price += (Math.random() - 0.5) * 10;
        if (this.price < 1) this.price = 1;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
class Market {
    private Map<String, Stock> stocks = new HashMap<>();
    public void addStock(String symbol, double price) {
        stocks.put(symbol, new Stock(symbol, price));
    }
    public Stock getStock(String symbol) {
        return stocks.get(symbol);
    }
    public void updateMarketPrices() {
        for (Stock stock : stocks.values()) {
            stock.updatePrice();
        }
    }
    public void displayMarket() {
        System.out.println("\nMarket Prices:");
        for (Stock stock : stocks.values()) {
            System.out.println(stock.getSymbol() + ": $" + String.format("%.2f", stock.getPrice()));
        }
    }

    public void setStocks(Map<String, Stock> stocks) {
        this.stocks = stocks;
    }
}
class Portfolio {
    private Map<String, Integer> holdings = new HashMap<>();
    private double balance;

    public Portfolio(double initialBalance) {
        this.balance = initialBalance;
    }

    public void buyStock(Stock stock, int quantity) {
        double cost = stock.getPrice() * quantity;
        if (balance >= cost) {
            holdings.put(stock.getSymbol(), holdings.getOrDefault(stock.getSymbol(), 0) + quantity);
            balance -= cost;
            System.out.println("Bought " + quantity + " shares of " + stock.getSymbol());
        } else {
            System.out.println("Insufficient funds!");
        }
    }
    public void sellStock(Stock stock, int quantity) {
        String symbol = stock.getSymbol();
        if (holdings.getOrDefault(symbol, 0) >= quantity) {
            holdings.put(symbol, holdings.get(symbol) - quantity);
            balance += stock.getPrice() * quantity;
            System.out.println("Sold " + quantity + " shares of " + symbol);
        } else {
            System.out.println("Not enough shares to sell!");
        }
    }
    public void displayPortfolio(Market market) {
        System.out.println("\nPortfolio:");
        for (String symbol : holdings.keySet()) {
            int quantity = holdings.get(symbol);
            double currentPrice = market.getStock(symbol).getPrice();
            System.out.println(symbol + " - Shares: " + quantity + ", Value: $" + String.format("%.2f", quantity * currentPrice));
        }
        System.out.println("Cash Balance: $" + String.format("%.2f", balance));
    }

    public void setHoldings(Map<String, Integer> holdings) {
        this.holdings = holdings;
    }
}
public class StockTradingPlatform1 {
    @SuppressWarnings("ConvertToTryWithResources")
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Market market = new Market();
        market.addStock("AAPL", 150);
        market.addStock("GOOGL", 280);
        market.addStock("TSLA", 170);
        Portfolio portfolio = new Portfolio(50000);

        OUTER:
        while (true) {
            market.updateMarketPrices();
            market.displayMarket();
            portfolio.displayPortfolio(market);
            System.out.println("\n1. Buy Stock");
            System.out.println("2. Sell Stock");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 ->                     {
                        System.out.print("Enter stock symbol: ");
                        String symbol = scanner.next().toUpperCase();
                        System.out.print("Enter quantity: ");
                        int quantity = scanner.nextInt();
                        Stock stock = market.getStock(symbol);
                        if (stock != null) {
                            portfolio.buyStock(stock, quantity);
                        } else {
                            System.out.println("Invalid stock symbol!");
                        }                          }
                case 2 ->                     {
                        System.out.print("Enter stock symbol: ");
                        String symbol = scanner.next().toUpperCase();
                        System.out.print("Enter quantity: ");
                        int quantity = scanner.nextInt();
                        Stock stock = market.getStock(symbol);
                        if (stock != null) {
                            portfolio.sellStock(stock, quantity);
                        } else {
                            System.out.println("Invalid stock symbol!");
                        }                          }
                case 3 -> {
                    System.out.println("Exiting...");
                    break OUTER;
                }
                default -> System.out.println("Invalid choice! Try again.");
            }
        }
        scanner.close();
    }
}