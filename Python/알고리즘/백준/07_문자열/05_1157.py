
a = list(input())
dic = {}

for x in a:
    if x.islower():
        tmp = x.upper()
    else:
        tmp = x

    if tmp in dic:
        dic[tmp] += 1
    else:
        dic[tmp] = 1

s_dic = sorted(dic.items(), key = lambda x:x[1], reverse=True)

if len(s_dic)>=2:
    if s_dic[0][1] == s_dic[1][1]:
        print('?')
    else:
        print(s_dic[0][0])
else:
    print(s_dic[0][0])


