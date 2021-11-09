import sys

testcase = int(input())

for x in range(testcase):
    a = list(map(int, sys.stdin.readline().split()))

    sum = 0
    for i in range(1, a[0]+1):
        sum += a[i]
    avg = sum/a[0]

    count = 0
    for i in range(1, a[0]+1):
        if a[i] > avg:
            count += 1
    print('{0:0.3f}%'.format(count/a[0]*100))