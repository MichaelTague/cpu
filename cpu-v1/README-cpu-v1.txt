2021-07-10 Version 1 Simple Cpu emulator

Use:
    cat count-10.txt | java Cpu
or like:
    cat fact-12.txt | java Cpu debug
or:
    cat cpu-good.txt | java Cpu

Arguably, this isn't the 1st version.
- Version 0.1 could run these programs: count-10.txt, fib-20.txt, and fact-12.txt.
  It possed these 10 instructions:
    00 halt,
    01 load,
    02 store,
    05 print,
    10 add,
    11 sub,
    12 mul,
    50 jmp,
    63 clt (compare less than), and
    65 cle (compare less equal).

- Version 1, here, possesses the above but also the instructions to allow cpu-good, a cpu emulator, to be run.  It adds these
  instructions:
    03 loadi (indirect),
    04 storei (indirect),
    08 loadpgm,
    20 and,
    25 shftr (shift right),
    and 99 stop.
  - Stop & loadpgm (load program) work together to allow a 1st program to be loaded (but stop loading when it gets to 99 0), then 
    the loaded program, a machine emulator, can then do a loadpgm to start loading again what comes next into memory so that it 
    can read it (as data) and starting emulating its execution.
  - The loadi & storei are key to allowing an emulator to reference addresses mentioned in 2nd loaded program's data.
    E.g., load works like this pseudo code:
        store 10     is    put a value in location 10
        storei 10    is    put a value in location X, where the value of X can be found at location 10.
  - The other additional instructions were just orginary ones needed by the program's logic: and and shftr.

- The main feature of Version 1 is that it can run an emulator program: cpu-good.txt:

      cat cpu-good.txt | java Cpu.txt
    
    cpu-good implements an emulator that implements all of the version 1 instructions listed above and so can run any other program,
    including itself!
    
    cpu-good.txt comes with the emulator and a copy of count-10.txt pasted in at the end.

    cpu-good-5.txt has the emulator pasted in 5 times with a copy of count-10 at the end.  
    Try it!  It takes about 30 seconds and executes nearly 10 billion instructions!
    
- Finally, though not needed for version 1 (it is used in version 2), this Cpu.java also implements
  new eq, ne, lt, gt, le, & ge instructions which are simplified from ceq, cne, clt, cgt, etc.
  See version 2 to see how this simplifies things.
