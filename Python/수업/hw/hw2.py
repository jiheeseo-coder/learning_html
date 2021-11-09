from rank_bm25 import BM25Okapi

corpus = [
    "You make me feel like it's a Sunday morning",
    "Whatever we do comes easy like we want it",
    "And we can't turn forever off 'cause we ain't got the pressure on us",
    "You make me feel like, you make me feel like it's a Sunday morning"
]


tokenized_corpus = [doc.split(" ") for doc in corpus]

bm25 = BM25Okapi(tokenized_corpus)
print('bm25: ',bm25)

query = "we do"
tokenized_query = query.split(" ")

doc_scores = bm25.get_scores(tokenized_query)
print('doc_scores: ', doc_scores)