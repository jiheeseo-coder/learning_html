n = int(input())

result = [i for i in range(n//5+1)]
result.reverse()
flag = False
r = 1700

for y in result:
    if (n-5*y)%3 == 0 and (n-5*y)/3 >=0:
        x = (n-5*y)/3
        flag = True
        check = min(x+y, r)

if flag == True:
    print(int(check))
else:
    print(-1)

