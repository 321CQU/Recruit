# -*- coding: gb18030 -*-
import re

s="1.1万"

if re.findall(r'^\d',s):
    print(s)
pattern=re.compile(r'(\d\.?\d*)万')
pattern.findall(s)
print(re.findall(r'(\d\.?\d*)万',s))
# s='2022'
# print(int(s))
# str = 'abc123qwer456tyui789'
# num = ''.join([x for x in str if x.isdigit()])
# print(int(num))