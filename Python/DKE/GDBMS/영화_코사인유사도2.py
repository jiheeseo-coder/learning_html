from py2neo import Graph, Path

# graph database
graph = Graph("http://114.70.235.43:37474/db/data/", user="neo4j", password="neo4j")

#두 사용자간의 동일 영화에 대한 평점을 이용한 similarity 계산 CQL
query = """MATCH (u1:User {id:$uid1})-[r1:RATING]->(m:Movie)<-[r2:RATING]-(u2:User {id:$uid2}) \
WITH SUM(r1.rating * r2.rating) AS r1r2DotProduct, \
     SQRT(REDUCE(r1Dot = 0.0, a IN COLLECT(r1.rating) | r1Dot + a^2)) AS r1Length, \
     SQRT(REDUCE(r2Dot = 0.0, b IN COLLECT(r2.rating) | r2Dot + b^2)) AS r2Length, \
     u1, u2 \
MERGE (u1)-[s:SIMILARITY]-(u2) \
SET s.similarity = r1r2DotProduct / (r1Length * r2Length)"""



for i in range(1, 671+1):
    for j in range(i+1, 671+1):
        graph.run(query, uid1=i, uid2=j)
