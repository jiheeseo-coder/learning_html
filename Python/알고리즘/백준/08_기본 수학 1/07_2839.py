n = int(input())

result = 1700
flag = False

for y in range(10001):
    if (n-5*y)%3 == 0 and (n-5*y)/3 >=0:
        x = (n-5*y)/3
        flag = True
        result = min(x+y, result)

if flag == True:
    print(int(result))
else:
    print(-1)

