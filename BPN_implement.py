#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Mon Sep 10 19:24:52 2018
@author: heerokbanerjee
"""

import numpy as np

print("*************BACKPROPAGATION NEURAL NETWORK IMPLEMENTATION*************")
print("Constant Parameters-")
print("Number of Input Nuerons- 2")
print("Number of Hidden Layer Neurons-3")
print("Number of Output Nuerons- 1")


#Input of Weight Matrix , W1:

print("Enter Input-->Hidden Layer Weights (3*2)-")

W1=[[0 for x in range(3)] for y in range(2)] 
for i in range(0,2):
    for j in range(0,3):
        W1[i][j]=input("Enter Weight ("+str(i)+","+str(j)+"):")

#Input of Weight Matrix , W2:        
        
print("Enter Hidden-->Output Layer Weights (3*1)-")

W2=[[0 for x in range(1)] for y in range(3)] 
for i in range(0,3):
    for j in range(0,1):
        W2[i][j]=input("Enter Weight ("+str(i)+","+str(j)+"):")
        

#Training Examples for getting expected output
X = np.asarray(([4, 2], [8, 3], [9, 12]), dtype=float)
y = np.array(([87], [63], [49]), dtype=float)


#Scaling to 0.xx form
X = X/np.amax(X, axis=0)
y = y/100 

class BackPropNeuralNetwork(object):
  def __init__(self):
   
    self.W1 = np.asarray(W1, dtype=np.float32)
    self.W2 = np.asarray(W2, dtype=np.float32)

  def NetInput(self, X):
    
    self.DotProd = np.dot(X, self.W1)
    self.DotProd2 = self.sigmoid(self.DotProd) 
    self.DotProd3 = np.dot(self.DotProd2, self.W2) 
    o = self.sigmoid(self.DotProd3)
    return o 

  def sigmoid(self, s):
  
    return 1/(1+np.exp(-s))

  def sigmoidPrime(self, s):

    return s * (1 - s)

  def BackPropagate_Error(self, X, y, op_error):
  
    self.output_error = y - op_error
    self.op_delta = self.output_error*self.sigmoidPrime(op_error)

    self.DotProd2_error = self.op_delta.dot(self.W2.T)
    self.DotProd2_delta = self.DotProd2_error*self.sigmoidPrime(self.DotProd2) 
    self.W1 += X.T.dot(self.DotProd2_delta) 
    self.W2 += self.DotProd2.T.dot(self.op_delta) 

    
  def trainNeuralNetwork (self, X, y):
    op_HidToOut = self.NetInput(X)
    self.BackPropagate_Error(X, y, op_HidToOut)

NN = BackPropNeuralNetwork()

#learning parameters, threshold value & Initial residual error set to 1.
threshold=0.0005
mse=1
max_iterations=100000

for i in range(max_iterations) or mse<threshold :
    
  print("Input Matrix: \n" + str(X))
  print("Desired Output: \n" + str(NN.NetInput(X))) 
  print("Actual Output: \n" + str(y)) 

  mse=np.mean(np.square(y - NN.NetInput(X)))
  print("Error: \n" + str(mse))
  NN.trainNeuralNetwork(X, y)
    

print("Weight Matrix,W1 (Input --> Hidden Layer) :")
print(NN.W1)


print("Weight Matrix,W2 (Hidden --> Output Layer) :")
print(NN.W2)
