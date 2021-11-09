a = int(input())
x = int(input())

x100 = int(x/100)
x10 = int((x%100)/10)
x = (x%100)%10

print(a*x)
print(a*x10)
print(a*x100)
print(a*x+int(a*x10*10)+int(a*x100*100))