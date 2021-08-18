import sys
import time

INSTS = "halt  load  store loadi storeiprint prtdbg      loadpgprtmem" \
        "add   sub   mul   div   mod   15    16    17    18    19    " \
        "and   or    xor   not   shftl shftr 26    27    28    29    " \
        "30    31    32    33    34    35    36    37    38    39    " \
        "eq    ne    lt    gt    le    ge    46    47    48    49    " \
        "jmp   jmp+  52    53    54    55    56    57    58    59    " \
        "ceq   61    cne   clt   cgt   cle   cge   67    68    69    " \
        "70    71    72    73    74    75    76    77    78    79    " \
        "80    81    82    83    84    85    86    87    88    89    " \
        "90    91    92    93    94    95    96    97    98    stop  "

def loadprogram(debug, base, mem):
    if(debug):
        print(f"loadprogram, base: {base}")
    i = base + 1
    for line in sys.stdin:
        line_array = line.split()
        opcode = int(line_array[0])
        address = int(line_array[1])
        mem[i] = (opcode << 24) | address
        if debug:
            print(f'{i:4}  {line}', end='')
        if opcode == 99:
            if debug:
                print(f"loadprogram, returning new base(99): {i}")
            return i
        i += 1
    if debug:
        print(f"loadprogram, returning new base: {i}")
    return i

def runprogram(debug, base, acc, mem):
    global INSTS
    stats = [int(0)] * 256
    maxmem = acc
    pc = int(1)  #initialize Program Counter
    while True:
        opcode =  (mem[pc] & 0xFF000000) >> 24
        address = (mem[pc] & 0x00FFFFFF)
        if debug:
            print(f'{pc:4} {opcode:3} {INSTS[opcode*6:opcode*6+6]} {address:4}   acc: {acc}')
        stats[opcode] += 1

        if opcode == 50:       # jmp
            pc = address
            continue
        elif opcode == 2:      # store
            mem[address] = acc
        elif opcode == 3:      # load indirect
            acc = mem[mem[address]]
        elif opcode == 1:      # load
            acc = mem[address]
        elif opcode == 20:     # and
            acc = acc & mem[address]
        elif opcode == 10:     # add
            acc += mem[address]
        elif opcode == 25:     # shftr
            acc = acc >> mem[address]
        elif opcode == 51:     # jmp+
            pc = address + acc
            continue
        elif opcode == 4:      # store indirect
            mem[mem[address]] = acc
        elif opcode == 5:      # print
            print(acc)
        elif opcode == 8:      # loadprog
            acc = loadprogram(debug, mem[address], mem)
        elif opcode == 9:      # print memory
            for i in range(1, maxmem+1):
                op = (mem[pc] & 0xFF000000) >> 24
                ad = (mem[pc] & 0x00FFFFFF)
                print(f'{i:4} {op:3} {INSTS[op*6:op*6+6]} {ad:4}')
        elif opcode == 11:     # sub
            acc -= mem[address]
        elif opcode == 12:     # mul
            acc *= mem[address]
        elif opcode >= 40 and opcode <= 45:
            if opcode == 40:   # eq
                if acc == mem[address]:
                    pc += 2
                    continue
            elif opcode == 41: # ne
                if acc != mem[address]:
                    pc += 2
                    continue
            elif opcode == 42: # lt
                if acc < mem[address]:
                    pc += 2
                    continue
            elif opcode == 43: # gt
                if acc > mem[address]:
                    pc += 2
                    continue
            elif opcode == 44: # le
                if acc <= mem[address]:
                    pc += 2
                    continue
            elif opcode == 45: # ge
                if acc >= mem[address]:
                    pc += 2
                    continue
        elif opcode == 62:     # cne
            if acc != 0:
                pc += 2
                continue
        elif opcode == 63:     # clt
            if acc < 0:
                pc += 2
                continue
        elif opcode == 65:     # cle
            if acc <= 0:
                pc += 2
                continue
        elif opcode == 0:      # halt
            return stats
        else:
            print(f'Invalid instruction: {opcode} at {pc}')
            return stats
        pc += 1

def printstats(stats, starttime):
    endtime = time.time()*1000
    total = 0
    for i in range(100):
        total += stats[i]

    for i in range(100):
        if stats[i] != 0:
            percent = f'{((stats[i]*100+0.05)/total):.1f}'
            if percent[0] == '0':
                percent = percent[1:]
            print(f'{stats[i]:16,} {INSTS[i*6:i*6+6]} {percent:>6} %')
    print(f'{total:16,} Instructions Executed in {(endtime - starttime + 0.5):,.0f} ms')


def main():
    debug = False
    stats = False
    mem = [int(0)] * 10000
    starttime = time.time() * 1000
    if len(sys.argv) > 1:
        if sys.argv[1] == "debug":
            debug = True
            stats = True
        elif sys.argv[1] == "stats":
            stats = True
    base = loadprogram(debug, base=0, mem=mem)
    statvals = runprogram(debug, base=0, acc=base, mem=mem)
    if stats:
        printstats(statvals, starttime)

if __name__ == "__main__" :
    main()