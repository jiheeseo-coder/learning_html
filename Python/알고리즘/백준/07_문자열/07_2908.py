l = list(map(int, input().split()))

def reverse(n):
    r_sum = 0
    tmp = n
    for x in [100, 10, 1]:
        r_sum += tmp%10 * x
        tmp = tmp//10
    return r_sum

n1 = reverse(l[0])
n2 = reverse(l[1])
if  n1> n2:
    print(n1)
else:
    print(n2)

