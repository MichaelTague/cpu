2021-07-25 Version 2 Simple CPU Emulator

Use:

    cat cpu-good.txt | java Cpu
or
    cat cpu-good.txt | java Cpu status
or
    cat cpu-good.txt | java Cpu debug

cpu-good.txt contains the cpu emulator written in version 2 of the instruction set.

Version 2 of the instruction set:
Adds:
    40    eq
    41    ne
    42    lt
    43    gt
    44    le
    45    ge
Deletes:
    62    cne
    63    clt
    65    cle
Fixes:
     3    loadi
     4    storei
     8    loadpg
Each of the fixes needed to remove the "add base" line, as addresses already have the base added in the early
part of the emulator.

Curiously, the old version of loadi/storei/loadprog all managed to work when running multiple copies of the emulator,
it just would not put them in the obvious place in memory.

The cpu-good.txt will actually work in v1 as the 40-45 instructions were already added in that Cpu.java, though it will
load them in different places. 
