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
    'User-Agent':'Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/105.0.0.0 Mobile Safari/537.36',
    # 'cookie':'_uab_collina=166938102940804087360442; rrc_ip_city_twohour=cq; rrc_modified_city=true; rrc_record_city=cn; rrc_fr=bd_other; rrc_tg=fr%3Dbd_other; rrc_ss=initiative; new_visitor_uuid=76d05d48426747b8b5c657db2fd6199d; ssxmod_itna=iqmxyDgi0QdWTxBcDeTm4jxwFDCQ1tKztr7eYrtDlr=exA5D8D6DQeGTrnq1D0exhxQtlO7a2PHKQ0y3qKKn8hIa8YjS0uHXD0eGnDBKGSDG5Di3yxqGX00=4D9+q0tD6xDUXBxW93D=25Duxi7Dl4D7xGdDlKDRx0OriGWWKx7mhI86ZFeD24Pdir5YneaXG4xeBuDYlwbfK8Wqj2Pz8LNDDPP/7roWYD==; ssxmod_itna2=iqmxyDgi0QdWTxBcDeTm4jxwFDCQ1tKztr7eYD6pF7D054DQ1DLjAiNnrc6B4nR48VYiwRLsqAPu75G4Vr2AwvqdzeAbIlD31dKGPwIqrypnCiolO5eQVPEnGnS6zQpxIo8eEiIe+Ef2ng6EFkLBtkGR9kL7CkUZ4Eilf8dvIkdfRjoG0BjQR4n/KPYeKb3jBQnsC03/eZtQ=7Yh7PfLDd3gtpcNvTa3muaFa8Nah3a1PMf7Wkjw2mOwDwE2FYNLX3rGuKaw3u8E8GbbORx54jrf6e92UmRpxPTXQRMwrfAXtmKDgXRm0I5lN1ZELY6/OAw2UgAeUQINPjeKfuxdI7LH0m8i2CnEdRUV7r8erfBAtaekrpIgrw7AWOdvWe54I+e2RYm=SeEiQa9rt+rHYeka6oSm3i55tDQl43GD8rmPlE4Bq3YtzS4QBfsjqnBIwIeE4If+3IjYHYtwihb9RQinCz9/p3ScN5IrkB0ctDBae4mfxm4DQKKWu7TGbFGx7RG1h0Ahh6r+=RQ7jKqh=SGWMrA8C5S0h2jrYD7FDTQiSj249IqFDDFqD+PqxD==; zhimai-page-list-banner=true; rrc_promo_two_years=rrc_promo_two_years; _ga=GA1.2.1762693609.1669381032; rls_uuid=3365495A-F17C-44B4-8D59-36C18EABE7AB; _pzfxuvpc=1669381031915%7C7480024601112488692%7C5%7C1669387862628%7C3%7C1519173075159786287%7C9556545740119429144; Hm_lvt_c8b7b107a7384eb2ad1c1e2cf8c62dbe=1669381033,1669386027; acw_tc=b65cfd3e16693878268267094e4d0c48cc15257f46b02262b5a1ceda54e028; acw_sc__v2=6380cf20c80365301d12b995efd934b7c1d82385; rrc_rrc_session=0akthn5mkfjtccolhvr3io3601; rrc_rrc_signed=s%2C0akthn5mkfjtccolhvr3io3601%2C811ef4b5995d527c8d3b27cff206aaff; rrc_session_city=cn; SERVERID=16b08e25ce34c915f4c1511ea20e3105|1669387862|1669387204; Hm_lpvt_c8b7b107a7384eb2ad1c1e2cf8c62dbe=1669387863; server-common-request=1; _uab_collina=166938720550563134892939; _bl_uid=5jlsvaUjwtwmw11Cdgb3v17g1pjs; acw_sc__v3=6380d557f98ffcb251e52c6d12260770ae1fe603; _pzfxsvpc=9556545740119429144%7C1669387611830%7C2%7Chttps%3A%2F%2Fwww.renrenche.com%2Fcn%2Fershouche%2F%3F%26plog_id%3D897330b08d84c9e0758e02eea9616092; rrc_login_phone=18295146356; rrc_login_token=14ba79f5d69b32cf743a3f4daaf09543; rrc_userid=2736569584'
}

#将数据保存为CSV文件
cf=open("D:\code\大数据\cars.csv", "w", newline="",encoding='GB18030')
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



