import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

input_file = "Train_Sample_WInd_Data.csv"
df = pd.read_csv(input_file)
WindSpeed = np.array(df.ix[:, 'WindSpeed'])	
df['WindSpeed'] = df['WindSpeed'].replace([np.NaN,0.0], -99)
df.to_csv('a.csv')
WindSpeed = np.array(df.ix[:, 'WindSpeed'])
#WindSpeedByDay = np.reshape(WindSpeed, (-1,96))

WindSpeedByTimeAvg=[]
for x in range(96):
	time_sum=0
	count=0
	for y in range(x, len(WindSpeed), 96):
		if WindSpeed[y]!=-99:
			time_sum+=WindSpeed[y]
			count+=1
	WindSpeedByTimeAvg.append(time_sum/count)
print(np.max(WindSpeedByTimeAvg))
print(np.min(WindSpeedByTimeAvg))



WindSpeedByTime= np.zeros((96,513))
for x in range(len(WindSpeed)):
	if WindSpeed[x]!=-99:
		WindSpeedByTime[x%96][x//96]=WindSpeed[x]
	else:
		WindSpeedByTime[x%96][x//96]=WindSpeedByTimeAvg[x%96]

WindSpeedByTimeAvg=[]
WindSpeedByTimeStd=[]
y=[]
for x in range(96):
	if x%4==0:
		y.append(str(x//4)+':'+str((x%4)*15))
	WindSpeedByTimeStd.append(np.std(WindSpeedByTime[x]))
	WindSpeedByTimeAvg.append(np.mean(WindSpeedByTime[x]))
my_xtick=np.arange(0,96,4)
plt.plot(np.arange(96), WindSpeedByTimeAvg)
plt.plot(np.arange(96), WindSpeedByTimeStd, '--')
plt.xlabel('Hours of the Day')
plt.xticks(my_xtick,y, rotation=45)
plt.legend(['Wind Speed Avg', 'Wind Speed Var'])
plt.show()

