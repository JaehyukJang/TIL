//wget https://raw.githubusercontent.com/databricks/Spark-The-Definitive-Guide/master/data/flight-data/csv/2015-summary.csv
//val directory = "/2015-summary.csv"

//sparksession driver process, 애플리케이션 기준
spark

//데이터프레임 생성
val flightData2015 = spark.read.option("inferSchema", "true").option("header", "true").csv(directory)

flightData2015.printSchema

flightData2015.take(3)

//실행계획 확인
flightData2015.sort("count").explain()

flightData2015.sort("count").take(2)

//SQL 사용을 위한 임시 view 생성
flightData2015.createOrReplaceTempView("flight_data_2015")

val sqlWay = spark.sql("""
SELECT DEST_COUNTRY_NAME, count(1)
FROM flight_data_2015
GROUP BY DEST_COUNTRY_NAME
""")

//데이터프레임 통계
sqlWay.describe().show()

sqlWay.explain

spark.sql("SELECT max(count) from flight_data_2015").take(1)

import org.apache.spark.sql.functions.max

flightData2015.select(max("count")).take(1)

val maxSql = spark.sql("""
SELECT DEST_COUNTRY_NAME, sum(count) as destination_total
FROM flight_data_2015
GROUP BY DEST_COUNTRY_NAME
ORDER BY sum(count) DESC
LIMIT 5
""")

maxSql.explain

maxSql.show()

//https://stackoverflow.com/questions/51270747/scala-spark-illegal-start-of-definition
(flightData2015
.groupBy("DEST_COUNTRY_NAME")
.sum("count")
.withColumnRenamed("sum(count)", "destination_total")
.sort(desc("destination_total"))
.limit(5)
.explain)

(flightData2015
.groupBy("DEST_COUNTRY_NAME")
.sum("count")
.withColumnRenamed("sum(count)", "destination_total")
.sort(desc("destination_total"))
.limit(5)
.show())

val data = Seq(("Banana",1000,"USA"), ("Carrots",1500,"USA"), ("Beans",1600,"USA"),
      ("Orange",2000,"USA"),("Orange",2000,"USA"),("Banana",400,"China"),
      ("Carrots",1200,"China"),("Beans",1500,"China"),("Orange",4000,"China"),
      ("Banana",2000,"Canada"),("Carrots",2000,"Canada"),("Beans",2000,"Mexico"))

import spark.sqlContext.implicits._

val df = data.toDF("Product", "Amount", "Country")

df.show()

val pivotDF = df.groupBy("Product").pivot("Country").sum("Amount")

pivotDF.show()

//spark2.0에서 성능향상을 위해 pivot대상열을 선언
val countries = Seq("USA", "China", "Canada", "Mexico")
val pivotDF = df.groupBy("Product").pivot("Country", countries).sum("Amount")

pivotDF.show()

//성능 향상을 위해 두단계 집계를 사용
val pivotDF = (df.groupBy("Product", "Country")
    .sum("Amount")
    .groupBy("Product")
    .pivot("Country")
    .sum("sum(Amount)"))

pivotDF.show()

//stack 기능을 이용해 unpivot 수행
val unPivotDF = (pivotDF.select($"Product",
expr("stack(4, 'Canada', Canada, 'China', China, 'Mexico', Mexico, 'USA', USA) as (Country, Total)"))
.where("Total is not null"))

unPivotDF.show()


