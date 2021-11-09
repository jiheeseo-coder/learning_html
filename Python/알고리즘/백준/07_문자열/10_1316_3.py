word_n = int(input())
total = 0

for x in range(word_n):
    word = input()
    count = 0
    check = []
    for j in word:  # 단어의 철자가 j
        if not check:  # 리스트가 비어있다면
            check.append(j)
        else:
            if check[-1] == j:
                continue
            else:
                check.append(j)
    # print(check)
    # 체크하기
    for x in range(len(check)):
        if len(check) == 1:
            count = 0
        else:
            # print(check[x], " 와 ", check[x + 1:], "을 비교중")
            if check[x] in check[x + 1:]:
                count += 1

    if count == 0:
        total += 1

print(total)