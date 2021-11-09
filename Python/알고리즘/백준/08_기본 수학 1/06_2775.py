t = int(input())
for x in range(t):
    k = int(input())+1    # 층
    n = int(input())    # 호수

    a = [[0]*n for i in range(k)]

    for i in range(k):
        for j in range(n):
            if j == 0:      # 1호
                a[i][j] = 1
            else:           # 1호가 아닐 때
                if i == 0:  # 근데 0층일 때
                    a[i][j] = j+1
                else:       # 0층이 아닐 때
                    a[i][j] = a[i][j-1] + a[i-1][j]

    #print(a)
    print(a[k-1][n-1])

