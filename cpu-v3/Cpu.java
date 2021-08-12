import java.util.*;
import java.lang.Math.*;
class Cpu {
    static boolean debug = false;
    static boolean stats = false;
    static int[] mem = new int[10000];
    static int maxmem = 0;
    static int acc = 0;
    static int base = 0;
    static Scanner sc = new Scanner(System.in);
    static long startTime = System.currentTimeMillis();

    static int loadProgram(int[] mem, int base) {
        if(debug) System.out.println("loadProgram, base: " + base);
        int i = base + 1;
        while(sc.hasNextLine()) {
            int opcode = sc.nextInt();
            int address = sc.nextInt();
            String line = sc.nextLine();
            if(debug) System.out.printf("%03d  %02d %8d %-20s\n", i, opcode, address, line);
            mem[i] = opcode << 24 | address;
            maxmem = Math.max(maxmem, i);
            if(opcode == 99) {
                if(debug) System.out.println("loadProgram, returning(99): " + i);
                return i;
            }
            i++;
        }
        if(debug) System.out.println("loadProgram, returning: " + i);
        return i;
    }

    static long[] runProgram(int[] mem) {
        long[] stats = new long[256];
        int pc = 1; //initialize Program Counter (one based)
        while(true) {
            int opcode = (mem[pc] & 0xFF000000) >> 24;
            int address = (mem[pc] & 0xFFFFFF);
            if(debug) {
                System.out.printf("                   >>pc: %3d, acc: %10d, op: %2d, addr: %3d, @addr: %10d" +
                ", 033ind: %3d, 034temp: %3d, 035base: %3d, 036pc: %3d, 037op: %3d, 038addr: %3d, 039acc: %3d\n", 
                pc, acc, opcode, address, mem[address], mem[33], mem[34], mem[35], mem[36], mem[37], mem[38], mem[39]);
         //       System.out.printf("                   XXpc: %3d, acc: %10d, op: %2d, addr: %3d, @addr: %10d" +
         //       ", 333ind: %3d, 334temp: %3d, 335base: %3d, 336pc: %3d, 337op: %3d, 338addr: %3d, 339acc: %3d\n", 
         //       mem[36], mem[39], mem[37], mem[38], mem[mem[38]], mem[333], mem[334], mem[335], mem[336], mem[337], mem[338], mem[339]);
            }
            stats[opcode] += 1;
            switch(opcode) {
                case 0:  //halt
                    return stats;
                case 1:  //load
                    acc = mem[address];
                    break;
                case 2:  //store
                    mem[address] = acc;
                    break;
                case 3:  //load indirect
                    acc = mem[mem[address]];
                    break;
                case 4:  //store indirect
                    mem[mem[address]] = acc;
                    break;
                case 5:  //print
                    System.out.println(acc);
                    break;
//                case 6:  //printdebug
//                    System.out.println("                    " + acc);
//                    break;
                case 8:  //loadprog
                    acc = loadProgram(mem, mem[address]);
                    break;
                case 9:  //printmemory
                    for(int i = 1; i <= maxmem; i++) {
                        System.out.printf("%4d: %3d %3d\n", i, ((mem[i] & 0xFF000000) >> 24), mem[i] & 0x00FFFFFF);
                    }
                case 10: //add
                    acc += mem[address];
                    break;
                case 11: //sub
                    acc -= mem[address];
                    break;
                case 12: //mul
                    acc *= mem[address];
                    break;
                case 20: //and
                    acc = acc & mem[address];
                    break;
                case 25: //shftr
                    acc = acc >> mem[address];
                    break;
                case 40: //eq
                    if(acc == mem[address]) {
                        pc += 2;
                        continue;
                    }
                    break;
                case 41: //ne
                    if(acc != mem[address]) {
                        pc += 2;
                        continue;
                    }
                    break;
                case 42: //lt
                    if(acc < mem[address]) {
                        pc += 2;
                        continue;
                    }
                    break;
                case 43: //gt
                    if(acc > mem[address]) {
                        pc += 2;
                        continue;
                    }
                    break;
                case 44: //le
                    if(acc <= mem[address]) {
                        pc += 2;
                        continue;
                    }
                    break;
                case 45: //ge
                    if(acc >= mem[address]) {
                        pc += 2;
                        continue;
                    }
                    break;
                case 50: //jmp
                    pc = address;
                    continue;
                case 51: //jmp (address + Accumulator)
                    pc = address + acc;
                    continue;
//                case 60: //ceq
//                    if(acc == 0) {
//                        pc += 2;
//                        continue;
//                    }
//                    break;
                case 62: //cne
                    if(acc != 0) {
                        pc += 2;
                        continue;
                    }
                    break;
                case 63: //clt
                    if(acc < 0) {
                        pc += 2;
                        continue;
                    }
                    break;
//                case 64: //cgt
//                    if(acc > 0) {
//                        pc += 1;
//                    }
//                    break;
                case 65: //cle
                    if(acc <= 0) {
                        pc += 1;
                    }
                    break;
//                case 66: //cge
//                    if(acc >= 0) {
//                        pc += 1;
//                    }
//                    break;
                case 99: //stop
                default:
                    System.out.println("Invalid instruction: " + opcode + " at " + pc);
                    return stats;
            }
            pc += 1;
        }
    }
    static void printStats(long[] stats) {
        long endTime = System.currentTimeMillis();
        String insts = "halt  load  store loadi storeiprint prtdbg      loadpgprtmemadd   sub   mul   div   mod   15    16    17    18    19    " +
                       "and   or    xor   not   shftl shftr 26    27    28    29    30    31    32    33    34    35    36    37    38    39    " +
                       "eq    ne    lt    gt    le    ge    46    47    48    49    jmp   jmp+  52    53    54    55    56    57    58    59    " +
                       "ceq   61    cne   clt   cgt   cle   cge   67    68    69    70    71    72    73    74    75    76    77    78    79    " +
                       "80    81    82    83    84    85    86    87    88    89    90    91    92    93    94    95    96    97    98    stop  ";
        long total = 0;
        for(int i = 0; i <= 99; i++) {
            total += stats[i];
        }
        for(int i = 0; i <= 99; i++) {
            if(stats[i] != 0) {
                String percent = String.format("%.1f", (stats[i]*100+0.05)/total);
                if(percent.substring(0,1).equals("0")) {
                    percent = percent.substring(1);
                }
                System.out.printf("%,16d %6s %6s %%\n", stats[i], insts.substring(i*6, i*6+6), percent);
            }
        }
        System.out.printf("%,16d Instructions Executed in %,d ms\n", total, (endTime - startTime));
    }
    public static void main(String [] args) {
        if(args.length > 0) {
            if(args[0].equals("debug")) {
                debug = true;
                stats = true;
            } else if (args[0].equals("stats")) {
                stats = true;
            }
        }
        acc = loadProgram(mem, base);
        long[] statVals = runProgram(mem);
        if(stats) printStats(statVals);
        sc.close();
    }
}