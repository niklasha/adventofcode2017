package se.hallqvist.adventofcode2017.day3

import org.scalatest._
import scala.collection._

/*
--- Day 3: Spiral Memory ---

You come across an experimental new kind of memory stored on an infinite two-dimensional grid.

Each square on the grid is allocated in a spiral pattern starting at a location marked 1 and then counting up while spiraling outward. For example, the first few squares are allocated like this:

17  16  15  14  13
18   5   4   3  12
19   6   1   2  11
20   7   8   9  10
21  22  23---> ...
While this is very space-efficient (no squares are skipped), requested data must be carried back to square 1 (the location of the only access port for this memory system) by programs that can only move up, down, left, or right. They always take the shortest path: the Manhattan Distance between the location of the data and square 1.

For example:

Data from square 1 is carried 0 steps, since it's at the access port.
Data from square 12 is carried 3 steps, such as: down, left, left.
Data from square 23 is carried only 2 steps: up twice.
Data from square 1024 must be carried 31 steps.
How many steps are required to carry the data from the square identified in your puzzle input all the way to the access port?

--- Part Two ---

As a stress test on the system, the programs here clear the grid and then store the value 1 in square 1. Then, in the same allocation order as shown above, they store the sum of the values in all adjacent squares, including diagonals.

So, the first few squares' values are chosen as follows:

Square 1 starts with the value 1.
Square 2 has only one adjacent filled square (with value 1), so it also stores 1.
Square 3 has both of the above squares as neighbors and stores the sum of their values, 2.
Square 4 has all three of the aforementioned squares as neighbors and stores the sum of their values, 4.
Square 5 only has the first and fourth squares as neighbors, so it gets the value 5.
Once a square is written, its value does not change. Therefore, the first few squares would receive the following values:

147  142  133  122   59
304    5    4    2   57
330   10    1    1   54
351   11   23   25   26
362  747  806--->   ...
What is the first value written that is larger than your puzzle input?



*/

class Day3 extends FlatSpec {
  val input = 368078

  def part1(s: Int) = {
    // The series 1, 9, 25, ... , i.e. (2*i+1)^2 is relevant, note
    // that: (2*(i+1)+1)^2-(2*i+1)^2
    // (2*i+3)^2-(4*i^2+4*i+1)
    // (4*i^2+12*i+9)-(4*i^2-4*i-1)
    // 8*i+8
    def radius(p: Int) = Math.floor((Math.sqrt(p - 1) + 1) / 2).toInt
    def area(r: Int) = (2 * r + 1) * (2 * r + 1)
    def circumference(r: Int) = 8 * r + 8
    // for every radius the circumference is split in four sides:
    // east, north, west and south each a 4th in length and offset an 8th
    // making east use 0-c/8 and 7/8*c - c, the corners can go either way.
    // It's important to get to the axis, which s done by just moving 1
    // step
    // The axis are of importance:
    // east 1, 2, 11, 28 (increase each step with 1+c(r(i))+c(r(i) + n)/8
    def east(r: Int) = {
      val c1 = area(r - 1)
      val c2 = area(r)
      1 + 7 * c1 / 8 + c2 / 8
    }
    def north(r: Int) = {
      val c1 = area(r - 1)
      val c2 = area(r)
      1 + 5 * c1 / 8 + 3 * c2 / 8
    }
    def west(r: Int) = {
      val c1 = area(r - 1)
      val c2 = area(r)
      1 + 3 * c1 / 8 + 5 * c2 / 8
    }
    def south(r: Int) = {
      val c1 = area(r - 1)
      val c2 = area(r)
      1 + 1 * c1 / 8 + 7 * c2 / 8
    }
//    info(s"${circumference(0)} ${circumference(1)} ${circumference(2)} ${circumference(3)}")
//    info(s"${east(0)} ${east(1)} ${east(2)} ${east(3)}")
//    info(s"${north(0)} ${north(1)} ${north(2)} ${north(3)}")
//    info(s"${west(0)} ${west(1)} ${west(2)} ${west(3)}")
//    info(s"${south(0)} ${south(1)} ${south(2)} ${south(3)}")
    // find closest axis on the circumference, and then walk towards the middle
    var i = 0
    var x = s
    while (x > 1) {
      val r = radius(x)
      val a = Seq(east(r), north(r), west(r), south(r))
      val c = circumference(r - 1)
      val o = x - east(r)
      val e = (8 * o / c + 1) % 8 / 2
//      info(s"$i $x $r $c $o $e ${a(e)}")
      if (x != a(e)) {
        i += Math.abs(x - a(e))
        x = a(0)
      } else {
        x = east(r - 1)
        i += 1
      }
    }
    i
  }

  "part1" should "satisfy the examples given" in {
    assertResult(0) { part1(1) }
    assertResult(3) { part1(12) }
    assertResult(2) { part1(23) }
    assertResult(31) { part1(1024) }
  }

  "part1" should "succeed" in { info(part1(input).toString) }

  def part2(s: Int) = {
    def radius(p: Int) = Math.floor((Math.sqrt(p - 1) + 1) / 2).toInt
    def area(r: Int) = (2 * r + 1) * (2 * r + 1)
    def circumference(r: Int) = 8 * r + 8
    def eigth(p: Int) = circumference(radius(p)) / 8
    def east(r: Int) = {
      val c1 = area(r - 1)
      val c2 = area(r)
      1 + 7 * c1 / 8 + c2 / 8
    }
    def north(r: Int) = {
      val c1 = area(r - 1)
      val c2 = area(r)
      1 + 5 * c1 / 8 + 3 * c2 / 8
    }
    def west(r: Int) = {
      val c1 = area(r - 1)
      val c2 = area(r)
      1 + 3 * c1 / 8 + 5 * c2 / 8
    }
    def south(r: Int) = {
      val c1 = area(r - 1)
      val c2 = area(r)
      1 + 1 * c1 / 8 + 7 * c2 / 8
    }
    def xy(p: Int) = {
      var n = p
      var x = 0
      var y = 0
      var e = 0
      var f = 0
      var d = -1
      while (n > 1) {
        val r = radius(n)
        val a = Seq(east(r), north(r), west(r), south(r))
        val c = circumference(r - 1)
        val o = (n - east(r) + c) % c
        f = (8 * o / c + 1) % 8
        e = f / 2
        if (d == -1)
          d = f
//        info(s"$n $x $y $r $c $o $f $e ${a(e)}")
        if (n != a(e)) {
          x += Math.abs(n - a(e)) % (c / 4)
          n = a(0)
        } else {
          n = east(r - 1)
          y += 1
        }
      }
      var xy = if ((d / 2) % 2 == 1) (x, y) else (y, x)
      xy = if (d > 0 && d < 5) (xy._1, xy._2) else (xy._1, -xy._2)
      if (d > 2 && d < 7) (-xy._1, xy._2) else (xy._1, xy._2)
    }
    var i = 1
    var t = 0
    val m = mutable.Map[(Int, Int), Int]()
    var p = (0, 0)
    m(p) = 1
    while (m(p) <= s) {
      p = xy(i)
      m(p) = (-1 to 1).map((x) => { (-1 to 1).map((y) => m.getOrElse((p._1 + x, p._2 + y), 0)).sum }).sum
//      info(s"$i $p ${m(p)}")
      i += 1
    }
    m(p)
  }

  "part2" should "satisfy the examples given" in {
    assertResult(10) { part2(5) }
    assertResult(54) { part2(50) }
    assertResult(747) { part2(500) }
  }

  "part2" should "succeed" in { info(part2(input).toString) }
}
