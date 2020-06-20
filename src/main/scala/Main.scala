import cats.effect.{ExitCode, IO, IOApp}
import org.http4s.HttpRoutes
import org.http4s.dsl.io._
import org.http4s.implicits._
import org.http4s.server.blaze._

import scala.concurrent.ExecutionContext.Implicits.global

object Main extends IOApp {
  
  override def run(args: List[String]): IO[ExitCode] = {

    val helloWorldService: HttpRoutes[IO] = HttpRoutes.of[IO] {
      case GET -> Root / "hello" / name => Ok(s"Hello, $name.")
    }

    val services: HttpRoutes[IO] = helloWorldService

    BlazeServerBuilder[IO](global)
      .bindHttp(8080, "localhost")
      .withHttpApp(services.orNotFound)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
  }
}
