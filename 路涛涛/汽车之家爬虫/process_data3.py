# -*- coding: gb18030 -*-
import csv

with open('cars4.csv',encoding='gb18030',mode='r') as f:
    reader = csv.DictReader(f)
    newf=open('cars5.csv',encoding='gb18030',mode='w',newline="")
    header_list=['money','kind','usingTime','mile','performance']
    writer=csv.DictWriter(newf,header_list)
    writer.writeheader()

    for row in reader:
        newRow={}
        if int(row['money'])<=600000 and int(row['money'])>500000:
            newRow['money']="vhigh"
        elif int(row['money'])<=500000 and int(row['money'])>300000:
            newRow['money']="high"
        elif int(row['money'])<=300000 and int(row['money'])>100000:
            newRow['money']="med"
        else: newRow['money']="low"

        if row['kind']=='跑车': newRow['kind']="vhigh"
        elif row['kind']=='MUV': newRow['kind']='high'
        elif row['kind']=='轿车': newRow['kind']='med'
        else: newRow['kind']='low'

        if int(row['usingTime'])>10: newRow['usingTime']="big"
        elif int(row['usingTime'])<=10 and int(row['usingTime'])>5: newRow['usingTime']="med"
        else: newRow['usingTime']="small"

        if int(row['mile'])>=1500: newRow['mile']="high"
        elif int(row['mile'])>=500 and int(row['mile'])<1500: newRow['mile']="med"
        else: newRow['mile']="low"

        writer.writerow(newRow)
    newf.close()