#현재 그룹에 포함된 모험가의 수가 현재 확인하고 있는 공포도보다 크거나 같다면 이를 그룹으로 설정

N = 5
values = '3 3 3 4 2'

result = 0
val_list = [int(i) for i in values.split(' ')]

# while len(val_list) > 0:
#   target_val = min(val_list)
#   mate_list = [val for val in val_list if val <= target_val]
#   if target_val > len(mate_list):
#     val_list.remove(target_val)
#   else:
#     for i in range(target_val):
#       go = max(mate_list)
#       val_list.remove(go)
#       mate_list.remove(go)
#     result += 1

val_list.sort()
count = 0 #현재 그룹에 포함된 모험가의 수

for i in val_list:
  count += 1
  if count >= i:
    result += 1
    count = 0

print(result)

