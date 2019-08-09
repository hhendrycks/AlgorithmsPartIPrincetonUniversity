/* *****************************************************************************
 *  Name: H. H.
 *  Date: 2/27/19
 *  Description: Tests for percolation threshold with an input of
 *               number of trials and size of grid
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double confidence95 = 1.96;
    private final double mean;
    private final double stdev;
    private final double[] results;

    public PercolationStats(int n, int trials) {
        // perform trials independent experiments on an n-by-n grid
        if (n < 1 || trials < 1) {
            throw new IllegalArgumentException("Grid size and trials must be at least 1");
        }
        results = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation percolate = new Percolation(n);
            while (!percolate.percolates()) {
                percolate.open(StdRandom.uniform(n) + 1, StdRandom.uniform(n) + 1);
            }
            results[i] = ((double) percolate.numberOfOpenSites() / (n * n));
        }
        mean = StdStats.mean(results);
        stdev = StdStats.stddev(results);
    }

    public double mean() {
        // sample mean of percolation threshold
        return mean;
    }

    public double stddev() {
        // sample standard deviation of percolation threshold
        return stdev;
    }

    public double confidenceLo() {
        // low  endpoint of 95% confidence interval
        return (mean - ((confidence95 * stdev) / (Math.sqrt(results.length))));
    }

    public double confidenceHi() {
        // high endpoint of 95% confidence interval
        return (mean + ((confidence95 * stdev) / (Math.sqrt(results.length))));
    }

    public static void main(String[] args) {
        // test client (described below)
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats perc = new PercolationStats(n, t);
        System.out.println("mean                    = " + perc.mean);
        System.out.println("stddev                  = " + perc.stdev);
        System.out.println("95% confidence interval = [" + perc.confidenceLo() +
                                   ", " + perc.confidenceHi() + "]");
    }
}
