# Projects

## This repository contains all my academic projects

### 1.Helium ChatBot
This chatbot application is a native android application that uses opensource SimSimi Chatbot API to train the system for user-friendly chatbot interaction.The application uses two seperate Messenger model.
* 1.The SimSimi ChatModel- The SimiSimi chat model is used to create an instance of a specific format of HTTPRequest that connect with the simsimi api interface.The API interface is accesible via the link - http://sandbox.api.simsimi.com/request.p?key=%s&lc=en&ft=1.0&text=%s".

* 2.The Custom ChatModel- The Custom Chat Model is used to parse the SimSimi Chat Model into a more generalized format.For example, the Custom chat model only contains 2 attribute namely, the user message and a boolean value for response.This model is then used to display the API response into a bubble text.


### 2.Object Detection
This project is based on python3 using numpy,matplotlib and opencv which uses Haar Cascade Classfiers to detect face mesh.I have used a training algorithm to train the system to detect individual faces from a reference dataset, which consists of a list of Haar Cascade classifiers for n number of individuals.

### 3. Time-Series Classification using XGBoost Classifier
In this project, I have used ML pipeline architectures to distribute sequencial preprocessing using Apache PySpark library. The pipeline consists of [LabelIndexer-->StringIndexer-->VectorAssembler-->PCA]--> XGBoostClassifer. I have used xgb.XGBoostClassifier to perform classification on normalized vectors. The model accuracy was 98.52%.
