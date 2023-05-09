import matplotlib.pyplot as plt
from math import e

def f(x, l):
  return e**(l * (x-1))-x

def ff(x, l):
  return l*e**(l* (x-1))-1

lam = range(1, 100)
lamy = []
temp = 0

for i in range(0, 99):
  for j in range (1, 20):
    temp = temp - f(temp, lam[i])/ff(temp, lam[i])
  lamy.append(temp)
  temp = 0

for i in range(0, 99):
  print(lamy[i])

plt.plot(lam, lamy)
plt.xlabel('lambda - axis')
plt.ylabel('y - axis')
plt.title('CM 4.1')
plt.show()
