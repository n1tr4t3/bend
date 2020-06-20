import com.amazonaws.ClientConfiguration
import com.amazonaws.auth.{AWSStaticCredentialsProvider, BasicAWSCredentials}
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.regions.Regions
import com.amazonaws.services.dynamodbv2.model.{AttributeDefinition, CreateTableRequest, ScalarAttributeType}
import com.amazonaws.services.dynamodbv2.{AmazonDynamoDBAsync, AmazonDynamoDBAsyncClient, AmazonDynamoDBClientBuilder}
import org.apache.http.impl.client.BasicCredentialsProvider
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scanamo.generic.auto._
import org.scanamo.syntax._
import org.scanamo.{LocalDynamoDB, Scanamo, Table}

import scala.jdk.CollectionConverters._
import scala.util.{Failure, Success, Try}

class DynamoSpec extends AnyWordSpec with Matchers {

  "Dynamo" should {
    "work" in {

      val client: AmazonDynamoDBAsync = AmazonDynamoDBAsyncClient
        .asyncBuilder()
        .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("dummy", "credentials")))
        .withEndpointConfiguration(new EndpointConfiguration(s"http://localhost:8000", "eu-west-1"))
        .withClientConfiguration(new ClientConfiguration().withClientExecutionTimeout(50000).withRequestTimeout(5000))
        .build()

//      Try(LocalDynamoDB.deleteTable(client)("spot"))
//      val tableCreateResult = LocalDynamoDB.createTable(client)("spot")("name" -> ScalarAttributeType.S)

      case class Uploader(name: String)
      case class Spot(name: String, rating: Int, uploader: Uploader)

      val table = Table[Spot]("spot")

      val ops = for {
        _ <- table.putAll(Set(
          Spot("Hammersmith", 100, Uploader("amy")),
          Spot("Pudding Mill Lane", 60, Uploader("terry"))
        ))
        hammersmith <- table.get("name" -> "Hammersmith")
      } yield hammersmith

      val result = Scanamo(client).exec(ops)

      result mustBe Some(Right(Spot("Hammersmith", 100, Uploader("amy"))))
    }

  }
}
