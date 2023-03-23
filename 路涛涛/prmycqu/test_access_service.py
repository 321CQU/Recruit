import requests
from requests import Session, get
from typing import Dict, Optional, Callable
import random
import re
from base64 import b64encode
from html.parser import HTMLParser
from urllib.parse import parse_qs, urlsplit
from requests import Session, Response, cookies
# from ._lib_wrapper.encrypt import pad, aes_cbc_encryptor

class NotLogined(Exception):
    """未登陆或登陆过期的会话被用于进行需要统一身份认证登陆的操作
    """

    def __init__(self):
        super().__init__("not in logined status")

# 认证页面解析
class AuthPageParser(HTMLParser):
    _SALT_RE: re.Pattern = re.compile('var pwdDefaultEncryptSalt = "([^"]+)"')

    def __init__(self):
        super().__init__()
        self.input_data: Dict[str, Optional[str]] = \
            {'lt': None, 'dllt': None,
                'execution': None, '_eventId': None, 'rmShown': None}
        """几个关键的标签数据"""
        self.salt: Optional[str] = None
        """加密所用的盐"""
        self._js_start: bool = False
        self._js_end: bool = False
        self._error: bool = False
        self._error_head: bool = False

    def handle_starttag(self, tag, attrs):
        if tag == 'input':
            name: Optional[str] = None
            value: Optional[str] = None
            for attr in attrs:
                if attr[0] == 'name':
                    if attr[1] in self.input_data:
                        name = attr[1]
                    else:
                        break
                elif attr[0] == 'value':
                    value = attr[1]
            if name:
                self.input_data[name] = value
        elif tag == 'script' and attrs and attrs[0] == ("type", "text/javascript"):
            self._js_start = True
        elif tag == "div" and attrs == [("id", "msg"), ("class", "errors")]:
            self._error = True
        elif tag == 'h2' and self._error:
            self._error_head = True


session=requests.session()
resp = session.get('https://sso.cqu.edu.cn/login',#http://authserver.cqu.edu.cn/authserver/login
                       params={"service": "http://lib.cqu.edu.cn/"},#http://lib.cqu.edu.cn/caslogin
                       allow_redirects=False)
print(resp.status_code)
# print(resp.text)
# print(resp.headers)
if resp.status_code != 200:
    AuthPageParser().feed(resp.text)
    raise NotLogined()
# print(resp.headers['Location'])
print(resp.url)
res=session.get(resp.url,allow_redirects=False)
print(res.url)
print(res.status_code)
print(res.headers)
# res1 = session.get(url="http://lib.cqu.edu.cn" + res.headers['Location'], allow_redirects=False)
# print(res1.status_code)
# https://sso.cqu.edu.cn/login?service=http:%2F%2Flib.cqu.edu.cn%2F 应该用这个网址登录图书馆
# https://sso.cqu.edu.cn/login?service=http%3A%2F%2Flib.cqu.edu.cn
# https://sso.cqu.edu.cn/login?service=http%3A%2F%2Flib.cqu.edu.cn%2F 这个可以登录

