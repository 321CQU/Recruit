import requests
from _lib_wrapper.encrypt import pad, aes_cbc_encryptor
from html.parser import HTMLParser
import re


# def get_formdata(html: str, username: str, password: str) -> Dict[str, Optional[str]]:
#     # from https://github.com/CQULHW/CQUQueryGrade
#     parser = AuthPageParser()
#     parser.feed(html)
#     if not parser.salt:
#         ValueError("无法获取盐")
#     passwd_pkcs7 = pad((_random_str(64)+str(password)).encode())
#     encryptor = aes_cbc_encryptor(
#         parser.salt.encode(), _random_str(16).encode())
#     passwd_encrypted = b64encode(encryptor(passwd_pkcs7)).decode()
#     parser.input_data['username'] = username
#     parser.input_data['password'] = passwd_encrypted
#     return parser.input_data

sess=requests.session()
data={
    "ume":"0707***",
    "pwd":"***"
}
AUTHSERVER_URL = "http://authserver.cqu.edu.cn/authserver/login"
login_url='https://self.cqu.edu.cn'
# https://sso.cqu.edu.cn
url='https://sso.cqu.edu.cn/login?service=http:%2F%2Flib.cqu.edu.cn%2F'
headers={
    'User-Agent':'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36 Edg/111.0.1661.44',
    'Host':'sso.cqu.edu.cn',
    'Origin':'https://sso.cqu.edu.cn',
    'Referer':'https://sso.cqu.edu.cn/login',
    'Upgrade-Insecure-Requests':'1',
    'Sec-Fetch-User':'?1',
    'Accept':'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7'
}
# res1=sess.post(login_url,headers=headers,data=data)
res=sess.get('https://self.cqu.edu.cn',headers=headers)
# tempPQ=pq(res.text)
# tempPQStr = tempPQ("input[type='hidden'][name='lt']").attr("value")
# tempPQStr22 = tempPQ("input[type='hidden'][name='execution']").attr("value")
# mylist=[tempPQStr,tempPQStr22]
# print(mylist)
# execution = re.search('name="execution" value="(.*?)"', res.text).group(1)
# print(execution)
# print(res.status_code)
print(res.text)