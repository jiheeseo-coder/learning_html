import sys

t = int(input())
for tt in range(t):
    x, y = map(int, sys.stdin.readline().split())
    n = y-x
    if n == 1:
        print(1)
        continue
    elif n == 2:
        print(2)
        continue
    else:
        i = 0
        while n > i*i:
            i += 1
        if n <= i*(i-1):
            i -= 1

        if n <= i**2:
            print(i*2-1)
        else:
            print(i*2)
