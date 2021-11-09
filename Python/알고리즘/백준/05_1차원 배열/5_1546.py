import sys
n=int(input())
l = list(map(int, sys.stdin.readline().split()))
sum = 0

l.sort()
for i in l:
    sum += (i/l[-1]*100)
print(sum/len(l))


