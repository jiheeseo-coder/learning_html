a = int(input())

count = 0
result = 100
n=a
while a!=result:
    sum = int(n/10)+n%10
    result = (n%10)*10 + sum%10
    count +=1
    n=result

print(count)


