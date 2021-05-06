#include <stdio.h>

int main(void) {
	
	int a, b;
	int sum;

	double x, y;
	double diff;

	a = 30;
	b = 40;
	sum = a + b;

	x = 38.4;
	y = 45.5;
	diff = x - y;

	printf("==============================\n");
	printf("\t합과 차 구하기\n");
	printf("==============================\n");
	printf("\t합 : %d\n", sum);
	printf("\t차 : %.1f\n", diff);
	printf("==============================\n\n");


}