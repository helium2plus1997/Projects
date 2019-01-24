### Heerok Banerjee
###

1. "xgbclass_timeSeries_model.py" for model evaluation.
2. "xgbclass_timeSeries_run.py" for runtime evaluation.

### Discussions
###

1. Briefly describe the conceptual approach you chose! What are the trade-offs?

The approach I employed can be categorically represented as : Preprocessing + Dimensionality Reduction + Classification + Parameter Tuning.

For Pre-processing the data, I used StringIndexer to label 'Timestamp';
But for an optimized runtime perforace, I just dropped 'Timestamp' from dataframe.
Consequently, I employed VectorAssembler to assembler five features as one VectorArray. 
I modelled a xgb.XGBoostClassifier and trained the model with the given training dataset.
Then, I moderated the learning parameters to optimize the model performance.


Trade-offs:
	1. The Model is not robust against impurities; Pyspark Imputer transformer can be used for that purpose. 
	Additionally, the model will predict less accurately for outliers.

	2. The model assumes that the system is linear; Hence, the model accuracy will be low if there are inter-dependencies 		between the features.

2. What's the model performance?

The model performance is convincingly good.
Accuracy of the model is 99.25%. And, the root mean square error is 0.0479,which is convincing.
However, I feel that the learning parameters are overcalibarated which migh lead to poor runtime
performance.

3. What's the runtime performance? What is the complexity? Where are the bottlenecks?

Runtime Performance is poor. The pipeline model is consistent, although some of the test 
samples are impure. But, the XGBoostCLassifier consumes a lot of time. Possibly, 
because of improper paramters.

4. If you had more time, what improvements would you make, and in what order of priority?

	1. I would have classified with neural networks, preferably with an LSTM model.

	2. I would have fine-tuned the learning parameters as per the sample size.
