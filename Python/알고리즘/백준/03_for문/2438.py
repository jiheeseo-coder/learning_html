n = int(input())

for x in range(n):
    for i in range(n-1-x):
        print(' ', end='')
    for i in range(x+1):
        print('*', end='')
    print()
