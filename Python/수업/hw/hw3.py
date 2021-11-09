from rank_bm25 import BM25Okapi

corpus = ["구글 뉴스(Google News)는 구글이 제공하고 운영하는 무료 뉴스 애그리게이터이다.",
          "자동 집계 알고리즘에 의해 수천 곳의 발행사로부터 최신의 정보를 선별한다.",
          "현 총괄자는 리처드 징그러스이다." ,
          "2002년 9월 출범한 이 서비스는 2006년 1월까지 3년에 걸쳐 베타 테스트로 표기되었다.",
          "초기의 아이디어는 크리시나 브하라트(Krishna Bharat)에 의해 개발되었다.",
           "구글 뉴스는 2014년 12월 16일부로 스페인에서 구글 뉴스 서비스를 중단한다고 밝혔다.",
          "이는 스페인 저작권의 개정으로 2015년 1월부터 뉴스 인용문에도 저작권료를 지불해야 하기 때문이다.",
        "이 서비스는 인터넷이 사용되는 환경에서라면 대한민국을 비롯한 다양한 국가에서 전 세계적으로 이용할 수 있다."]


def bigram(str):
    result = []
    for word in str.split():
        prev=''
        for c in word:
            if prev:
                result.append(prev+c)
            prev = c
    return result

tokenized_corpus = []
for i in corpus:
    tokenized_corpus.append(bigram(i))
#print(tokenized_corpus)

bm25 = BM25Okapi(tokenized_corpus)
print('bm25: ',bm25)

query = "뉴스"
tokenized_query = bigram(query)

doc_scores = bm25.get_scores(tokenized_query)
print('doc_scores: ', doc_scores)


