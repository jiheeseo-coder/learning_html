
anw = [0 for i in range(19001)]

n = 0
while n < 10001:
    digit = n
    sum = 0
    while digit > 0:
        sum += int(digit % 10)
        digit = int(digit / 10)

    self_n = n+sum
    anw[self_n] = 1
    n += 1

anw = anw[0:10001]
for index, value in enumerate(anw):
    if value==0:
        print(index)


