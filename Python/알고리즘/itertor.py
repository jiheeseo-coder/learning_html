l1 = [1,2,3,4,5]
l2=['a', 'b', 'c', 'd']
l3 = ["무야호","정말","즐거워", "사랑해"]
l4 = [10]

l_sum=[]

l_sum.append(l1)
l_sum.append(l2)
l_sum.append(l3)
l_sum.append(l4)

print('print l_sum : ',l_sum)
iterlist = iter(l_sum)
print('iterable 하게 만들어봤다!')

for i in range(4):
    print('print list next: ', next(iterlist))

print(l_sum[0])