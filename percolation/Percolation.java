/* *****************************************************************************
 *  Name: H. H.
 *  Date: 2/20/19
 *  Description: Does the input file of indices percolate? Does the water
 *  flow through? Does the electricity conduct?
 *  Input is between 1 and n inclusive
 *  OpenSites are mapped by a 2D array with 0 indexing
 *  WeightedQuickUnion array is n * n + 2 length, for each slot in the 2D array,
 *  plus 2 for a virtual top and virtual bottom
 *     [[0, 1, 2],[3, 4, 5],[6, 7, 8]]
 *      [0, 1, 2,  3, 4, 5,  6, 7, 8, 9, 10]
 *                                    ^  ^
 *                          virtual top  virtual bottom
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] isOpenArr;
    private final WeightedQuickUnionUF parent;
    private final int nSize;
    private final int virtualTop;
    private final int virtualBottom;
    private int openSites;

    public Percolation(int n) {
        // create n-by-n grid, with all sites blocked
        if (n < 1) {
            throw new IllegalArgumentException("Grid size must be at least 1");
        }
        isOpenArr = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                isOpenArr[i][j] = false;
            }
        }
        parent = new WeightedQuickUnionUF(n * n + 2);
        nSize = n;
        virtualTop = n * n;
        virtualBottom = n * n + 1;
    }

    public void open(int row, int col) {
        // open site (row, col) if it is not open already
        if (isValidIndices(row, col)) {
            row--; // subtract 1 for zero indexing
            col--;
            if (!isOpenArr[row][col]) {
                isOpenArr[row][col] = true;
                connectOpenNeighbors(row, col);
                openSites++;
            }
        }
    }

    private boolean isValidIndices(int row, int col) {
        if (row >= 1 && row <= nSize) {
            if (col >= 1 && col <= nSize) return true;
        }
        throw new IllegalArgumentException("Indices (" + row + ", " + col + ") are not "
                                                   + "between 1 and " + nSize);
    }

    private void connectOpenNeighbors(int row, int col) {
        if (col < nSize - 1 && isOpenArr[row][col + 1]) { // is right block open?
            parent.union((row * nSize + col), (row * nSize + col + 1));
        }
        if (col > 0 && isOpenArr[row][col - 1]) { // is left open?
            parent.union((row * nSize + col), (row * nSize + col - 1));
        }
        if (row > 0 && isOpenArr[row - 1][col]) { // is above open?
            parent.union((row * nSize + col), ((row - 1) * nSize + col));
        }
        if (row < nSize - 1 && isOpenArr[row + 1][col]) { // is below open?
            parent.union((row * nSize + col), ((row + 1) * nSize + col));
        }
        // if in top row, connect to virtual top node
        if (row == 0) parent.union((row * nSize + col), virtualTop);
        // if in bottom row, connect to virtual bottom node
        if (row == nSize - 1) parent.union((row * nSize + col), virtualBottom);
    }

    public boolean isOpen(int row, int col) {
        // is site (row, col) open?
        if (isValidIndices(row, col)) {
            // subtract for zero indexing
            return isOpenArr[row - 1][col - 1];
        }
        return false;
    }

    public boolean isFull(int row, int col) {
        // Is site (row, col) full? Does this cell connect to the top?
        if (isValidIndices(row, col) && isOpenArr[row - 1][col - 1]) {
            return parent.connected((((row - 1) * nSize) + (col - 1)), virtualTop);
        }
        return false;
    }

    public int numberOfOpenSites() {
        // Number of open sites
        return openSites;
    }

    public boolean percolates() {
        // Does the system percolate? Does the virtual bottom connect to virtual top?
        return parent.connected(virtualTop, virtualBottom);
    }

    public static void main(String[] args) {
        // test client (optional)
    }
}
