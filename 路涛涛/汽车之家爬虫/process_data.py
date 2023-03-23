# -*- coding: gb18030 -*-
import pandas as pd
import numpy as np
raw_data=pd.read_csv('cars2.csv',encoding='gb18030',keep_default_na=np.nan)
record_num=raw_data.describe()
print(record_num)

for row in raw_data:
    #缺失值检查
    print(row)
    # raw_data.isnull().sum
    # # raw_data.replace(' ',np.nan)
    # raw_data.dropna(axis=0, how='any', inplace=True, subset=None)
