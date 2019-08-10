# AlgorithmsPartIPrincetonUniversity
These are assignments from Princeton's Algorithms course. 

## Percolation ## 

![Percolated vs non-percolated](percolation/percolation.png?raw=true)

_The model_: We model a percolation system using an n-by-n grid of sites. Each site is either open or blocked. A full site is an open site that can be connected to an open site in the top row via a chain of neighboring (left, right, up, down) open sites. We say the system percolates if there is a full site in the bottom row. In other words, a system percolates if we fill all open sites connected to the top row and that process fills some open site on the bottom row. 
~_From Princeton's assignment specifications_

_Language_: Java

_Getting Started_: Run Percolation Visualizer with a provided .txt file of your choosing. This will automatically open a window displaying the sites as they are randomly opened, and whether or not the system percolates. To estimate the percolation threshold, run the Monte Carlo simulation file.

_Solving Strategies_: I used a weighted quick union algorithm to efficiently search which nodes were connected (O(logN)). A node was “full” if it connected to the top. Each node in the top row was connected to one virtual top node. Each node in the bottom row was connected to one virtual bottom node. To see if the system percolates, I only have to check if the virtual bottom node is connected to the virtual top node. This is much faster than searching if _any_ of the bottom nodes are connected to _any_ of the top nodes. I have not yet solved the issue of backwash, but the code as-is is fast, memory-efficient, and accurate. 

## Collinear ## 

![Collinear points](collinear/collinear.png?raw=true)

_The Problem_: Given a set of n distinct points in the plane, find every (maximal) line segment that connects a subset of 4 or more of the points.

_Language_: Java

_Getting Started_: Run fastcollinear with any provided .txt file. This will automatically open the display of found lines based on the input of points.

_Solving Strategies_: 
* I made a point class which allowed me to compare points and calculate slopes. 
* I made a brute force solution for lines containing four points (not larger as it would be massively slow). 
* For a fast search of maximal line segments, I utilized merge sort for its stable sorting property. 
  * When iterating through coordinates, I sorted the other points based on their slope in relation to the current point. When scanning through the rest of the list, I note if the slopes are the same. 
  * As soon as the slopes differ, I add the start and previous point to my list of candidate line segments (if there were more than 3 points of shared slopes). 
  * After completing all scans and iterations, I check for duplicates and only add one of each maximal line segment by sorting my found segments by point values. 
