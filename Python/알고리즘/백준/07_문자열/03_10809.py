word = list(map(str, input()))

result_list = [-1 for i in range(26)]

for x in word:
    idx = ord(x)-ord("a")
    if result_list[idx] == -1:
        result_list[idx] = word.index(x)

for x in result_list:
    print(x, end=" ")


