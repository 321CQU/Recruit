# Define here the models for your scraped items
#
# See documentation in:
# https://docs.scrapy.org/en/latest/topics/items.html

import scrapy


class MyscrapyredisItem(scrapy.Item):
    # define the fields for your item here like:
    # name = scrapy.Field()
    price=scrapy.Field()#价格
    years=scrapy.Field()#使用年限
    brand=scrapy.Field()#品牌
    type=scrapy.Field()#型号
    miles=scrapy.Field()#里程数
    location=scrapy.Field()#所在地
