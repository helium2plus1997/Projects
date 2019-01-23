#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Tue Jan 22 00:51:09 2019

Version 1.0
@author: heerokbanerjee
contact-heerok.banerjee@hotmail.com
www.heerokbanerjee.in
"""
import numpy as np
from pyspark.ml.classification import DecisionTreeClassifier
from pyspark.ml.feature import VectorAssembler

from pyspark.sql.session import SparkSession
from pyspark.context import SparkContext

import xgboost as xgb
#from xgboost.sklearn import XGBClassifier
from sklearn import model_selection
from sklearn.metrics import accuracy_score
from sklearn.metrics import mean_squared_error

sc = SparkContext('local')
spark = SparkSession(sc)


### Functions
###

#*** To read CSV Files
def spark_read(filename):
        file = spark.read.format("csv").option("header", "true").load(filename)
        return file
#*** To convert Dataframe to compatible types    
def convert_to_numeric(data):
    for x in ["A","B","C","D","E"]:
        data = data.withColumn("Fea"+x, data["Fea"+x].cast('double'))
    try:
        data=data.withColumn("Label",data["Label"].cast('int'))
    finally:
        data.drop("Timestamp")
        return data

### Importing training Dataset; filename-train.csv
### Casting DF from [string,string,...] to [string, double,....]
###
data_df = spark_read("mle_task/train.csv")
data_df = convert_to_numeric(data_df)

#data_df.printSchema()

### PipelineComp1- Vector Assembler
### Convert DF to Vector.<FeaA, FeaB.....>
###
vec_assembler=VectorAssembler(
        inputCols=["FeaA","FeaB","FeaC","FeaD","FeaE"],
        outputCol="vecFea",handleInvalid="skip")

input_vec=vec_assembler.transform(data_df)
#input_vec.select("vecFea").show()
input_vec=input_vec.drop("FeaA","FeaB","FeaC","FeaD","FeaE")

#input_vec.select("vecFea").show()


### XGBoostClassifier Model
###  
###
params = {
    'max_depth': 5, 
    'eta': 0.3,  
    'silent': 1,
    'num_class': 5,
    'objective': 'multi:softprob'} 
     

features_x=np.array(input_vec.select("vecFea").collect())
labels_y=np.array(input_vec.select("Label").collect())
features_x=np.squeeze(features_x,axis=1)
X_train,X_test,Y_train,Y_test=model_selection.train_test_split(features_x,labels_y,test_size=0.33,random_state=123)


xgb_train = xgb.DMatrix(X_train, label=Y_train)
xgb_test = xgb.DMatrix(X_test, label=Y_test)

#xgbmodel=XGBClassifier()
xgbmodel = xgb.train(params, xgb_train,100)
print(xgbmodel)

xgb_output=xgbmodel.predict(xgb_test)
print(xgb_output)

predictions = np.asarray([np.argmax(line) for line in xgb_output])
print(predictions)

### Determining Accuracy Score
###
accuracy = accuracy_score(Y_test, predictions)
print("Accuracy: %.2f%%" % (accuracy * 100.0))

### Determining Mean Squared Error
###
mse = mean_squared_error(Y_test, predictions)
print("MSE: ",mse)




