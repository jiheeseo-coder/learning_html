



for dn in range(1, 100+1):
    breakpoint = False
    checkpoint = True
    if dn== 1:
        start = 1
    else:
        start = int(dn/2)
    for n in range(start, dn+1):
        cond = n
        count = 0
        tmp = 0
        while cond>0:
            add_n = int(cond%10)
            if n+add_n == dn and count ==0:
                breakpoint = True
                checkpoint = False
                count += 1
                break
            elif tmp+add_n == dn and count != 0:
                breakpoint = True
                checkpoint = False
                break
            else:
                cond = int(cond / 10)
                tmp = n+add_n
                count += 1
        if breakpoint==True:
            break
    if checkpoint == True:
        print(dn)
