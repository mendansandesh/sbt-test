import org.apache.spark.sql.SparkSession

/**
 * @project sbt-test
 * @author Sandesh Mendan on 02/07/20
 */
import org.mongodb.scala._
import Helpers._
import scala.collection.immutable.IndexedSeq

import org.mongodb.scala._
import org.mongodb.scala.model.Aggregates._
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.Projections._
import org.mongodb.scala.model.Sorts._
import org.mongodb.scala.model.Updates._
import org.mongodb.scala.model._
object MongoTest extends App {

  val mongoClient: MongoClient = MongoClient("mongodb://localhost:27017/")
  val database: MongoDatabase = mongoClient.getDatabase("TestDb")
  val collection: MongoCollection[Document] = database.getCollection("TestCollection")

  collection.drop().results()

  /*val doc: Document = Document("_id" -> 0, "name" -> "MongoDB", "type" -> "database",
    "count" -> 1, "info" -> Document("x" -> 203, "y" -> 102))

  collection.insertOne(doc).results()

  collection.find.first().printResults()

  val documents: IndexedSeq[Document] = (1 to 100) map { i: Int => Document("i" -> i) }
  val insertObservable = collection.insertMany(documents)

  val insertAndCount = for {
    insertResult <- insertObservable
    countResult <- collection.countDocuments()
  } yield countResult

  println(s"total # of documents after inserting 100 small ones (should be 101):  ${insertAndCount.headResult()}")*/

  //collection.find().first().printHeadResult()

  //collection.find(equal("i", 71)).first().printHeadResult()

  //collection.find(gt("i", 50)).printResults()

  //collection.find(and(gt("i", 50), lte("i", 100))).printResults()

  //collection.find(exists("i")).sort(descending("i")).first().printHeadResult()

  //to exclude fields in our document
  //collection.find().projection(excludeId()).first().printHeadResult()

  //Aggregation
  /*collection.aggregate(Seq(
    filter(gt("i", 0)),
    project(Document("""{iTimes10: {$multiply: ["$i", 10]}}"""))
  )).printResults()

  collection.updateOne(equal("i", 10), set("i", 110413123)).printHeadResult("Update Result: ")

  collection.find().first().printHeadResult()
  collection.find(equal("i", 110413123)).first().printHeadResult()*/


  val doc: Document = Document("_id" -> 123213, "name" -> "Sandesh", "type" -> "database",
    "count" -> 1, "info" -> Document("club" -> "fcb", "num" -> 10))
  val insertObservable: Observable[Completed] = collection.insertOne(doc)

  insertObservable.subscribe(new Observer[Completed] {
    override def onNext(result: Completed): Unit = println(s"onNext: $result")
    override def onError(e: Throwable): Unit = println(s"onError: $e")
    override def onComplete(): Unit = println("onComplete")
  })

}

/*val spark = SparkSession
    .builder
    .appName("TestProject")
    .master("local[2]")
    .getOrCreate()

  import spark.implicits._

  val lines = spark.readStream
    .format("socket")
    .option("host", "localhost")
    .option("port", 9999)
    .load()

  val words = lines.as[String].flatMap(_.split(" "))

  val wordCounts = words.groupBy("value").count()

  val query = wordCounts.writeStream
    .outputMode("complete")
    .format("console")
    .start()

  query.awaitTermination()*/
