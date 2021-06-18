mylist = [0 for _ in range(4)]

for i in range(4):
    print('%dclass?' %(i+1))
    mylist[i]= list(map(int,input().split()))

count = 1
for row in mylist:
    sum = 0
    for number in row:
        sum += number
    print('%dclass : %d' %(count, sum))
    count += 1
