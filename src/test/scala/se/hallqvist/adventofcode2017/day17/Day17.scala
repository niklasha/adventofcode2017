package se.hallqvist.adventofcode2017.day17

import org.scalatest._

/*
Part one.

--- Part Two ---

Part two.

*/

class Day17 extends FlatSpec {
  val input = 344

  def part1(n: Int) = {
    var buf = Seq.fill(1)(0)
    var p = 0
    for (i <- 1 to 2017) {
      p = (p + n) % buf.size + 1
      buf = (buf.take(p) :+ i) ++ buf.drop(p)
    }
    buf(p + 1)
  }

  "part1" should "satisfy the examples given" in {
    assertResult(638) { part1(3) }
  }
  "part1" should "succeed" in { info(part1(input).toString) }

  def part2(n: Int) = {
    var p = 0
    var p0 = 0
    var v = 0
    for (i <- 1 to 50000000) {
      p = (p + n) % i + 1
      if (p == p0 + 1) v = i
      else if (p <= p0) p0 += 1
    }
    v
  }

  "part2" should "satisfy the examples given" in {
  }
  "part2" should "succeed" in { info(part2(input).toString) }
}
