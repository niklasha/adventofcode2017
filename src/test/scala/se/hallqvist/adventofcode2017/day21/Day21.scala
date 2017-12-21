package se.hallqvist.adventofcode2017.day21

import org.scalatest._
import scala.collection._

/*
--- Day 21: Fractal Art ---
You find a program trying to generate some art. It uses a strange process that involves repeatedly enhancing the detail of an image through a set of rules.

The image consists of a two-dimensional square grid of pixels that are either on (#) or off (.). The program always begins with this pattern:

.#.
..#
###
Because the pattern is both 3 pixels wide and 3 pixels tall, it is said to have a size of 3.

Then, the program repeats the following process:

If the size is evenly divisible by 2, break the pixels up into 2x2 squares, and convert each 2x2 square into a 3x3 square by following the corresponding enhancement rule.
Otherwise, the size is evenly divisible by 3; break the pixels up into 3x3 squares, and convert each 3x3 square into a 4x4 square by following the corresponding enhancement rule.
Because each square of pixels is replaced by a larger one, the image gains pixels and so its size increases.

The artist's book of enhancement rules is nearby (your puzzle input); however, it seems to be missing rules. The artist explains that sometimes, one must rotate or flip the input pattern to find a match. (Never rotate or flip the output pattern, though.) Each pattern is written concisely: rows are listed as single units, ordered top-down, and separated by slashes. For example, the following rules correspond to the adjacent patterns:

../.#  =  ..
          .#

                .#.
.#./..#/###  =  ..#
                ###

                        #..#
#..#/..../#..#/.##.  =  ....
                        #..#
                        .##.
When searching for a rule to use, rotate and flip the pattern as necessary. For example, all of the following patterns match the same rule:

.#.   .#.   #..   ###
..#   #..   #.#   ..#
###   ###   ##.   .#.
Suppose the book contained the following two rules:

../.# => ##./#../...
.#./..#/### => #..#/..../..../#..#
As before, the program begins with this pattern:

.#.
..#
###
The size of the grid (3) is not divisible by 2, but it is divisible by 3. It divides evenly into a single square; the square matches the second rule, which produces:

#..#
....
....
#..#
The size of this enhanced grid (4) is evenly divisible by 2, so that rule is used. It divides evenly into four squares:

#.|.#
..|..
--+--
..|..
#.|.#
Each of these squares matches the same rule (../.# => ##./#../...), three of which require some flipping and rotation to line up with the rule. The output for the rule is the same in all four cases:

##.|##.
#..|#..
...|...
---+---
##.|##.
#..|#..
...|...
Finally, the squares are joined into a new grid:

##.##.
#..#..
......
##.##.
#..#..
......
Thus, after 2 iterations, the grid contains 12 pixels that are on.

How many pixels stay on after 5 iterations?

Your puzzle answer was 190.

--- Part Two ---
How many pixels stay on after 18 iterations?
*/

class Day21 extends FlatSpec {
  val input = """
../.. => #.#/###/#.#
#./.. => ..#/.../###
##/.. => .../.##/###
.#/#. => #.#/##./.#.
##/#. => #../#.#/..#
##/## => #.#/#../###
.../.../... => .###/..##/.#../###.
#../.../... => ##.#/.###/#.../##.#
.#./.../... => ..../#.##/..../.#.#
##./.../... => ..#./#.../#.../.###
#.#/.../... => ..##/####/#.#./..##
###/.../... => .##./#.#./###./.#..
.#./#../... => #..#/..#./...#/#.#.
##./#../... => ##../.##./##.#/#..#
..#/#../... => #.##/.##./##.#/.###
#.#/#../... => ...#/#.##/..#./##..
.##/#../... => #.#./..#./##.#/.#.#
###/#../... => #..#/...#/..#./##.#
.../.#./... => .#.#/#.../.##./.#.#
#../.#./... => #.#./.##./..../.#.#
.#./.#./... => .#../#.##/..##/..##
##./.#./... => ..##/##.#/...#/..#.
#.#/.#./... => ...#/.##./####/.#..
###/.#./... => ###./####/...#/####
.#./##./... => ...#/.#.#/#.#./#.#.
##./##./... => ..../...#/#.#./...#
..#/##./... => .##./#.../##.#/.#..
#.#/##./... => .#../.#../...#/....
.##/##./... => ..#./.##./####/.#..
###/##./... => ##../.#.#/##../.#..
.../#.#/... => ..#./.#../.#../.###
#../#.#/... => ####/..../#..#/#...
.#./#.#/... => #.##/##../##.#/##.#
##./#.#/... => ###./..../#.##/###.
#.#/#.#/... => ###./.#../#.#./#.#.
###/#.#/... => ...#/#..#/#.#./..##
.../###/... => .#../...#/...#/....
#../###/... => ####/#.../##../.#.#
.#./###/... => ...#/####/.##./#..#
##./###/... => .###/##.#/..#./.#..
#.#/###/... => ####/###./.###/.###
###/###/... => .#.#/..##/..#./##..
..#/.../#.. => #.../.###/###./...#
#.#/.../#.. => ..../#.../##../..#.
.##/.../#.. => ####/####/...#/####
###/.../#.. => #.../.#../#.#./#.#.
.##/#../#.. => ##../..#./.#../##.#
###/#../#.. => ..../#..#/.###/.###
..#/.#./#.. => ...#/##../.##./##..
#.#/.#./#.. => #.##/.###/#.#./##.#
.##/.#./#.. => ..../..../.#.#/#..#
###/.#./#.. => ##../.#.#/.#.#/####
.##/##./#.. => #.##/##.#/####/....
###/##./#.. => ..../..##/#.#./.###
#../..#/#.. => #.#./...#/#.##/.###
.#./..#/#.. => ####/#.##/.#../.###
##./..#/#.. => .##./..#./.#.#/##.#
#.#/..#/#.. => .#.#/#.##/##../#...
.##/..#/#.. => ..../.###/####/.#..
###/..#/#.. => ##.#/##.#/..##/.#..
#../#.#/#.. => #.##/###./##../....
.#./#.#/#.. => ..../###./####/###.
##./#.#/#.. => ##.#/#.##/##../#.##
..#/#.#/#.. => .###/#.../.#../##..
#.#/#.#/#.. => ##../##.#/#.../.##.
.##/#.#/#.. => ...#/..#./.###/##.#
###/#.#/#.. => #.../#.##/..##/..##
#../.##/#.. => #.##/#.../.##./##..
.#./.##/#.. => #.#./#.../..##/.#..
##./.##/#.. => .###/.#.#/####/.#.#
#.#/.##/#.. => ####/.#../##.#/.###
.##/.##/#.. => .#../##.#/####/#.#.
###/.##/#.. => #.##/#.../...#/....
#../###/#.. => ###./.#.#/##../#..#
.#./###/#.. => #..#/..##/..../....
##./###/#.. => ..#./#.../...#/###.
..#/###/#.. => ##../..##/##../#.##
#.#/###/#.. => ..../..#./.###/##..
.##/###/#.. => #..#/####/.#.#/.##.
###/###/#.. => ###./#.##/##.#/.#..
.#./#.#/.#. => #.../####/#.#./.##.
##./#.#/.#. => ..##/..../.#.#/##..
#.#/#.#/.#. => ####/..##/####/#...
###/#.#/.#. => ##.#/#.#./.##./####
.#./###/.#. => .#.#/.#.#/##.#/###.
##./###/.#. => .#../###./#.##/#...
#.#/###/.#. => #.../.###/.#../.#..
###/###/.#. => #.#./.##./.###/####
#.#/..#/##. => .#../#..#/###./#.##
###/..#/##. => #.#./####/###./###.
.##/#.#/##. => .#.#/...#/..../#.##
###/#.#/##. => ...#/..../#.##/####
#.#/.##/##. => ##../.#../.#.#/##..
###/.##/##. => #.../#.#./#.#./#.#.
.##/###/##. => ..../#.##/#.##/..##
###/###/##. => ####/##.#/#..#/.##.
#.#/.../#.# => ##.#/.#.#/####/.##.
###/.../#.# => #..#/.#.#/#.../#..#
###/#../#.# => ..##/###./.###/..##
#.#/.#./#.# => #.##/#.#./##../...#
###/.#./#.# => ..#./.###/..##/#...
###/##./#.# => #.../...#/..##/.###
#.#/#.#/#.# => #..#/.#../...#/#..#
###/#.#/#.# => ###./#.../##../.##.
#.#/###/#.# => ...#/..#./...#/#..#
###/###/#.# => ###./####/.###/###.
###/#.#/### => .###/.#../..../##.#
###/###/### => #..#/.###/##../.##.
"""

  def part1(n: Int, s: String) = {
    val rows = s.split("\n").map(_.trim).filter(!_.isEmpty)
    val ruleSet = mutable.Map[Int, mutable.Map[String, Grid]]()

    class Grid(private val grid: Array[Array[Char]]) {
      def this(g: Grid) = this(g.toArray)
      def this(s: String) = this(s.split("/").map(_.split("").map(_.charAt(0))))
      def isEmpty = grid.isEmpty
      override def toString = grid.map((row) => row.mkString("")).mkString("\n")
      def size = grid.size
      def toArray = grid
      def equals(g: Grid) = toString == g.toString
      override def hashCode = toString.hashCode
      def split(n: Int) =
        grid.grouped(n).map((slice) => slice.map((row) => row.grouped(n).toArray).transpose.map(new Grid(_))).
          toArray
      def rotate = new Grid(grid.transpose.reverse)
      def hflip = new Grid(grid.map(_.reverse))
      def vflip = new Grid(grid.reverse)
      def enhance = {
        val rules = ruleSet(size % 2)
        var grid = this
        val keys = mutable.Set[String]()
        for (rotation <- 0 until 4) {
          for (flip <- 0 until 2) {
            keys += grid.toString
            grid = grid.vflip
          }
          grid = grid.rotate
        }
        (keys collectFirst { case k: String if rules.contains(k) => rules(k) }).get
      }
      def hjoin(g: Grid) = if (isEmpty) g else new Grid(grid.zip(g.toArray).map((t) => t._1 ++ t._2))
      def vjoin(g: Grid) = if (isEmpty) g else new Grid(grid ++ g.toArray)
      def count(c: Char) = grid.map(_.count(_ == c)).sum
    }
    object Grid {
      final val Empty = new Grid(Array[Array[Char]]())
      def hjoin(gs: GenTraversableOnce[Grid]) = gs.fold(Grid.Empty)(_ hjoin _)
      def vjoin(gs: GenTraversableOnce[Grid]) = gs.fold(Grid.Empty)(_ vjoin _)
      def join(gs: TraversableOnce[Array[Grid]]) =
        vjoin(gs.map(hjoin(_)))
    }

    val Rule = "(.*) => (.*)".r
    rows.map(_ match {
      case Rule(from, to) => 
        val kind = from.indexOf('/') % 2
        ruleSet(kind) = ruleSet.getOrElse(kind, mutable.Map[String, Grid]()) += new Grid(from).toString -> new Grid(to)
    })

    var size = 3
    var cnt = 5
    var grid = new Grid(".#./..#/###")
    for (i <- 0 until n) {
      def process(n: Int) =  if (grid.size % n == 0) Some(Grid.join(grid.split(n).map(_.map(_.enhance)))) else None
      var next = process(2)
      if (next == None) next = process(3)
      grid = next.get
    }
    grid.count('#')
  }

  "part1" should "satisfy the examples given" in {
    assertResult(12) { part1(2, """
../.# => ##./#../...
.#./..#/### => #..#/..../..../#..#
""") }
  }
  "part1" should "succeed" in { info(part1(5, input).toString) }

  def part2(n: Int, s: String) = part1(n, s)

  "part2" should "satisfy the examples given" in {
  }
  "part2" should "succeed" in { info(part2(18, input).toString) }
}
