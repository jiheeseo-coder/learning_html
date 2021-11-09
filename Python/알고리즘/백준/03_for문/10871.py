import sys

x, n = map(int, input().split())
a = list(map(int,sys.stdin.readline().split()))

for i in a:
    if i < n:
        print(i, end=' ')
