import numpy as np
from numpy.linalg import inv

def GAA(x):
  return 1/(2.0-x)

def GAB(y):
  return 1/(2.0-y)

def GBA(x):
  return 1/(3.0-2.0*x)

def GBB(y):
  return 2.0/(3.0-y)

def J11(x,y):
  return 1/((2.0-x)**2 * (2.0-y)) - 1.0

def J12(x, y):
  return 1/((2.0-x) * (2.0-y)**2)

def J21(x, y):
  return 4.0/((3.0-2.0*x)**2 * (3.0-y))

def J22(x, y):
  return 2.0/((3.0-2.0*x) * (3.0-y)**2) - 1.0

n = range(1,100)
xn = [ np.array([[0],[0]]) ]

def J(x, y):
  return np.array([[J11(x, y), J12(x, y)], [J21(x, y), J22(x, y)]])

def f(x, y):
  return np.array(([[GAA(x)*GAB(y) - x], [GBA(x) * GBB(y) - y]]))

X = J(1, 1)
print(xn[0][1][0])

for r in range(1,99):
  A = J(xn[r-1][0][0], xn[r-1][1][0])
  B = f(xn[r-1][0][0], xn[r-1][1][0])
  C = np.linalg.solve(A, -B)
  xn.append(C + xn[r-1])

for i in range(0, 99):
  print(xn[i])


