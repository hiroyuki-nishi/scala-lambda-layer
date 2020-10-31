package common.layer

import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.ListTablesRequest

import scala.annotation.tailrec
import scala.jdk.CollectionConverters._
import scala.util.{Failure, Success, Try}

trait DynamoDBWrapper {
  protected val dynamoDBClient: DynamoDbClient

  private def createRequest(lastTableName: Option[String]): ListTablesRequest = {
    val builder = ListTablesRequest.builder()
    if (lastTableName.nonEmpty) builder.exclusiveStartTableName(lastTableName.getOrElse("")).build() else builder.build()
  }

  @tailrec
  private def internalListAllTables(acc: Seq[String], lastTableName: Option[String]): Try[Seq[String]] =
    (for {
      response <- Try(dynamoDBClient.listTables(createRequest(lastTableName)))
    } yield response) match {
      case Success(value) =>
        val accTableNames = acc ++ value.tableNames().asScala
        if (value.lastEvaluatedTableName() != null) {
          internalListAllTables(accTableNames, Some(value.lastEvaluatedTableName()))
        } else {
          Success(accTableNames)
        }
      case Failure(exception) =>
        println(exception)
        Failure(exception)
    }

  def listAllTables: Try[Seq[String]] = internalListAllTables(Seq.empty[String], None)

  def hoge = "hoge"
}
