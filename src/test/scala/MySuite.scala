import io.getquill.jdbczio.Quill
import zio.test.*
import zio.test.Assertion.*
import zio.test.TestAspect.*
import zio.*

object MySuite extends ZIOSpecDefault:

  val dataSourceLayer = Quill.DataSource.fromPrefix("datasource")
  val contextLayer = Context.layer
  val repoLayer = PostRepository.layer

  override def spec =
    suite("post repository")(

      test("list is non empty") {
        for {
          posts <- ZIO.serviceWithZIO[PostRepository](_.list)
        } yield assert(posts)(isNonEmpty)
      },

    ).provideShared(contextLayer, dataSourceLayer, repoLayer) @@ sequential