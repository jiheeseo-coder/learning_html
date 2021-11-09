l=[]

for i in range(9):
    a=int(input())
    l.append(a)

l2 = l.copy()
l2.sort()

count = 1
for x in l:
    if x!=l2[-1]:
        count += 1
    else:
        break

print(l2[-1])
print(count)