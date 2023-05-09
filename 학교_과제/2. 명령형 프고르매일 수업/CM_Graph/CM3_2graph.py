import matplotlib.pyplot as plt

x= range(1,100)
y = [1/3, 1/12]

for r in range(2,99):
  y.append(9.0/4.0 * y[r-1] - y[r-2]/2)

plt.plot(x, y)
plt.xlabel('x - axis')
plt.ylabel('y - axis')
plt.title('CM 3.2')
plt.show()
