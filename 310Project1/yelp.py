from bs4 import BeautifulSoup
from requests import get
import string
import sys

def main():
    # get page
    q = sys.argv[1]

    # get page
    page = get(q)
    soup = BeautifulSoup(page.content, 'html.parser')

    # extract span
    body = soup.find('span', class_='biz-website')

    if not body:
#        print("No page available for " + q)
        print("empty")
        return 1

    # extract link
    body_text = body.find('a').get_text()

    # print link
    print(body_text)


if __name__=="__main__":
    main()
