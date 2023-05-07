class CpuFact {
    public static void main(String [] args) {
        int[] mem = new int[30];

        // int fact = 1;
        // for(int i = 1; i <= 12; i++) {
        //     fact *= i;
        //     System.out.println(fact):
        // }
        
        mem[0]  = 50 << 24 | 10;             // jump to location 10
        mem[1]  =  0 << 24 | 12;             // 12  (constant: 12)
        mem[2]  =  0 << 24 |  1;             //  1  (constant: 1)
        mem[3]  =  0 << 24 |  1;             //  1  (variable: i = 1)
        mem[4]  =  0 << 24 |  1;             //  1  (variable: fact = 1)
        mem[5]  =  0 << 24 |  0;
        mem[6]  =  0 << 24 |  0;
        mem[7]  =  0 << 24 |  0;    
        mem[8]  =  0 << 24 |  0;
        mem[9]  =  0 << 24 |  0;
        mem[10] =  1 << 24 |  3;             // load i (loc 3)                 i < 12
        mem[11] = 42 << 24 |  1;             // i < 12 (loc 1)
        mem[12] =  0 << 24 |  0;             // halt if above not true
        mem[13] = 12 << 24 |  4;             // mul fact (loc 4)               fact *= i
        mem[14] =  2 << 24 |  4;             // fact = acc
        mem[15] =  5 << 24 |  0;             // print                          print
        mem[16] =  1 << 24 |  3;             // load i (loc 3)                 i++
        mem[17] = 10 << 24 |  2;             // add 1 (loc 2)
        mem[18] =  2 << 24 |  3;             // store i (loc 3)
        mem[19] = 50 << 24 | 10;             // jump to location 10, top of loop

        int acc = 0;   // accumulator, initial value 0
        int pc = 0;    // program count starts at 0

        for(int i = 0; i < 5000; i++) {
            int opcode = (mem[pc] & 0xFF000000) >> 24;
            int address = (mem[pc] & 0xFFFFFF);
        //    System.out.println("acc: " + acc + ", pc: " + pc + ", op: " + opcode + ", addr: " + address);
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
                case 12: // mul
                    acc *= mem[address];
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
