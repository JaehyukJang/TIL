#주어진 N에 대하여 최대한 많이 나누길르 수행
#N의 값을 줄일 때 2 이상의 수로 나눈느 작업이 1을 빼는 작업보다 수를 훨씬 많이 줄일 수 있음
#K가 2 이상이기만 하면, K로 나누는 것이 1을 빼는 것보다 항상 빠르게 N을 줄일 수 있음

N = 25
K = 3

cnt = 0

# while N != 1:
#   if N % K == 0:

#     N = N/K
#     cnt += 1
#   else:
#     N -= 1
#     cnt +=1

# print(cnt)

while (N != 1) and (N > K):
  cnt += N - ((N // K) * K)
  N = N // K
  cnt += 1

cnt += N - 1

print(cnt)