a=int(input())
b=int(input())
c=int(input())
l = [0 for i in range(10)]

result = a*b*c

while result != 0:
    x = result%10
    l[x] += 1
    result = int(result/10)

for x in l:
    print(x)