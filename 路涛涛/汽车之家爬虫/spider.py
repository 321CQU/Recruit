from lxml import etree
import requests
import csv
import time
import random

url='https://www.che168.com/china/a0_0msdgscncgpi1ltocsp1exx0/?pvareaid=102179#currengpostion'
urls = ["https://www.che168.com/china/a0_0msdgscncgpi1ltocsp{}exx0/?pvareaid=102179#currengpostion".format(str(i)) for i in range(1,101)]
# res=requests.get(url2,headers1)
# print(res.cookies)
headers = {
    'User-Agent':'Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/105.0.0.0 Mobile Safari/537.36'
}

#将数据保存为CSV文件
cf=open("cars.csv", "w", newline="",encoding='GB18030')
header=['brand','type','miles','years','location','nowprice','preprice']
w = csv.DictWriter(cf,header)
w.writeheader()

count=0
flag=1
while(flag):
    time.sleep(5)
    print(url)
    num=0
    page = requests.get(url, headers)
    # page.encoding='utf-8'
    text_page=page.text
    print(len(text_page))
    tree = etree.HTML(text_page)

    li_list = tree.xpath("/html/body/div[12]/div[1]/ul/li")
    print(len(li_list))
    for li in li_list:
        num=num+1
        item={}
        text=li.xpath("./a/div[2]/h4/text()")#别克-别克GL6 2018款 ES 26T 豪华型
        text2=li.xpath("./a/div[2]/p/text()")#['0.01万公里／未上牌／深圳']
        print(text2)
        if len(text)!=0 :#and len(text[0].split(' '))>=2:
            item["brand"]=text[0].split(' ')[0]#别克-别克GL6
            item["type"]=text[0].split(' ')[1]#2018款
        if len(text2)!=0 :#and len(text2[0].split('／'))>=3:
            item["miles"]=text2[0].split('／')[0]#0.01
            item["years"]=text2[0].split('／')[1]#weishangpai
            item["location"]=text2[0].split('／')[2]#shengzhen
        # print(num)
        if len(li.xpath("./a/div[2]/div[1]/span/em/text()"))!=0:#special
        #/html/body/div[12]/div[1]/ul/li[21]/a/div[2]/div[1]/span/em
            item["nowprice"]=li.xpath("./a/div[2]/div[1]/span/em/text()")[0]
        if len(li.xpath("./a/div[2]/div[1]/s/text()")) !=0:
            item["preprice"]=li.xpath("./a/div[2]/div[1]/s/text()")[0]
        
        # print(item)
        w.writerow(item)
    
    next_url=tree.xpath('/html/body/div[12]/div[2]/a[9]/@href')[0]
    url="https://www.che168.com"+next_url
    print(num)
    count=count+num
cf.close()
print(count)



