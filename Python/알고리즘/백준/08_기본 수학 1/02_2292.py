a = int(input())

if a == 1:
    print(1)
else:
    if ((a - 1) % 6) != 0:
        n = ((a - 1) // 6) + 1
    else:
        n = ((a-1)//6)

    sum = 0
    for i in range(1, n + 1):
        sum += i
        if n <= sum:
            print(i + 1)
            break