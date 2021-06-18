d1, d2 = map(int, input().split())
f1, f2 = map(float, input().split())


def int_compare(d1, d2):
    if abs(d1) > abs(d2):
        print(d1)
    else:
        print(d2)

def flo_compare(f1, f2):
    if abs(f1) > abs(f2):
        print("%.2f" %f2)
    else:
        print("%.2f" %f1)

int_compare(d1, d2)
flo_compare(f1, f2)