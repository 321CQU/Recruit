"""my.cqu.edu.cn 认证相关的模块
"""
from typing import Dict
import re
from requests import Session
from auth2 import access_sso_service,login
__all__ = ("access_mycqu",)
import requests
import json

MYCQU_TOKEN_INDEX_URL = "https://my.cqu.edu.cn/enroll/token-index" #选课网页
MYCQU_TOKEN_URL = "https://my.cqu.edu.cn/authserver/oauth/token" #提交表单的网页
#重定向到选课网页
MYCQU_AUTHORIZE_URL = f"https://my.cqu.edu.cn/authserver/oauth/authorize?client_id=enroll-prod&response_type=code&scope=all&state=&redirect_uri={MYCQU_TOKEN_INDEX_URL}"
# 选课网页
MYCQU_SERVICE_URL = "https://my.cqu.edu.cn/authserver/authentication/cas"
CODE_RE = re.compile(r"\?code=([^&]+)&")
# http://authserver.cqu.edu.cn/authserver/index.do
# https://my.cqu.edu.cn/authserver/oauth/authorize?client_id=enroll-prod&response_type=code&scope=all&state=&redirect_uri=https://my.cqu.edu.cn/enroll/token-index
# https://my.cqu.edu.cn/authserver/oauth/authorize?client_id=enroll-prod&response_type=code&scope=all&state=&redirect_uri=http://my.cqu.edu.cn/enroll/token-index
class MycquUnauthorized(Exception):
    def __init__(self):
        super().__init__("Unanthorized in mycqu, auth.login firstly and then mycqu.access_mycqu")

# 授权，颁发令牌token
def get_oauth_token(session: Session) -> str:
    # from https://github.com/CQULHW/CQUQueryGrade
    resp = session.get(MYCQU_AUTHORIZE_URL, allow_redirects=False)
    # print(resp.headers)
    # match = CODE_RE.search(resp.headers['Location'])
    # assert match
    # print(match)
    codeValue = resp.headers['Location']
    print(codeValue)
    codeValue = re.search(pattern=r'=.*?&', string=str(codeValue))
    print(codeValue)
    codeValue = codeValue.group()[1:-1]
    token_data = {
        'client_id': 'enroll-prod',
        'client_secret': 'app-a-1234',
        # 'code': match[1],
        'code':str(codeValue),
        'redirect_uri': MYCQU_TOKEN_INDEX_URL, #b接受或拒绝后的跳转网址
        'grant_type': 'authorization_code'
    }
    # 发送post请求获取到token
    access_token = session.post(MYCQU_TOKEN_URL, data=token_data)
    token_response=json.loads(access_token.content)
    TOKEN=token_response['access_token']
    return "Bearer " + TOKEN


def access_mycqu(session: Session, add_to_header: bool = True) -> Dict[str, str]:
    """用登陆了统一身份认证的会话在 my.cqu.edu.cn 进行认证
    :param session: 登陆了统一身份认证的会话
    :type session: Session
    :param add_to_header: 是否将 mycqu 的认证信息写入会话属性，默认为 :obj:`True`
    :type add_to_header: bool, optional
    :return: mycqu 认证信息的请求头，当 ``add_to_header`` 参数为 :obj:`True` 时无需手动使用该返回值
    :rtype: Dict[str, str]
    """
    if "Authorization" in session.headers:
        del session.headers["Authorization"]
    access_sso_service(session, MYCQU_SERVICE_URL)
    token = get_oauth_token(session)
    if add_to_header:
        session.headers["Authorization"] = token
    return {"Authorization": token}


session1=requests.session()
session2=requests.session()
ume='0707***'
pwd='****'
service=None  #'http://lib.cqu.edu.cn/'
timeout=10
force_relogin=False
keep_longer= False
kick_others = False
# print(requests.utils.dict_from_cookiejar(session1.cookies))
session2=login(session1,ume,pwd,
      service=None,timeout=10,force_relogin=False,captcha_callback=None,keep_longer=False,kick_others = False)
# print(requests.utils.dict_from_cookiejar(session1.cookies))['session']
# print(requests.utils.dict_from_cookiejar(session1.cookies))
# get_oauth_token(session)
# print(res.json())
add_to_header=True
print(access_mycqu(session2,add_to_header))
# https://sso.cqu.edu.cn/login?service=https%3A%2F%2Fmy.cqu.edu.cn%2Fauthserver%2Fauthentication%2Fcas
# https://sso.cqu.edu.cn/login?service=https%3A%2F%2Fmy.cqu.edu.cn%2Fauthserver%2Fauthentication%2Fcas