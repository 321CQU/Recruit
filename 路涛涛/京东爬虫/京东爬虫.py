import requests
import time
import json
import random
# from lxml import etree
# import xlwt
# import xlrd
import csv

#写入文件 
fp=open('D:\code\C++\promote\PTA\京东评论.csv','a',newline="",encoding='gb18030')
for i in range(1,101):
    # url='https://item.jd.com/100014352501.html#none'
    # url='https://club.jd.com/comment/productPageComments.action?callback=fetchJSON_comment98&productId=100014352501&score=0&sortType=5&page=0&pageSize=10&isShadowSku=0&fold=1'
    url='https://club.jd.com/comment/productPageComments.action?callback=fetchJSON_comment98&productId=100014352501&score=0&sortType=5&page={}&pageSize=10&isShadowSku=0&rid=0&fold=1'
    # urk='https://club.jd.com/comment/productPageComments.action?callback=fetchJSON_comment98&productId=100014352501&score=0&sortType=5&page=2&pageSize=10&isShadowSku=0&rid=0&fold=1'
    url = url.format(i)
    headers = {
        'User-Agent':'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36 Edg/110.0.1587.69',
        'Referer':'https://item.jd.com/100014352501.html'
    }
    time.sleep(random.random())
    page = requests.get(url=url, headers=headers).text
    if page !=None:
        page=page.strip('fetchJSON_comment98vv385();')
    # print(page)
    data = json.loads(page)
    comments=data["comments"]
    
    header=["用户id","评论内容","评论时间"]
    w = csv.DictWriter(fp,header)
    # w.writeheader() #只需要第一次写入
    for comment in comments:
        # print(comment["id"],":",comment["content"])
        item={}
        #评论人
        id = comment['id']
        item['用户id']=id
        #评论内容
        contents = comment['content']
        item['评论内容']=contents
        #评论时间
        creationTime = comment['creationTime']
        item["评论时间"]=creationTime
        
        w.writerow(item)
        # string = str(item)
        # fp.write(string + '\n')
        
    print('第' + str(i) + '页写入完毕\n')
    time.sleep(4)
fp.close()
