#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Wed Jan 23 12:55:15 2019

@author: heerokbanerjee
"""

import pandas as pd
import numpy as np
import xgboost as xgb


from sklearn.metrics import accuracy_score
from sklearn.metrics import mean_squared_error
from sklearn.model_selection import train_test_split


filename_train="data/Train_Sample_WInd_Data.csv"
data_fr=pd.read_csv(filename_train)
data_fr.fillna(data_fr.mean(), inplace=True)
data_fr=data_fr.drop(columns=["DateTime"])
print(data_fr)



features_x=data_fr["WindSpeed"].values
labels_y=data_fr["PowerGenerated"].values

    
x_train, x_test, y_train, y_test=train_test_split(features_x,labels_y,test_size=0.12,random_state=123) 

x_train=np.array(x_train).reshape((-1,1))
y_train=np.array(y_train).reshape((-1,1))
x_test=np.array(x_test).reshape((-1,1))
y_test=np.array(y_test).reshape((-1,1))

print(x_train.shape)
print(y_train.shape)


xgb_train = xgb.DMatrix(x_train, label=y_train)
xgb_test = xgb.DMatrix(x_test, label=y_test)

params = {'max_depth':1,
'silent':0,
'min_child_weight':10,
'learning_rate':0.01,
'subsample':0.8,
'colsample_bytree':0.3,
'gamma':1,
'obj':'multi:softprob',
'n_estimators':1000,
'eta':0.3}

xg_reg = xgb.XGBRegressor(**params)

xg_reg.fit(x_train, y_train,eval_metric="rmse")
   
predictions=xg_reg.predict(x_test)

print(predictions)

### Determining Mean Squared Error
###
mse = mean_squared_error(y_test, predictions)
print("MSE: ",mse)

### Predicting for Test dataset
filename_train="data/Test_forecast_data.csv"
data_fr=pd.read_csv(filename_train)
data_fr.fillna(data_fr.mean(), inplace=True)
data_fr=data_fr.drop(columns=["DateTime"])

