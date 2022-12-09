import io.getquill.jdbczio.Quill
import zio.*

object Main extends ZIOAppDefault:

  def run: RIO[Environment, Unit] = {
    for {
      posts <- ZIO.serviceWithZIO[PostRepository](_.list)
      _ <- Console.printLine(posts)
    } yield ()
  }.provide(
    PostRepository.layer,
    Context.layer,
    Quill.DataSource.fromPrefix("datasource"),
  )