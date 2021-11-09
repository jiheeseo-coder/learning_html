a = input()
t = 0
for x in a:
    if x in 'ABC': t += 2
    elif x in 'DEF': t += 3
    elif x in 'GHI': t += 4
    elif x in 'JKL': t += 5
    elif x in 'MNO': t += 6
    elif x in 'PQRS': t += 7
    elif x in 'TUV': t += 8
    elif x in 'WXYZ': t += 9

print(t+len(a))
