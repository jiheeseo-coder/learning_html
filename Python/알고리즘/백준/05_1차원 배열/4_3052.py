l = []

for x in range(10):
    a = int(input())
    n = a%42
    if n not in l:
        l.append(n)

print(len(l))
