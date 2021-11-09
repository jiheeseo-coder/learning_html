x = int(input())

a = 1.0
b = 1.0
c = -(2*x)

n = (-b + (b ** 2 - 4 * a * c) ** 0.5) / (2 * a)
#print(n)

if n%1 > 0:
    n = int(n) + 1
else:
    n = int(n)

#print(n)

l = int(n * (n + 1) / 2)
#print(l,'\n')

count = 0
r1, r2 = 0,0
for i in range(l - n + 1, l + 1):
    count += 1
    #print(i, count)
    if i == x:
        r1 = count
        r2 = n - count + 1

if (n + 1) % 2 == 0:
    r1, r2 = r2, r1

print('{}/{}'.format(r1,r2))


