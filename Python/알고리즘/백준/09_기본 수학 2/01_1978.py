import sys

n = int(input())
t = list(map(int, sys.stdin.readline().split()))
result = 0
for n in t:
    count = 0

    for i in range(1, n+1):
        if n%i == 0:
            count += 1

    if count == 2:
        result += 1

print(result)