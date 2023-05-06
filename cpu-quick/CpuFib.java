class CpuFib {
    public static void main(String [] args) {
        int[] mem = new int[30];

        // int n1 = -1;
        // int n2 = 1;
        // for(int i = 0; i < 20; i++) {
        //     int n3 = n1 + n2;
        //     System.out.println(n3);
        //     n1 = n2;
        //     n2 = n3;
        // }
 
        mem[0]  = 50 << 24 | 10;             // jump to location 10
        mem[1]  =  0 << 24 | 20;             // 20  (constant: 20)
        mem[2]  =  0 << 24 |  1;             //  1  (constant: 1)
        mem[3]  =  0 << 24 |  0;             //  0  (variable: i = 0)
        mem[4]  =  0 << 24 | -1;             // -1  (variable: n1 = -1)
        mem[5]  =  0 << 24 |  1;             //  1  (variable: n2 = 1)
        mem[6]  =  0 << 24 |  0;             //  0  (variable: n3)
        mem[7]  =  0 << 24 |  0;    
        mem[8]  =  0 << 24 |  0;
        mem[9]  =  0 << 24 |  0;
        mem[10] =  1 << 24 |  3;             // load i (loc 2)                 i < 20
        mem[11] = 42 << 24 |  1;             // i < 20 (loc 1)
        mem[12] =  0 << 24 |  0;             // halt if above not true
        mem[13] =  1 << 24 |  4;             // load n1 (loc 4)                n3 = n1 + n2
        mem[14] = 10 << 24 |  5;             // add n2 (loc 5)
        mem[15] =  2 << 24 |  6;             // store to n3 (loc 6)
        mem[16] =  5 << 24 |  0;             // print acc (n3)                 print n3
        mem[17] =  1 << 24 |  5;             // load n2 (loc 5)                n1 = n2
        mem[18] =  2 << 24 |  4;             // store to n1 (loc 4)
        mem[19] =  1 << 24 |  6;             // load n3 (loc 6)                n2 = n3
        mem[20] =  2 << 24 |  5;             // store to n2 (loc 5)
        mem[21] =  1 << 24 |  3;             // load i (loc 3)
        mem[22] = 10 << 24 |  2;             // add 1 to acc (from loc 2)
        mem[23] =  2 << 24 |  3;             // store to i (loc 3)
        mem[24] = 50 << 24 | 10;             // jump to location 10, top of loop

        int acc = 0;   // accumulator, initial value 0
        int pc = 0;    // program count starts at 0

        for(int i = 0; i < 5000; i++) {
            int opcode = (mem[pc] & 0xFF000000) >> 24;
            int address = (mem[pc] & 0xFFFFFF);
    //        System.out.println("acc: " + acc + ", pc: " + pc + ", op: " + opcode + ", addr: " + address);
            switch(opcode) {
                case 0:  // halt
                    return;
                case 1:  // load
                    acc = mem[address];
                    break;
                case 2:  // store
                    mem[address] = acc;
                    break;
                case 5:  // print
                    System.out.println(acc);
                    break;
                case 10: // add
                    acc += mem[address];
                    break;
                case 42: // lt
                    if(acc < mem[address]) {
                        pc += 2;
                        continue;
                    }
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