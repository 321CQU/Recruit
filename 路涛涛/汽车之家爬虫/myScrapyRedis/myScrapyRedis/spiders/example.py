import scrapy
from scrapy_redis.spiders import RedisSpider
from scrapy.linkextractors import LinkExtractor
from scrapy.spiders import CrawlSpider, Rule
from myScrapyRedis.items import MyscrapyredisItem

class ExampleSpider(RedisSpider):
    name = 'cars'
    #allowed_domains = ['example.com']
    #start_urls = ['http://example.com/']
    redis_key='cars:start_urls'

    rules = (
        Rule(LinkExtractor(allow=r'Items/'), callback='parse_item', follow=True),
    )

    def parse(self, response):
        li_list=response.xpath("/html/body/div[3]/div[3]/div/div/div[1]/ul/li")#提取一级分类元素
        
        for li in li_list:
            item=MyscrapyredisItem()
            text=li.xpath("./a/h3/text()").extract()#别克-别克GL6 2018款 ES 26T 豪华型
            item["brand"]=text[0]#别克-别克GL6
            item["type"]=text[1]#2018款
            
            item["years"]=li.xpath("./a/div[2]/span/text()[1]")
            item["miles"]=li.xpath("./a/div[2]/span/text()[2]")
            item["location"]=li.xpath("./a/div[2]/span/text()[3]")

            item["price"]=li.xpath("./a/div[4]/div/text()")

        #提取列表下一页地址
        next_url='https://www.guazi.com/buy?search=%257B%2522tag_types%2522%253A10012%257D'
        'https://www.guazi.com/buy?search=%257B%2522tag_types%2522%253A10012%257D'
        'https://www.guazi.com/buy?search=%257B%2522tag_types%2522%253A10012%257D'
        'https://www.guazi.com/buy?search=%257B%2522tag_types%2522%253A10012%257D'
        'https://www.che168.com/china/a0_0msdgscncgpi1ltocsp5exx0/?pvareaid=102179#currengpostion'
        'https://www.che168.com/china/a0_0msdgscncgpi1ltocsp6exx0/?pvareaid=102179#currengpostion'
        'https://www.che168.com/china/a0_0msdgscncgpi1ltocsp1exx0/?pvareaid=102179#currengpostion'

    def parse_item(self, response):
        item = {}
        
        #item['domain_id'] = response.xpath('//input[@id="sid"]/@value').get()
        #item['name'] = response.xpath('//div[@id="name"]').get()
        #item['description'] = response.xpath('//div[@id="description"]').get()
        return item

