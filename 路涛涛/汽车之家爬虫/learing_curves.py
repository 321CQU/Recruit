# based on 2_9 car.py
# goal: generate validation curves about classifier's performance

import numpy as np
from sklearn import preprocessing
from sklearn.ensemble import RandomForestClassifier
from sklearn import model_selection
import matplotlib.pyplot as plt
import csv
import random
input_file = 'car.data1.txt'

# Reading the data
X = []
y = []

with open(input_file, 'r') as f:
    for line in f.readlines():
        data = line[:-1].split(',')
        X.append(data)

X = np.array(X)

# 标记编码
label_encoder = []
X_encoded = np.empty(X.shape)
for i,item in enumerate(X[0]):
    label_encoder.append(preprocessing.LabelEncoder())
    X_encoded[:, i] = label_encoder[-1].fit_transform(X[:, i])

X = X_encoded[:, :-1].astype(int)
y = X_encoded[:, -1].astype(int)


# 森林中树的数量： 200
# 决策树 划分选择： 信息熵
# 决策树的最大深度： 10
# 控制从原始的数据集中采取有放回的抽样  参数=10


params = {"n_estimators":200, "criterion":'entropy', "max_depth":10, "random_state":10}

classifier = RandomForestClassifier(**params)
classifier.fit(X, y)

# cross validation
accuracy = model_selection.cross_val_score(classifier, X, y,scoring='accuracy', cv=10)
print("Accuracy of the classifier: " + str(round(100*accuracy.mean(), 2)) + "%")

input_data=[]
test_path='cars5.csv'
with open(test_path, 'r',encoding='utf-8') as csvfile:
    csv_reader=csv.reader(csvfile)
    header=next(csv_reader)
    for line in csv_reader:
        input_data.append(line)
input_data = np.array(input_data)
input_data_encoded = np.empty(input_data.shape)
for i,item in enumerate(input_data[0]):
    input_data_encoded[:, i] = label_encoder[i].transform(input_data[:, i])

input_data = input_data_encoded[:, :].astype(int)

# 使用单一数据样例进行检验
# input_data = ['vhigh', 'vhigh', 'small', 'low']
# input_data_encoded = [-1]*len(input_data)
# for i, item in enumerate(input_data):
#     input_data_encoded[i] = int(label_encoder[i].transform([input_data[i]]))

# input_data_encoded = np.array(input_data_encoded)  # 将标记编码后的单一样本转换成numpy数组
# input_data_encoded = input_data_encoded.reshape(1, len(input_data))

# 打印输出结果
output_class = classifier.predict(input_data)
output_class=np.array(output_class)
output_class=output_class.reshape(len(output_class),1)

output_class=label_encoder[-1].inverse_transform(output_class[:,0])

output_score=[]
for word in output_class:
    a=random.uniform(0,1)
    a=str(a).split('.')[0] + '.' + str(a).split('.')[1][:2]
    a=float(a)
    if word == 'vgood':
        output_score.append(4+a)
    elif word == 'good':
        output_score.append(3+a)
    elif word == 'acc':
        output_score.append(2+a)
    elif word == 'unacc':
        output_score.append(1+a)
    else:
        output_score.append(a)

# print("Output class:", label_encoder[-1].inverse_transform(output_class[:,0]))
# print("Output score:",output_score)

test_out_path='car.test_with_score2.csv'
with open(test_path, 'r',encoding='utf-8') as csvfile:
    rows = csv.reader(csvfile)
    with open(test_out_path, 'w',newline='',encoding='utf-8') as f:
        writer = csv.writer(f)
        header=next(rows)
        header.append('performance')
        writer.writerow(header)
        i=0
        for row in rows:
            row.append(output_score[i])
            print(output_score[i])
            i+=1
            writer.writerow(row)

