mylist=[list(map(int,input().split())) for _ in range(4)]

count = 1
for row in mylist:
    sum = 0
    for number in row:
        sum += number
    print('%dclass : %d' %(count, sum))
    count += 1

