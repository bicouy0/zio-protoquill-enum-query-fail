import io.getquill.*
import zio.{Task, URLayer, ZIO, ZLayer}

trait PostRepository:
  def list: Task[List[Post]]
  def byString(title: Option[String]): Task[List[Post]]
  def byEnum(state: Option[PostState]): Task[List[Post]]

object PostRepository {

  final case class Live(ctx: Context) extends PostRepository {
    import ctx.*
    import ctx.given

    override def list: Task[List[Post]] = {
      run(posts)
    }

    override def byString(title: Option[String]): Task[List[Post]] = {
      run(posts.filter(p => lift(title).forall(s => s == p.title)))
    }

    override def byEnum(state: Option[PostState]): Task[List[Post]] = {
      run(posts.filter(p => lift(state).forall(s => s == p.state)))
    }

  }

  val layer: URLayer[Context, PostRepository] = ZLayer.fromFunction(Live(_))
}