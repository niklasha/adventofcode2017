package se.hallqvist.adventofcode2017.day23

import org.scalatest._
import scala.collection._

/*
--- Day 23: Coprocessor Conflagration ---

You decide to head directly to the CPU and fix the printer from there. As you get close, you find an experimental coprocessor doing so much work that the local programs are afraid it will halt and catch fire. This would cause serious issues for the rest of the computer, so you head in and see what you can do.

The code it's running seems to be a variant of the kind you saw recently on that tablet. The general functionality seems very similar, but some of the instructions are different:

set X Y sets register X to the value of Y.
sub X Y decreases register X by the value of Y.
mul X Y sets register X to the result of multiplying the value contained in register X by the value of Y.
jnz X Y jumps with an offset of the value of Y, but only if the value of X is not zero. (An offset of 2 skips the next instruction, an offset of -1 jumps to the previous instruction, and so on.)
Only the instructions listed above are used. The eight registers here, named a through h, all start at 0.

The coprocessor is currently set to some kind of debug mode, which allows for testing, but prevents it from doing any meaningful work.

If you run the program (your puzzle input), how many times is the mul instruction invoked?

--- Part Two ---
Now, it's time to fix the problem.

The debug mode switch is wired directly to register a. You flip the switch, which makes register a now start at 1 when the program is executed.

Immediately, the coprocessor begins to overheat. Whoever wrote this program obviously didn't choose a very efficient implementation. You'll need to optimize the program if it has any hope of completing before Santa needs that printer working.

The coprocessor's ultimate goal is to determine the final value left in register h once the program completes. Technically, if it had that... it wouldn't even need to run the program.

After setting register a to 1, if the program were to run to completion, what value would be left in register h?

*/

class Day23 extends FlatSpec {
  val input = """
set b 93
set c b
jnz a 2
jnz 1 5
mul b 100
sub b -100000
set c b
sub c -17000
set f 1
set d 2
set e 2
set g d
mul g e
sub g b
jnz g 2
set f 0
sub e -1
set g e
sub g b
jnz g -8
sub d -1
set g d
sub g b
jnz g -13
jnz f 2
sub h -1
set g b
sub g c
jnz g 2
jnz 1 3
sub b -17
jnz 1 -23
"""

  def part1(s: String) = {
    val prog = s.split("\n").map(_.trim).filter(!_.isEmpty)
    val Set = "set (.*) (.*)".r
    val Sub = "sub (.*) (.*)".r
    val Mul = "mul (.*) (.*)".r
    val Jnz = "jnz (.*) (.*)".r
    val reg = mutable.Map[String, Int]()
    var cnt = 0
    var pc = 0
    def isReg(x: String) = "-0123456789".indexOf(x.charAt(0)) == -1
    def value(x: String) = if (isReg(x)) reg.getOrElse(x, 0) else x.toInt
    def execute(s: String) = s match {
      case Set(x, y) => reg(x) = value(y)
      case Sub(x, y) => reg(x) = reg.getOrElse(x, 0) - value(y)
      case Mul(x, y) => { reg(x) = reg.getOrElse(x, 0) * value(y); cnt += 1 }
      case Jnz(x, y) => if (value(x) != 0) pc += value(y).toInt - 1
    }
    while (pc >= 0 && pc < prog.size) { execute(prog(pc)); pc += 1 }
    cnt
  }

  "part1" should "satisfy the examples given" in {
  }
  "part1" should "succeed" in { info(part1(input).toString) }

  def part2() = {
    def isPrime(n: Int) = (2 to math.sqrt(n).toInt) forall (n % _ != 0)
    var cnt = 0
    for (candidate <- 109300 to 126300 by 17)
      if (!isPrime(candidate)) cnt += 1
    cnt
  }

  "part2" should "satisfy the examples given" in {
  }
  "part2" should "succeed" in { info(part2().toString) }
}
