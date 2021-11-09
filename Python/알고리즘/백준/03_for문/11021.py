import sys

n = int(input())

for x in range(n):
    a, b = map(int, sys.stdin.readline().split())
    print('Case #{0}: {1}'.format(x+1, a+b))



