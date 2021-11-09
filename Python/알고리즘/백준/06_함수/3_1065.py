n = int(input())
result = 0

def arthmetic (start, n):
    list = []
    result = 0
    for i in range(start, n+1):
        count = 0
        while i > 0:
            list.append(int(i%10))
            i = int(i/10)
            count +=1
        list.reverse()
        if (list[2]-list[1]) == (list[1]-list[0]):
            result +=1
    return result

count = 0
tmp = n
while tmp>0:
    tmp = int(tmp/10)
    count += 1
if count == 4:
    result = 99
    result += arthmetic(100, 999)
if count == 3:
    result = 99
    result += arthmetic(100, n)
if count <= 2:
    result = n

print(result)