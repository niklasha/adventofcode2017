package se.hallqvist.adventofcode2017.day14

import org.scalatest._

/*
Part one.

--- Part Two ---

Part two.

*/

class Day14 extends FlatSpec {
  val input = "hxtvlmkl"

  def part1(s: String) = {
    def knot(s: String) = {
      val n = 256
      val list = (0 to (n - 1)).toArray
      val lengths = (s.map(_.toInt) ++ Seq(17, 31, 73, 47, 23))
      var currentPos = 0
    	  var skip = 0
      def round = {
    		  lengths foreach((length) => {
    		    val savedList = list.clone
    			  for (i <- 0 to (length - 1)) {
    				  list((currentPos + i) % n) = savedList((currentPos + length - 1 - i) % n)
    		  	  }
    			  currentPos = (currentPos + length + skip) % n
    			  skip += 1
    		  })
      }
      1 to 64 foreach { _ => round }
      list.grouped(16).map((g) => g.fold(0) { (a, b) => a ^ b }).map("%02x".format(_)).mkString
    }
    val bits = Array(0, 1, 1, 2, 1, 2, 2, 3, 1, 2, 2, 3, 2, 3, 3, 4)
    var used = 0
    for (i <- 0 to 127) {
      used += knot(s"$s-$i").map((c) => bits(c - (if (c > '9') 'a' - 10 else '0'))).sum
    }
    used
  }

  "part1" should "satisfy the examples given" in {
    assertResult(8108) { part1("flqrgnkx") }
  }
  "part1" should "succeed" in { info(part1(input).toString) }

  def part2(s: String) = {
    def knot(s: String) = {
      val n = 256
      val list = (0 to (n - 1)).toArray
      val lengths = (s.map(_.toInt) ++ Seq(17, 31, 73, 47, 23))
      var currentPos = 0
    	  var skip = 0
      def round = {
    		  lengths foreach((length) => {
    		    val savedList = list.clone
    			  for (i <- 0 to (length - 1)) {
    				  list((currentPos + i) % n) = savedList((currentPos + length - 1 - i) % n)
    		  	  }
    			  currentPos = (currentPos + length + skip) % n
    			  skip += 1
    		  })
      }
      1 to 64 foreach { _ => round }
      list.grouped(16).map((g) => g.fold(0) { (a, b) => a ^ b }).map("%02x".format(_)).mkString
    }
    var a = new Array[Array[String]](128)
    for (i <- 0 to 127) {
      a(i) = knot(s"$s-$i").split("").flatMap((c) => {
        val nibble = (c.charAt(0) - (if (c.charAt(0) > '9') 'a' - 10 else '0')).toBinaryString
        ("000" + nibble).substring(nibble.length - 1).replace("0", ".").replace("1", "#").split("")
      }).toArray
    }
    val region = Array.fill(128)(Array.fill(128)(0))
    def replaceInRow(row: Array[Int], from: Int, to: Int): Unit = for (i <- 0 to 127) if (row(i) == from) row(i) = to
    def replace(from: Int, to: Int): Unit = region.map((row) => replaceInRow(row, from, to))
    var r = 0
    var c = 0
    for (y <- 0 to 127) for (x <- 0 to 127 if a(y)(x) == "#") {
      val north = y > 0 && a(y - 1)(x) == "#"
      val west = x > 0 && a(y)(x - 1) == "#"
      val northwest = x > 0 && y > 0 && a(y - 1)(x - 1) == "#"
      region(y)(x) = if (north) {
        val nv = region(y - 1)(x)
        if (west) {
          val wv = region(y)(x - 1)
          if (wv != nv) { replace(region(y)(x - 1), nv); c -= 1 }
        }
        nv
      } else if (west)
        region(y)(x - 1)
      else {
        r += 1
        c += 1
        r
      }
    }
    c
  }

  "part2" should "satisfy the examples given" in {
    assertResult(1242) { part2("flqrgnkx") }
  }
  "part2" should "succeed" in { info(part2(input).toString) }
}
