import sys
import math

a = int(input())
for i in range(a):
    h, w, n = map(int, sys.stdin.readline().split())
    yy = n%h
    if yy == 0:
        yy = h
    xx = math.ceil(n/h)
    print(yy*100+xx)




