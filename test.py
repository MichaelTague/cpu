import sys

mem = [0] * 1000
memmem = 1
def foo(mem, memmem):
    mem[1] = 12
    memmem = 99

foo(mem, memmem)
print(mem[1])
print(memmem)
