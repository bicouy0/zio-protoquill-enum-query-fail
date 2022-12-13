import io.getquill.*
import io.getquill.jdbczio.Quill
import zio.ZLayer
import Post.{PostTitle, PostState}

import javax.sql.DataSource

case class Context(override val ds: DataSource) extends Quill.Sqlite(Literal, ds) {
  given MappedEncoding[PostTitle, String](_.toString)
  given MappedEncoding[String, PostTitle](PostTitle(_))

  given MappedEncoding[PostState, String](_.toString)
  given MappedEncoding[String, PostState](s => PostState.valueOf(s))

  inline given SchemaMeta[Post] = schemaMeta("post")
  inline def posts        = query[Post]
}

object Context {
  val layer = ZLayer.fromFunction(apply)
}
