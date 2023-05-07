# CPU Emulator

Michael Tague, 2023-05-06 Sat

In the summer of 2021, I was looking for a project to recommend to someone learning to code.   I imagined that a simple machine language emulator might be 
instructive, but I wasn't sure how difficult it might be so I made one myself first.

Coding wise it was surprisingly quick and easy.  But I found it fascinating that something so small, 30 lines or so of code, could then actually run
all kinds of programs, it was Turing Complete!

Look in the cpu-quick directory for simple (one file) emulators that count to ten, produce a Fibonacci sequence, or a list of factorials.

## Descent into the Darkness!  :smile:

Very quickly I was facing the same question I've faced my whole life in writing programs for fun: now what, what program do I write next?   I have a 
made-up CPU, what programs can I write for it?

So I thought, well, you just wrote a CPU emulator in Java, how about writing that in your machine language?

So I did.

And then I wondered, what do I run on my emulator written in machine language?  Well, how about the emulator you just wrote?   
A Java based machine language emulator running a machine language emulator written in machine language running another machine language program,
say to count to ten.

So I did that too.

And then for fun, just kept going deeper.  It works, but an emulator running an emulator running an emulator ... running a program starts to get very slow.

Then, well, what can I do to make it go faster?   The emulator is just a loop with a big switch statement in it, but that is implemented in this
machine language essentially as a big if-else chain.  But that is too slow, so then I added an instruction to allow for a Computed Goto.
That made it much faster!

Eventually I stopped.   This:

    cat count-10.txt | java Cpu stats

will count to ten, it takes about 8 ms while executing a Java program that emulates 44 instructions.

This:

    cat cpuf1.txt | java Cpu stats

will count to ten also, it takes about 21 ms while executing 854 instructions, the instructions of the emulator while it executes the 44 instructions
that count to ten.  

This:

    cat cpuf6.txt | java Cpu stats

will count to ten, taking 8 seconds while executing 1.9 billion instructions running an emulator on an emulator - 6 levels deep - ultimately running a program to count to ten.

Each machine language emulator executes 18 instructions to emulate one instruction, so with each level deep it runs 18 times as many instructions
and takes around 18 times longer.

## The Directories

cpu-quick, cpu-v1, cpu-v2, are earlier versions.

Each level contains some progression in the basic CPU emulator, both the Java and Machine Language ones.

## Python

For comparison, I wrote a Python version of the Java emulator, called cpu.py.   In the cpu-v4 directory is a README that shows the stats.

The Python version is run like this:

    cat cpuf1.txt | python3 cpu.py

## Java

All of the Java code is built simply by just compiling it:

    javac Cpu.java

## cpu-v4

In the cpu-v4 directory are cpus1.txt, cpus2.txt, etc. which are the emulators that do not use Computed Goto ("s" for slow, "f" for fast).
You can compare the speeds yourself:

    cat cpuf5.txt | java Cpu stats
    cat cpu-v4/cpus4.txt | java Cpu stats
