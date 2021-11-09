n = int(input())

for x in range(n):
    l = []
    a = input()
    for y in a:
        if y=='O':
            l.append(1)
        else:
            l.append(0)

    for y in range(len(a)):
        if y>=1:
            if l[y-1]!=0 and l[y]!=0:
                l[y] = l[y-1]+1

    sum = 0
    for y in l:
        sum +=y
    print(sum)