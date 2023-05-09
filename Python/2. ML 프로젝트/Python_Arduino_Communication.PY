#Importing all the shit I need
import numpy as np
import pyautogui
import matplotlib.pyplot as plt
import time
import serial
plt.style.use('fivethirtyeight')
from skimage.transform import resize
from keras.models import load_model
model = load_model('my_model.h5')
ser = serial.Serial('COM8', 9600)

classification = ['Apple', 'White Wall', 'Lemon', 'Orange', 'Peach', 'Coconut', 'Mint', 'Banana', 'Lavender', 'Ocean'] #This is what I can analyze


for i in range(15):
    myScreenshot = pyautogui.screenshot()
    myScreenshot.save(r'C:\Users\Jewoo Suh\Desktop\Pics\ ' + str(i) + str(i) + str(i) + '.png')
    new_image = plt.imread(r'C:\Users\Jewoo Suh\Desktop\Pics\ ' + str(i) + str(i) + str(i) + '.png')  # Read in the image (3, 14, 20)
    img = plt.imshow(new_image)

    resized_image = resize(new_image, (32, 32, 3))
    img = plt.imshow(resized_image)
    predictions = model.predict(np.array([resized_image]))

    list_index = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
    x = predictions
    for i in range(10):
        for j in range(10):
            if x[0][list_index[i]] > x[0][list_index[j]]:
                temp = list_index[i]
                list_index[i] = list_index[j]
                list_index[j] = temp
    # Show the sorted labels in order from highest probability to lowest
    print(list_index)
    print("This is a " + classification[list_index[0]])

    if (list_index[0] == 0):
        print(classification[list_index[0]] + " was detected")
        time.sleep(0.1)
        ser.write(b'A')
    if (list_index[0] == 1):
        print(classification[list_index[0]] + " was detected")
        time.sleep(0.1)
        ser.write(b'B')
    if (list_index[0] == 2):
        print(classification[list_index[0]] + " was detected")
        time.sleep(0.1)
        ser.write(b'C')
    if (list_index[0] == 3):
        print(classification[list_index[0]] + " was detected")
        time.sleep(0.1)
        ser.write(b'D')
    if (list_index[0] == 4):
        print(classification[list_index[0]] + " was detected")
        time.sleep(0.1)
        ser.write(b'E')
    if (list_index[0] == 5):
        print(classification[list_index[0]] + " was detected")
        time.sleep(0.1)
        ser.write(b'F')
    if (list_index[0] == 6):
        print(classification[list_index[0]] + " was detected")
        time.sleep(0.1)
        ser.write(b'G')
    if (list_index[0] == 7):
        print(classification[list_index[0]] + " was detected")
        time.sleep(0.1)
        ser.write(b'H')
    if (list_index[0] == 8):
        print(classification[list_index[0]] + " was detected")
        time.sleep(0.1)
        ser.write(b'I')
    if (list_index[0] == 9):
        print(classification[list_index[0]] + " was detected")
        time.sleep(0.1)
        ser.write(b'J')