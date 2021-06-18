#import sys

#input_list=[]
#sys.stdin=open('input.txt', 'r')
'''
for i in range(10):
    for j in range(9):
        T=input()
        input_list.append(T)
   '''

T=10
#T=int(input())
for test_case in range(1, T+1):
    N = []
    for i in range(9):
        T=input()
        N.append(T)
    count = 0
    num = int(N[0][0])
    length = len(N[1])
    for i in range(1, 9):
        # print('N[',i,']: ',N[i])
        for j in range(length - num + 1):
            mini_count_r = 0
            mini_count_c = 0
            for k in range(int(num / 2)):
                # 가로
                if N[i][j + k] == N[i][num + j - k - 1]:
                    mini_count_r += 1
                    # print('[r]i: ', i, ', j: ',j,', k:',k,', matrix: ',N[i][j+k], ', ',N[i][num+j-k-1])
                if N[j + 1 + k][i - 1] == N[num + j - k][i - 1]:
                    mini_count_c += 1
                    # print('[c]i: ', i, ', j: ', j, ', k:', k, ', matrix: ', N[j + 1+k][i-1], ', ', N[num + j -k][i-1])
            if mini_count_r == int(num/2):
                count += 1
            if mini_count_c == int(num/2):
                count += 1
        # print('count: ',count)

    print('#', test_case, ' ', count)
