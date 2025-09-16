package edu.msoe.swe2721.lab11;

/**
 * This class simply holds the main for the program.
 */
public class Main {
    /**
     * @param args
     *            These are the arguments to the program. arg[0] is the refresh
     *            rate for the program. args[1] - args[n] is a listing of stock
     *            symbols to watch.
     */
    public static void main(String[] args) {
        if (args.length < 4) {
            printUsage();
        } else {
            System.out.println("Start the Market Analyzer.  ");

            int refreshRate = Integer.parseInt(args[1]);
            int runCount = Integer.parseInt(args[2]);

            System.out.println("Stocks will be refreshed every " + refreshRate + " seconds.");
            System.out.println("The program will run " + runCount + " times.");
            System.out.println("The program will monitor the following stocks: ");

            String[] truncArgs = new String[args.length - 3];

            for (int index = 3; index < args.length; index++) {
                truncArgs[index - 3] = args[index];
                System.out.print(truncArgs[index-3] + "\t");
            }
            System.out.println("\nStandby for the first quotes....");

            FCSAPIStockDownloader dl = new FCSAPIStockDownloader();
            dl.setApiKey(args[0]);
            dl.setRefreshRate(refreshRate);
            MarketAnalyzer ma = new MarketAnalyzer(truncArgs, dl, new AudioErrorPlayer());
            ma.setRunCount(runCount);
            Thread t1 = new Thread(dl);
            t1.start();
            ma.setRefreshRate(refreshRate);
            Thread t2 = new Thread(ma);
            t2.start();

            // The following is bad code.  But, it works...  It's getting around a bad architectural decision made in designing multiple threads and the interfaces a few years ago that is not easily fixed in 2020.
            try {
                synchronized (ma) {
                    // Block here, waiting for a signal from the market analyzer.
                    ma.wait();
                }
                // Stop execution of the downloader.
                dl.stopExecution();
                // Interrupt the sleeping thread for the downloader.
                t1.interrupt();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static void printUsage() {
        System.out.println(
                "Usage: MarketAnalyzer <API KEY> <Refresh Rate> <run count> <Symbol 0> <Symbol 1> ... <Symbol n>\n where Symbol are valid stock market symbols for companies on the stock market.");
    }
}
