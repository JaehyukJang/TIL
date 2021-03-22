#다만 두 수 중에서 하나라도 0 혹은 1인 경우, 곱하기보다는 더하기를 수행하는 것이 효율적
#두 수 중에서 하나라도 1 이하인 경우에는 더하며, 두 수가 모두 2 이상이 경우에는 곱

inputstr = '567'

result = 0

for i in inputstr:
  if result == 0:
    result += int(i)
  else:
    if i == '0':
      pass
    elif i == '1':
      result += 1
    else:
      result *= int(i)

print(result)