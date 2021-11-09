
for dn in range(20, 10000+1):
    breakpoint = False
    printck = 0
    # if dn== 1:
    #     start = 1
    # else:
    #     start = int(dn/2)
    start = int(dn/2)
    for n in range(start, dn+1):
        sum = n
        while n>0:
            sum += int(n%10)
            n = int(n/10)
            if sum == dn:
                breakpoint = True
                printck +=1
                break
        if breakpoint == True:
            break
    if printck==0:
        print(dn)


