n1, n2, n3 = map(int, input().split())

def maxNum(n1,n2,n3):
    a = n1
    if n1 > n2:
        if n1>n3:
            a = n1
        else:
            a = n3
    else:
        if n2>n3:
            a = n2
        else:
            a = n3
    return a


result = maxNum(n1, n2, n3)
print(result)