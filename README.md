# coursera
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 3.2 Final//EN">
<html>


<head>
    <link rel="stylesheet" type="text/css" href="../css/assignments.css">
    <link rel="stylesheet" type="text/css" href="https://algs4.cs.princeton.edu/css/link-icons.css">
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.1/MathJax.js?config=TeX-AMS-MML_HTMLorMML"></script>
    <title>Programming Assignment 1: Percolation</title>
</head>

<body class = "specification">


<div id="nav2">
<a href = "specification.php" id="logo"> <img src="logo.png"> Percolation</a>
<ul id="links">
<li><a href="specification.php" id = "here">Spec</a></li>
<li><a href="faq.php">FAQ</a></li>
<li><a href="percolation.zip">Project</a></li>
<li><a href="https://www.coursera.org/learn/algorithms-part1/programming/Lhp5z/percolation/submission" target="_blank">Submit</a></li>
</ul>
</div>


<p>
Write a program to estimate the value of the 
<em>percolation threshold</em>
via Monte Carlo simulation.


</p><p><b>Install our Java programming environment (optional).</b>
Install our custom IntelliJ programming environment by following
these step-by-step instructions for your operating system
[
<a href = "https://lift.cs.princeton.edu/java/mac">Mac OS X</a>
&middot;
<a href = "https://lift.cs.princeton.edu/java/windows">Windows</a>
&middot;
<a href = "https://lift.cs.princeton.edu/java/linux">Linux</a>
]. 

<p>
After following these instructions, the commands <code>javac-algs4</code> and <code>java-algs4</code>
will classpath in <a href = "https://algs4.cs.princeton.edu/code/algs4.jar">algs4.jar</a>,
which contains Java classes for I/O and all of the algorithms in the textbook.
To access a class in <code>algs4.jar</code>,
you need an <code>import</code> statement, such as the ones below:</em>

<blockquote>
<pre>
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
</pre></blockquote>

<p>
Note that <em>your</em> code must be in the <em>default package</em>; if you use a <code>package</code>
statement, the autograder will reject your submission.


<p><b>Percolation.</b>
Given a composite systems comprised of randomly distributed insulating and metallic
materials: what fraction of the materials need to be metallic so that the composite system is an 
electrical conductor? Given a porous landscape with water on the surface (or oil below),
under what conditions will the water be able to drain through to the bottom (or the
oil to gush through to the surface)?
Scientists have defined an abstract process known as <em>percolation</em>
to model such situations.

<p><b>The model.</b>
We model a percolation system using an <em>n</em>-by-<em>n</em> grid of <em>sites</em>.
Each site is either <em>open</em> or <em>blocked</em>.
A <em>full</em> site is an open site
that can be connected to an open site in the top row via a chain of
neighboring (left, right, up, down) open sites.
We say the system <em>percolates</em> if 
there is a full site in the bottom row.
In other words, a system percolates if we fill all open sites
connected to the top row and that process fills some open
site on the bottom row. (For the 
insulating/metallic materials example, the open sites correspond
to metallic materials, so that a system that percolates 
has a metallic path from top to bottom, with full sites conducting.
For the porous substance example, the open sites 
correspond to empty space through which water might 
flow, so that a system that percolates lets water fill open sites, 
flowing from top to bottom.)

<center>
<blockquote>
<img src = "percolates-yes.png" height = 175 align = "top" alt = "percolates">
&nbsp;
&nbsp;
&nbsp;
&nbsp;
&nbsp;
<img src = "percolates-no.png" height = 175 align = "top" alt = "does not percolate">
</blockquote>
</center>

<!--
<p>
<center>
<img src = "percolates.png" alt = "Percolates">
</center>
-->


<p><b>The problem.</b>
In a famous scientific problem, researchers are interested in the
following question: if sites are independently set to be open with
probability <em>p</em> (and therefore blocked with
probability 1 &minus; <em>p</em>), what is the probability that the system percolates?
When <em>p</em> equals 0, the system does not percolate; when <em>p</em> equals 1,
the system percolates.
The plots below show the site vacancy probability <em>p</em> versus the percolation
probability for 20-by-20 random grid (left) and 100-by-100 random grid (right).
<p>

<center>
<img src = "percolation-threshold20.png" height = 175 alt = "Percolation threshold for 20-by-20 grid">
&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;
<img src = "percolation-threshold100.png" height = 175 alt = "Percolation threshold for 100-by-100 grid">
&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;
</center>

<p>
When <em>n</em> is sufficiently large, there is a <em>threshold</em> value <em>p</em>* such
that when <em>p</em> &lt; <em>p</em>* a random <em>n</em>-by-<em>n</em> grid 
almost never percolates, and when <em>p</em> &gt; <em>p</em>*,
a random <em>n</em>-by-<em>n</em> grid almost always percolates.
No mathematical solution for determining the percolation threshold <em>p</em>*
has yet been derived.
Your task is to write a computer program to estimate <em>p</em>*.


<p><b>Percolation data type.</b>
To model a percolation system, create a data type <code>Percolation</code> with the following API:

<blockquote>
<pre class = "api">
public class Percolation {

    <span class = "comment">// creates n-by-n grid, with all sites initially blocked</span>
    public Percolation(int n)

    <span class = "comment">// opens the site (row, col) if it is not open already</span>
    public void open(int row, int col)

    <span class = "comment">// is the site (row, col) open?</span>
    public boolean isOpen(int row, int col)

    <span class = "comment">// is the site (row, col) full?</span>
    public boolean isFull(int row, int col)

    <span class = "comment">// returns the number of open sites</span>
    public int numberOfOpenSites()

    <span class = "comment">// does the system percolate?</span>
    public boolean percolates()

    <span class = "comment">// test client (optional)</span>
    public static void main(String[] args)
}
</pre></blockquote>


<p><em>Corner cases.&nbsp;</em>
By convention, the row and column indices 
are integers between 1 and <em>n</em>, where (1, 1) is the upper-left site:
Throw an <code>IllegalArgumentException</code>
if any argument to <code>open()</code>, <code>isOpen()</code>, or <code>isFull()</code> 
is outside its prescribed range.
Throw an <code>IllegalArgumentException</code> in the constructor if <em>n</em> &le; 0.


<p><em>Performance requirements.&nbsp;</em>
The constructor must take time proportional to <em>n</em><sup>2</sup>;
all instance methods must take constant time plus a constant number of calls to 
<code>union()</code> and <code>find()</code>.


<p><b>Monte Carlo simulation.</b>
To estimate the percolation threshold, consider the following computational experiment:
<ul>

<p><li> Initialize all sites to be blocked.

<p><li> Repeat the following until the system percolates:

<ul>
<p><li> Choose a site uniformly at random among all blocked sites.
<p><li> Open the site.
</ul>

<p><li> The fraction of sites that are opened when the system percolates
provides an estimate of the percolation threshold.
</ul>


<p>
For example, if sites are opened in a 20-by-20 lattice according to the snapshots below,
then our estimate of the percolation threshold is 204/400 = 0.51 because the system
percolates when the 204th site is opened.

<p>
<center>
<TABLE BORDER = 0 CELLPADDING =2 CELLSPACING = 0>
<tr>
<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<td><IMG SRC="percolation-50.png" height = 175 alt = "Percolation 50 sites">
<br><center><font size = -1><em>50 open sites</em></font></center>
<td><IMG SRC="percolation-100.png" height = 175 alt = "Percolation 100 sites">
<br><center><font size = -1><em>100 open sites</em></font></center>
<td><IMG SRC="percolation-150.png" height = 175 alt = "Percolation 150 sites">
<br><center><font size = -1><em>150 open sites</em></font></center>
<td><IMG SRC="percolation-204.png" height = 175 alt = "Percolation 204 sites">
<br><center><font size = -1><em>204 open sites</em></font></center>
<!--
<td><IMG SRC="percolation-250.png" height = 175 alt = "Percolation 250 sites">
<br><center><font size = -1><em>250 open sites</em></font></center>
-->
</tr>
</table>
</center>

<p>
By repeating this computation experiment <em>T</em> times and averaging the results,
we obtain a more accurate estimate of the percolation threshold.
Let <em>x<sub>t</sub></em> be the fraction of open sites in computational experiment <em>t</em>.
The sample mean \(\overline x\) provides an estimate of the percolation threshold;
the sample standard deviation <em>s</em>; measures the sharpness of the threshold.


<p>

<blockquote>
<!-- <img src = "percolation-stats.png" alt = "Estimating the sample mean and variance"> -->

\[
\overline x  = \frac{x_1 \, + \, x_2 \, + \, \cdots \, + \, x_{T}}{T},
\quad s^2  = \frac{(x_1 - \overline x )^2 \, + \, (x_2 - \overline x )^2 \,+\, \cdots \,+\, (x_{T} - \overline x )^2}{T-1}
\]

</blockquote>



Assuming <em>T</em> is sufficiently large (say, at least 30), the following
provides a 95% confidence interval for the percolation threshold:

<p>
<blockquote>
<!-- <img src = "percolation-confidence.png" alt = "95% confidence interval for percolation threshold"> -->

\[ 
\left [ \; \overline x  -  \frac {1.96 s}{\sqrt{T}}, \;\;
           \overline x  +  \frac {1.96 s}{\sqrt{T}} \; \right]
\]
</blockquote>

<p>
To perform a series of computational experiments, create a data type <code>PercolationStats</code>
with the following API.

<blockquote>
<pre class = "api">
public class PercolationStats {

    <span class = "comment">// perform independent trials on an n-by-n grid</span>
    public PercolationStats(int n, int trials)

    <span class = "comment">// sample mean of percolation threshold</span>
    public double mean()

    <span class = "comment">// sample standard deviation of percolation threshold</span>
    public double stddev()

    <span class = "comment">// low endpoint of 95% confidence interval</span>
    public double confidenceLo()

    <span class = "comment">// high endpoint of 95% confidence interval</span>
    public double confidenceHi()

   <span class = "comment">// test client (see below)</span>
   public static void main(String[] args)

}
</pre>
</blockquote>

Throw an <code>IllegalArgumentException</code> in the constructor
if either <em>n</em> &le; 0 or <em>trials</em> &le; 0.
<p>
Also, include a <code>main()</code> method
that takes two <em>command-line arguments</em>
<em>n</em> and <em>T</em>, performs <em>T</em> independent
computational experiments (discussed above) on an <em>n</em>-by-<em>n</em> grid,
and prints the sample mean, sample standard deviation, and the 
<em>95% confidence interval</em> for the percolation threshold.
Use
<a href = "https://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/StdRandom.html"><code>StdRandom</code></a>
to generate random numbers; use 
<a href = "https://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/StdStats.html"><code>StdStats</code></a>
to compute the sample mean and sample standard deviation.

<blockquote>
<pre class = "terminal">
<span class = prompt>~/Desktop/percolation&gt;</span> <span class = command>java-algs4 PercolationStats 200 100</span>
mean                    = 0.5929934999999997
stddev                  = 0.00876990421552567
95% confidence interval = [0.5912745987737567, 0.5947124012262428]

<span class = prompt>~/Desktop/percolation&gt;</span> <span class = command>java-algs4 PercolationStats 200 100</span>
mean                    = 0.592877
stddev                  = 0.009990523717073799
95% confidence interval = [0.5909188573514536, 0.5948351426485464]

<span class = prompt>~/Desktop/percolation&gt;</span> <span class = command>java-algs4 PercolationStats 2 10000</span>
mean                    = 0.666925
stddev                  = 0.11776536521033558
95% confidence interval = [0.6646167988418774, 0.6692332011581226]

<span class = prompt>~/Desktop/percolation&gt;</span> <span class = command>java-algs4 PercolationStats 2 100000</span>
mean                    = 0.6669475
stddev                  = 0.11775205263262094
95% confidence interval = [0.666217665216461, 0.6676773347835391]
</pre>
</blockquote>



<p><b>Analysis of running time and memory usage (optional and not graded).</b>
Implement the <code>Percolation</code> data type using the <em>quick find</em> algorithm in
<a href ="https://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/QuickFindUF.html"><code>QuickFindUF</code></a>.
<ul>
<p><li>
Use <a href ="https://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/Stopwatch.html"><code>Stopwatch</code></a>
to measure the total running time of <code>PercolationStats</code> for various values of
<em>n</em> and <em>T</em>.
How does doubling <em>n</em> affect the total running time?
How does doubling <em>T</em> affect the total running time?
Give a formula (using tilde notation) of the total running
time on your computer (in seconds) as a single function of both
<em>n</em> and <em>T</em>.

<p><li>
Using the 64-bit memory-cost model from lecture,
give the total memory usage in bytes (using tilde notation) that a <code>Percolation</code>
object uses to model an <em>n</em>-by-<em>n</em> percolation system.     
Count all memory that is used, including memory for the union&ndash;find data structure.
</ul>

</ul>

<p>
Now, implement the <code>Percolation</code> data type using the <em>weighted quick union</em> algorithm in
<a href ="https://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/WeightedQuickUnionUF.html"><code>WeightedQuickUnionUF</code></a>.
Answer the questions in the previous paragraph.


<p><b>Web submission.</b>
Submit a .zip file containing only
Submit only <code>Percolation.java</code> (using the weighted quick-union algorithm 
from 
<a href ="https://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/WeightedQuickUnionUF.html"><code>WeightedQuickUnionUF</code></a>)
and <code>PercolationStats.java</code>.
We will supply <code>algs4.jar</code>.
Your submission may not call library functions except those in
<a href ="https://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/StdIn.html"><code>StdIn</code></a>,
<a href ="https://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/StdOut.html"><code>StdOut</code></a>,
<a href ="https://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/StdRandom.html"><code>StdRandom</code></a>,
<a href ="https://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/StdStats.html"><code>StdStats</code></a>,
<a href ="https://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/WeightedQuickUnionUF.html"><code>WeightedQuickUnionUF</code></a>,
and
<code>java.lang</code>.

<p><b>For fun.</b>
Create your own percolation input file and share it in the discussion forums.
For some inspiration, do an image search for “nonogram puzzles solved.”

<p><br>


<ADDRESS><SMALL>
This assignment was developed by Bob Sedgewick and Kevin Wayne.
<br>Copyright &copy; 2008.
</SMALL>
</ADDRESS>

</BODY></HTML>
