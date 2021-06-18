
num_list = list(map(int, input().split()))

min = 10000
max = 0

for x in num_list:
    if 100 >= x:
        if max < x:
            max = x
    else:
        if min > x:
            min = x

if min == 10000:
    min = 100
elif max == 0:
    max = 100

print(max, min)