word_n = int(input())
word = []
for x in range(word_n):
    word.append(input())
count = 0
total = 0

# 하나씩 집어넣기
for i in word:         #단어 하나하나가 i
    check = []
    for j in i:         # 단어의 철자가 j
        if not check:       #리스트가 비어있다면
            check.append(j)
        else:
            if check[-1] == j:
                continue
            else:
                check.append(j)
    #print(check)
    # 체크하기
    for x in range(len(check)):
        if len(check) == 1:
            count = 0
        else:
            #print(check[x], " 와 ", check[x + 1:], "을 비교중")
            if check[x] in check[x+1:]:
                count += 1

    if count == 0:
        total +=1

print(total)