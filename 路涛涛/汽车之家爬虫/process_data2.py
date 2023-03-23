# -*- coding: gb18030 -*-
import csv
import re

with open('cars3.csv',encoding='gb18030',mode='r') as f:
    reader = csv.DictReader(f)
    newf=open('cars4.csv',encoding='gb18030',mode='w',newline="")
    header_list=['kind','usingTime','brandName','money','performance','mile','where']
    writer=csv.DictWriter(newf,header_list)
    writer.writeheader()
    for row in reader:
        newRow={}

        if row['nowprice']!="" and row['miles']!="" and row['type']!="":
            
            if re.findall(r'^\d',row['type']):
                newRow['usingTime']=2022-int(''.join([x for x in row['type'] if x.isdigit()]))
                if newRow['usingTime']<0: newRow['usingTime']=0
                # print(newRow['usingTime'])
                newRow['kind']=row['kind']
                newRow['brandName']=row['brand']
                newRow['money']=int(float(row['nowprice'])*10000)
                newRow['performance']=1
                mile=re.findall(r'(\d\.?\d*)万公里',row['miles'])
                newRow['mile']=int(float(mile[0])*100)
                newRow['where']=row['location']
                # print(newRow['where'])
                
                writer.writerow(newRow)

    newf.close()