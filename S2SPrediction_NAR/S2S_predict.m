
clear all;

% Import data- Windspeed(Column C)
% #samples-29761
windsp=xlsread('Dataset_A.xlsx','C1:C29761');
[seq]=num2cell(windsp)

% Transpose sequence into column-wise vector.
seq=seq';

% NAR architecture
% Feedback delays-3
% Hidden Layer- 4 neurons
net=narnet(1:3,4);

% Preparing Time-series data
[Xs,Xi,Ai,Ts]=preparets(net,{},{},seq);

% Training NAR net
net=train(net,Xs,Ts,Xi,Ai);
[Y,Xf,Af]=net(Xs,Xi,Ai);
mse=perform(net,Ts,Y)


% Prediction of Windspeed for next 30
% timesteps

timesteps=100;
[netcl,Xic,Aic]=closeloop(net,Xf,Af);
pred=netcl(cell(0,timesteps),Xic,Aic)

% For plotting
% total length= Ts+pred = 29757+20=29787

y1=cell2mat(Y');
temp1=cell2mat(pred');
y=[y1; temp1];
plot(temp1)


