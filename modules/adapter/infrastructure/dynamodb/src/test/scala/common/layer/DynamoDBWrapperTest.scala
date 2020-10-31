package common.layer

import java.net.URI

import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll}
import org.scalatest.diagrams.Diagrams
import org.scalatest.wordspec.AnyWordSpec
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.{AttributeDefinition, CreateTableRequest, CreateTableResponse, DeleteTableRequest, DeleteTableResponse, KeySchemaElement, KeyType, ProvisionedThroughput, ScalarAttributeType}

class DynamoDBWrapperTest
  extends AnyWordSpec
    with Diagrams
    with BeforeAndAfterAll
    with BeforeAndAfter {
  private val _tableName = "test-table"
  private val _dynamoDBClient = DynamoDbClient
    .builder()
    .region(Region.AP_NORTHEAST_1)
    .endpointOverride(new URI("http://localhost:4569"))
    .build()

  override def beforeAll(): Unit = {
    println(s"***** beforeAll *****")
    // TODO: nishi テーブル作成
    createTable(_tableName)
  }

  override def afterAll(): Unit = {
    println(s"***** afterAll *****")
    deleteTableName(_tableName)
  }

  private def createTable(tableName: String): CreateTableResponse =
    _dynamoDBClient.createTable(
      CreateTableRequest.builder()
        .attributeDefinitions(AttributeDefinition.builder()
          .attributeName("id")
          .attributeType(ScalarAttributeType.S)
          .build())
        .keySchema(KeySchemaElement.builder()
          .attributeName("id")
          .keyType(KeyType.HASH)
          .build())
        .provisionedThroughput(ProvisionedThroughput.builder()
          .readCapacityUnits(1L)
          .writeCapacityUnits(1L)
          .build())
        .tableName(tableName)
        .build()
    )

  private def deleteTableName(tableName: String): DeleteTableResponse =
    _dynamoDBClient.deleteTable(DeleteTableRequest.builder()
      .tableName(tableName)
      .build())

  "listAllTables" when {
    "テーブル一覧を取得した時" should {
      "1つテーブルが作られていること" in new WithFixture {
        private val actual = listAllTables
        assert(actual.isSuccess)
        assert(actual.get.length == 1)
      }
    }
  }

  trait WithFixture extends DynamoDBWrapper {
    override protected val dynamoDBClient: DynamoDbClient = _dynamoDBClient
  }

}
