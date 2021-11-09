



for dn in range(13, 100+1):
    breakpoint = False
    checkpoint = True
    if dn== 1:
        start = 1
    else:
        start = int(dn/2)
    #for n in range(start, dn+1):
    for n in range(11, dn + 1):
        cond = n
        tmp = 0
        while cond>0:
            add_n = int(cond%10)
            if tmp+cond+add_n == dn:
                breakpoint = True
                checkpoint = False
                break
            else:
                tmp = n+add_n
                cond = int(cond / 10)
        if breakpoint==True:
            break
    if checkpoint == True:
        print(dn)
