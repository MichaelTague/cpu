2021-08-13 CPU V4 - Michael Tague

Created a python version of the emulator: cpu.py

Also, set up a series of cpu emulators, the slow ones: cpus(1 2 3 4 5 6).txt and similar
and the fast one: cpuf(1 2 3 4 5 6 7).txt

Table of Performance times (in ms):
#     Java          Python
0       9               4
1      33               6
2      43              14
3     104             113
4     113           1,836
5     581          33,775
6   9,119

E.g., cat cpuf6.txt | java Cpu stats
      cat cpuf4.txt | python3 cpu stats
The 0'th one is just the count-10.txt project, no emulators.

CPU2 is CPU with the case statement optimized to put the most
heavily used instructions at the top.

After running it five times for each, it sped the computation up by .5%.