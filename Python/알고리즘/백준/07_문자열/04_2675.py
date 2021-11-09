t = int(input())
for i in range(t):
    r, s = input().split()
    r = int(r)
    s = list(s)

    for x in s:
        for j in range(r):
            print(x,end='')
    print()
