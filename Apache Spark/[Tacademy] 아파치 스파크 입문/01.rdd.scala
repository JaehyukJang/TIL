val directory = "/sample.log"

spark

val logs = sc.textFile(directory)

logs.take(10)

//전체 문자길이와 개수 및 평균을 구한다
val lengths = logs.map(str => str.length)
val totalLength = lengths.reduce( (acc, newVal) => acc + newVal )
val count = lengths.count()
val average = totalLength.toDouble / count

//각 line의 사이즈를 구한다
val log = logs.map((log) => log.size)
log.collect().foreach(println)

//각 라인의 길이가 200이상인 것만 필터링
val filter = logs.filter( (log) => log.size > 200)

filter.collect().foreach(println)


