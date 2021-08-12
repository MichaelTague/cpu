2021-07-25 Simple CPU Emulator - version 3

Version 3 adds the jmp+ instruction (jump to address + accumulator) in the Java and machine language versions.

This allows the long string of "ne" (essentially else if chains) to be replaced by a computed goto, jmp+, so 
execution of the machine language emulator is much faster.

Version 2 with 5 levels of emulators takes 3,827 ms to count to 10.   Version 3 with 5 levels takes 556 ms (6.9 times faster!)


The cpu6.txt is 6 levels of emulators which just takes a bit over 8 seconds.

    cat cpu6.txt | java Cpu stats
or
    cat cpu-v3-good.txt | java Cpu stats




