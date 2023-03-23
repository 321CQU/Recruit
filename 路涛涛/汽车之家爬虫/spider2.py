# -*- coding: utf-8 -*-

from lxml import etree
import requests
import csv


url='https://www.che168.com/china/a0_0msdgscncgpi1ltocsp8exx0/?kw=%C3%E6%B0%FC%B3%B5&pvareaid=102179#currengpostion'
urls = ["https://www.che168.com/china/a0_0msdgscncgpi1ltocsp{}exx0/?pvareaid=102179#currengpostion".format(str(i)) for i in range(1,101)]
# res=requests.get(url2,headers1)
# print(res.cookies)
headers = {
    'User-Agent':'Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/105.0.0.0 Mobile Safari/537.36',
    'cookie':'fvlid=16689310193665Orw476DWQOg; sessionid=3e7c4c7a-de00-4bca-b0ae-5772daf02e14; che_sessionid=59FBC771-C58A-4555-AAE6-92751B983C15||2022-11-20+15:57:45.693||0; listuserarea=0; userarea=0; Hm_lvt_0f2ac73eb429af8bb7f48d01f2a25a25=1669563964; __SK_cookieId=7584478871597531669563965225; sessionvisit=758a4d61-2d97-462d-b4c6-054c1f751b76; sessionvisitInfo=3e7c4c7a-de00-4bca-b0ae-5772daf02e14||100945; Hm_lvt_d381ec2f88158113b9b76f14c497ed48=1668931039,1669119371,1669303299,1669635867; che_sessionvid=2B84CB25-5DA7-451B-9A7E-355765AFA4A4; searchhistory=0|0|suv,0|0|�γ�; searchhistoryval=suv; ahpvno=67; ahuuid=DBB98D55-F515-4D61-A383-4C1854E88090; Hm_lpvt_d381ec2f88158113b9b76f14c497ed48=1669638081; sessionip=111.177.6.108; area=420699; showNum=85; v_no=87; visit_info_ad=59FBC771-C58A-4555-AAE6-92751B983C15||2B84CB25-5DA7-451B-9A7E-355765AFA4A4||-1||-1||87; che_ref=cn.bing.com|0|0|0|2022-11-28+20:21:23.436|2022-11-26+00:29:59.033; sessionuid=3e7c4c7a-de00-4bca-b0ae-5772daf02e14'
}


#�����ݱ���ΪCSV�ļ�
cf=open("D:\code\������\cars2.csv", "a", newline="",encoding='gb18030')
header=['brand','type','miles','years','location','nowprice','preprice','kind']
w = csv.DictWriter(cf,header)
# w.writeheader()
print(url)
num=0
page = requests.get(url, headers)
# page.encoding='utf-8'
text_page=page.text
# print(text_page)
print(len(text_page))
tree = etree.HTML(text_page)

li_list = tree.xpath("/html/body/div[12]/div[1]/ul/li")
print(len(li_list))
for li in li_list:
    num=num+1
    item={}
    text=li.xpath("./a/div[2]/h4/text()")#���-���GL6 2018�� ES 26T ������
    text2=li.xpath("./a/div[2]/p/text()")#['0.01���δ���ƣ�����']
    print(len(text2),len(text2[0].split('��')))
    
    if len(text)!=0 and len(text[0].split(' '))>=2:
        item["brand"]=text[0].split(' ')[0]#���-���GL6
        item["type"]=text[0].split(' ')[1]#2018��
    if len(text2)!=0 and len(text2[0].split('��'))>=3:
        item["miles"]=text2[0].split('��')[0]#0.01
        item["years"]=text2[0].split('��')[1]#weishangpai
        item["location"]=text2[0].split('��')[2]#shengzhen
        # print(num)
    if len(li.xpath("./a/div[2]/div[1]/span/em/text()"))!=0:#special
        #/html/body/div[12]/div[1]/ul/li[21]/a/div[2]/div[1]/span/em
        item["nowprice"]=li.xpath("./a/div[2]/div[1]/span/em/text()")[0]
    if len(li.xpath("./a/div[2]/div[1]/s/text()")) !=0:
        item["preprice"]=li.xpath("./a/div[2]/div[1]/s/text()")[0]
    item["kind"]="�����"  
        # print(item)
    w.writerow(item)
    
# next_url=tree.xpath('/html/body/div[12]/div[2]/a[9]/@href')[0]
# url="https://www.che168.com"+next_url
    print(num)

cf.close()

