#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Wed Jan 23 02:12:41 2019


@author: heerokbanerjee
contact-heerok.banerjee@hotmail.com
www.heerokbanerjee.in
"""

import numpy as np

from pyspark.ml import Pipeline
from pyspark.ml.feature import VectorAssembler
from pyspark.ml.feature import StringIndexer
#from pyspark.ml.classification import DecisionTreeClassifier
from pyspark.sql.session import SparkSession
from pyspark.context import SparkContext

from sklearn import model_selection
from sklearn.metrics import accuracy_score

import xgboost as xgb

sc = SparkContext('local')
spark = SparkSession(sc)

fname_train = "mle_task/train.csv"
fname_test = "mle_task/test.csv"

def spark_read(filename):
        file = spark.read.format("csv").option("header", "true").load(filename)
        return file

def convert_to_numeric(data):
    for x in ["A","B","C","D","E"]:
        data = data.withColumn("Fea"+x, data["Fea"+x].cast('double'))
    return data
    
### Import Training dataset
train_data = spark_read(fname_train)
train_data=convert_to_numeric(train_data)

### Pipeline Component1
### String Indexer for Column "Timestamp"
###
timeIndexer = StringIndexer(
        inputCol="Timestamp",
        outputCol="TimeIndex",handleInvalid="keep")
#print(strIndexer.getOutputCol())
out1 = timeIndexer.fit(train_data).transform(train_data)
#indexer_out.show()

### Pipeline Component2
### String Indexer for Column "Label"
###
labelIndexer = StringIndexer(
        inputCol="Label",
        outputCol="IndexLabel",handleInvalid="keep")
#print(strIndexer.getOutputCol())
#out2 = labelIndexer.fit(train_data).transform(train_data)

### Pipeline Component2
### VectorAssembler
###
vecAssembler = VectorAssembler(
        inputCols=["TimeIndex","FeaA","FeaB","FeaC","FeaD","FeaE"],
        outputCol="vecFea",handleInvalid="skip")
#assembler_out = vecAssembler.transform(indexer_out)
#assembler_out.select("vecFea").show(truncate=False)

### Pipeline Component3
### GBT Classifier
#dt_class=DecisionTreeClassifier(labelCol="IndexLabel", featuresCol="vecFea")

### Training- Pipeline Model
### 
pipe=Pipeline(stages=[timeIndexer,labelIndexer,vecAssembler])
pipe_model=pipe.fit(train_data)

output=pipe_model.transform(train_data)
out_vec=output.select("TimeIndex","vecFea").show(10)


### XGBoostClassifier Model
###  
###
params = {'max_depth':2,
          'silent':0,
          'learning_rate':0.38,
          'objective':'multi:softprob',
          'num_class':5}

features_x=np.array(output.select("vecFea").collect())
labels_y=np.array(output.select("Label").collect())
features_x=np.squeeze(features_x,axis=1)
X_train,X_test,Y_train,Y_test=model_selection.train_test_split(features_x,labels_y,test_size=0.51,random_state=123)


xgb_train = xgb.DMatrix(X_train, label=Y_train)
xgb_test = xgb.DMatrix(X_test, label=Y_test)

#xgbmodel=XGBClassifier()
xgbmodel = xgb.train(params, xgb_train,10)
print(xgbmodel)

### Testing Pipeline + XGBoostClassifier
###

### Import Test Data
temp = spark_read(fname_test)
test_data=convert_to_numeric(temp)

test_output=pipe_model.transform(test_data)

xgb_output=xgbmodel.predict(xgb_test)
print(xgb_output)

predictions = np.asarray([np.argmax(line) for line in xgb_output])
print(predictions)

### Determining Accuracy Score
###
accuracy = accuracy_score(Y_test, predictions)
print("Accuracy: %.2f%%" % (accuracy * 100.0))





