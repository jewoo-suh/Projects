import os
import time
import serial
import pyautogui
import tensorflow as tf
from tensorflow import keras
from keras.models import Sequential
from keras.layers import Dense, Flatten, Conv2D, MaxPooling2D, Dropout
from tensorflow.keras import layers
from keras.utils import to_categorical
import numpy as np
import matplotlib.pyplot as plt
plt.style.use('fivethirtyeight')
from keras.datasets import cifar10
from PIL import Image
from skimage.transform import resize

#Stuff I need to initialize
i = 0
pictureList = [] #This will be the place that eventually becomes the final product
indexList = [] #This takes care of the labels
classification = ['Apples', 'White Wall', 'Lemon', 'Orange', 'Peach', 'Coconut', 'Mint', 'Banana', 'Lavender', 'Ocean'] #This is what I can analyze


#Make list to NPArray
pictureNpArrayFinal = np.asarray(pictureList)
indexListFinal = np.asarray(indexList)

#print(pictureNpArrayFinal.shape)
#print(indexListFinal.shape)

(x_train, y_train), (x_test, y_test) = cifar10.load_data()
x_train = pictureNpArrayFinal
y_train = indexListFinal

y_train_one_hot = to_categorical(y_train)
y_test_one_hot = to_categorical(y_test)

x_train = x_train
x_test = x_test / 255

model = Sequential()
model.add(Conv2D(32, (5, 5), activation='relu', input_shape=(32,32,3)))
model.add(MaxPooling2D(pool_size=(2, 2)))
model.add(Conv2D(64, (5, 5), activation='relu'))
model.add(MaxPooling2D(pool_size=(2, 2)))
model.add(Flatten())
model.add(Dense(1000, activation='relu'))
model.add(Dropout(0.5))
model.add(Dense(500, activation='relu'))
model.add(Dropout(0.5))
model.add(Dense(250, activation='relu'))
model.add(Dense(10, activation='softmax'))

model.compile(loss='binary_crossentropy',
              optimizer='adam',
              metrics=['accuracy'])

hist = model.fit(x_train, y_train_one_hot,
           batch_size=256, epochs=10, validation_split=0.2 )
#model.evaluate(x_test, y_test_one_hot)[1]


new_image = plt.imread(r'C:\Users\Jewoo Suh\Desktop\Pics\MINT.jpg')

resized_image = resize(new_image, (32,32,3))
img = plt.imshow(resized_image)
predictions = model.predict(np.array( [resized_image] ))

list_index = [0,1,2,3,4,5,6,7,8,9]
x = predictions
for i in range(10):
  for j in range(10):
    if x[0][list_index[i]] > x[0][list_index[j]]:
      temp = list_index[i]
      list_index[i] = list_index[j]
      list_index[j] = temp
#Show the sorted labels in order from highest probability to lowest
print(list_index)

for i in range(10):
  print(classification[list_index[i]], ':', round(predictions[0][list_index[i]] * 100, 2), '%')

model.save('my_model.h5')


