import sys

a, b = map(int, sys.stdin.readline().split())
while a+b != 0:
    print(a+b)
    a, b = map(int, sys.stdin.readline().split())
