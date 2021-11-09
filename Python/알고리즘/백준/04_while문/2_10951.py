import sys

flag=1

while flag==1:
    try:
        a,b=map(int, sys.stdin.readline().rsplit())
        if a is None or b is None:
            flag=0
        print(a+b)
    except ValueError:
        exit()
