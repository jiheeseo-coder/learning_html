import sys
import math
a, b, c = map(int, sys.stdin.readline().split())

x = (c-b)/(a-b)
print(math.ceil(x))
