class CpuCount {
    public static void main(String [] args) {
        int[] mem = new int[20];

        // int count = 1;
        // while(count <= 10) {
        //     System.out.println(count);
        //     count += 1;
        // }

        mem[0]  = 50 << 24 | 10;             // jump to location 10
        mem[1]  =  0 << 24 | 10;             // 10  (constant: 10)
        mem[2]  =  0 << 24 |  1;             // 1   (constant: 1)
        mem[3]  =  0 << 24 |  1;             // 1   (variable: count = 1)
        mem[4]  =  0 << 24 |  0;
        mem[5]  =  0 << 24 |  0;
        mem[6]  =  0 << 24 |  0;
        mem[7]  =  0 << 24 |  0;    
        mem[8]  =  0 << 24 |  0;
        mem[9]  =  0 << 24 |  0;
        mem[10] = 44 << 24 |  1;             // count <= 10
        mem[11] =  0 << 24 |  0;             // halt if above not true
        mem[12] =  5 << 24 |  0;             // print
        mem[13] = 10 << 24 |  2;             // count += 1 [add 1 from location 2]
        mem[14] = 50 << 24 | 10;             // goto top of loop (location 10)

        int acc = 0;   // accumulator, initial value 0
        int pc = 0;    // program count starts at 0

        while(true) {
            int opcode = (mem[pc] & 0xFF000000) >> 24;
            int address = (mem[pc] & 0xFFFFFF);
            switch(opcode) {
                case 0:  // halt
                    return;
                case 5:  // print
                    System.out.println(acc);
                    break;
                case 10: // add
                    acc += mem[address];
                    break;
                case 44: // le
                    if(acc <= mem[address]) {
                        pc += 2;
                        continue;
                    }
                    break;
                case 50: //jmp
                    pc = address;
                    continue;
                default:
                    System.out.println("Invalid instruction: " + opcode + " at " + pc);
                    return;
            }
            pc += 1;
        }
    }
}