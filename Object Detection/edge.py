#!/usr/bin/python
import cv2;
import matplotlib.pyplot as plt;
img=cv2.imread('/home/heerok/Pictures/test samples/test_edges.png');
row,col,ch=img.shape;

for i in range(row):
	for j in range(col):
		if(all(img[i,j]>=[127,0,0])):
			 img[i,j]=[255,255,255];

	

mplot=plt.imshow(img);
plt.show()

