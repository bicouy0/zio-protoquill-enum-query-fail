import io.getquill.*
import io.getquill.jdbczio.Quill
import zio.ZLayer

import java.sql.Types
import javax.sql.DataSource

case class Context(override val ds: DataSource) extends Quill.Sqlite(Literal, ds) {

  given Encoder[PostState] = encoder(Types.VARCHAR, (index, value, row) => row.setString(index, value.toString))
  given Decoder[PostState] = decoder((index, row, _) => PostState.valueOf(row.getString(index)))

  inline given SchemaMeta[Post] = schemaMeta("post")
  inline def posts        = query[Post]
}

object Context {
  val layer = ZLayer.fromFunction(apply)
}
