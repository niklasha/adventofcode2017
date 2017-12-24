package se.hallqvist.adventofcode2017.day24

import org.scalatest._

/*
Part one.

--- Part Two ---

Part two.

*/

class Day24 extends FlatSpec {
  val input = """
25/13
4/43
42/42
39/40
17/18
30/7
12/12
32/28
9/28
1/1
16/7
47/43
34/16
39/36
6/4
3/2
10/49
46/50
18/25
2/23
3/21
5/24
46/26
50/19
26/41
1/50
47/41
39/50
12/14
11/19
28/2
38/47
5/5
38/34
39/39
17/34
42/16
32/23
13/21
28/6
6/20
1/30
44/21
11/28
14/17
33/33
17/43
31/13
11/21
31/39
0/9
13/50
10/14
16/10
3/24
7/0
50/50
"""

  def part1(s: String) = {
    val Port = "(.*)/(.*)".r
    type Port = Int
    type Comp = (Port, Port)
    val comps = s.split("\n").map(_.trim).filter(!_.isEmpty).map(_ match {
      case Port(a, b) => (a.toInt, b.toInt).asInstanceOf[Comp]
    }).toSet
    def accept(p: Port, c: Comp) = p match {
      case c._1 => (true, c._2)
      case c._2 => (true, c._1)
      case _ => (false, null)
    }
    type Bridge = (Seq[Comp], Set[Comp], Port)
    def end(b: Bridge) = b._3
    def strength(b: Bridge) = b._1.map((c) => c._1 + c._2).sum
    def extend(b: Bridge) = b._2.filter(accept(end(b), _)._1).map((c) =>
      (b._1 :+ c, b._2 - c, accept(end(b), c)._2).asInstanceOf[Bridge])
    def extendBridges(bs: Set[Bridge]): Set[Bridge] = bs.flatMap((b) => {
      val nbs = extend(b)
      if (nbs.isEmpty) Set(b) else extendBridges(nbs)
    })
    val start = ((Seq[Comp]()), comps, 0).asInstanceOf[Bridge]
    extendBridges(Set(start)).map((b) => strength(b)).max
  }

  "part1" should "satisfy the examples given" in {
    assertResult(31) { part1("""
0/2
2/2
2/3
3/4
3/5
0/1
10/1
9/10
""") }
  }
  "part1" should "succeed" in { info(part1(input).toString) }

  def part2(s: String) = {
    val Port = "(.*)/(.*)".r
    type Port = Int
    type Comp = (Port, Port)
    val comps = s.split("\n").map(_.trim).filter(!_.isEmpty).map(_ match {
      case Port(a, b) => (a.toInt, b.toInt).asInstanceOf[Comp]
    }).toSet
    def accept(p: Port, c: Comp) = p match {
      case c._1 => (true, c._2)
      case c._2 => (true, c._1)
      case _ => (false, null)
    }
    type Bridge = (Seq[Comp], Set[Comp], Port)
    def end(b: Bridge) = b._3
    def strength(b: Bridge) = b._1.map((c) => c._1 + c._2).sum
    def extend(b: Bridge) = b._2.filter(accept(end(b), _)._1).map((c) =>
      (b._1 :+ c, b._2 - c, accept(end(b), c)._2).asInstanceOf[Bridge])
    def extendBridges(bs: Set[Bridge]): Set[Bridge] = bs.flatMap((b) => {
      val nbs = extend(b)
      if (nbs.isEmpty) Set(b) else extendBridges(nbs)
    })
    val start = ((Seq[Comp]()), comps, 0).asInstanceOf[Bridge]
    extendBridges(Set(start)).map((b) => (b._1.size, strength(b))).maxBy((x) => x._1 * 10000 + x._2)._2
  }

  "part2" should "satisfy the examples given" in {
    assertResult(19) { part2("""
0/2
2/2
2/3
3/4
3/5
0/1
10/1
9/10
""") }
  }
  "part2" should "succeed" in { info(part2(input).toString) }
}
