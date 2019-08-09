# AlgorithmsPartIPrincetonUniversity
These are some of the assignments from Princeton's Algorithm's course. 

## Percolation ## 

_The model_: We model a percolation system using an n-by-n grid of sites. Each site is either open or blocked. A full site is an open site that can be connected to an open site in the top row via a chain of neighboring (left, right, up, down) open sites. We say the system percolates if there is a full site in the bottom row. In other words, a system percolates if we fill all open sites connected to the top row and that process fills some open site on the bottom row. 
~_From Princeton's assignment specifications_

_Language_: Java

_Getting Started:_ Run Percolation Visualizer with a provided .txt file of your choosing. This will automatically open a window displaying the sites as they are randomly opened, and whether or not the system percolates. To estimate the percolation threshold, run the Monte Carlo simulation file.

_Solving Strategies_: I used a weighted quick union algorithm to efficiently search which nodes were connected. A node was “full” if it connected to the top. Each node in the top row was connected to one virtual top node. Each node in the bottom row was connected to one virtual bottom node. To see if the system percolates, I only have to check if the virtual bottom node is connected to the virtual top node. This is much faster than searching if _any_ of the bottom nodes are connected to _any_ of the top nodes. I have not yet solved the issue of backwash, but the code as-is is fast, memory-efficient, and accurate. 
