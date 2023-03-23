import requests
from requests import Session, get
# from auth import access_service
from html.parser import HTMLParser
from typing import Any, Dict, Optional, Tuple, List, Union, ClassVar
from requests import Session, Response, cookies

# 页面解析，获得账号和密码
class LibPageParser(HTMLParser):
    def __init__(self):
        super().__init__()
        self._starttag: int = 0
        self.user_id: str = ""
        self.user_key: str = ""

    def handle_starttag(self, tag, attrs):
        if self._starttag != 2 and tag == 'input' and ('id', 'hfldUserId') in attrs:
            self._starttag = self._starttag + 1
            for key, val in attrs:
                if key == "value":
                    self.user_id = val
                    break

        if self._starttag != 2 and tag == 'input' and ('id', 'hfldUserKey') in attrs:
            self._starttag = self._starttag + 1
            for key, val in attrs:
                if key == "value":
                    self.user_key = val
                    break

def access_service(session: Session, service: str) -> Response:
    resp = session.get('https://sso.cqu.edu.cn/login',
                       params={"service": service},
                       allow_redirects=True)
    # if resp.status_code != 302:
    #     AuthPageParser().feed(resp.text)
    #     raise NotLogined()
    print(resp.text)
    return session.get(url=resp.url, allow_redirects=True)

def access_library(session: Session) -> Dict[str, Any]:
    """
    通过统一身份认证登陆图书馆页面，返回UserID和UserKey用于查询
    :param session: 登录了统一身份认证（:func:`.auth.login`）并在 mycqu进行了认证（:func:`.mycqu.access_mycqu`）的 requests 会话
    :type session: Session
    :return: 图书馆账号特有的UserID和UserKey
    :rtype: Dict[str, Any]
    """
    res = access_service(session, "http://lib.cqu.edu.cn/") #现在网址 http://v2.lib.cqu.edu.cn/
    print(res.url)
    res1 = session.get(url=res.url, allow_redirects=True)
    # print(res1.text)
    parser = LibPageParser()
    parser.feed(res1.text)
    data = {
        "UserID": parser.user_id,
        "UserKey": parser.user_key,
    }

    return data

