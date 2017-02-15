#!/usr/bin/python
import cv2;
import numpy as np;
from matplotlib import pyplot as plt;
from PIL import Image;



def threshold(ImageArray):
	
	balancear=[];
	newar=ImageArray;

	for eachrow in ImageArray:
		for eachpix in eachrow:
			avgnum=reduce(lambda x,y: x+y, eachpix[:3])/len(eachpix[:3]);
			balancear.append(avgnum);
	balance=reduce(lambda x,y:x+y, balancear)/len(balancear);


	for eachrow in ImageArray:
			for eachpix in eachrow:
				if reduce(lambda x,y:x+y, eachpix[:3])/len(eachpix[:3])> balance:
					eachpix[0]=255;
					eachpix[1]=255;
					eachpix[2]=255
				else:
					eachpix[0]=0;
					eachpix[1]=0;
					eachpix[2]=0;
			

	return newar;

img=Image.open('/home/heerok/Pictures/test samples/test.png');
imgar=np.array(img);
img_th=threshold(imgar);
iplot=plt.imshow(img_th);
plt.show();
	
