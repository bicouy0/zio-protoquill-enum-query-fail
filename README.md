## zio protoquil enum example

### Usage

Scala 3 `enum` with a Decoder fails to compile with zio-protoquill

Models :

```scala
enum PostState {
  case DRAFT
  case LIVE
}

case class Post(
  title: String,
  state: PostState
)
```

Quill context :

```scala
case class Context(override val ds: DataSource) extends Quill.Sqlite(Literal, ds) {
  given Encoder[PostState] = encoder(Types.VARCHAR, (index, value, row) => row.setString(index, value.toString))
  given Decoder[PostState] = decoder((index, row, _) => PostState.valueOf(row.getString(index)))

  inline given SchemaMeta[Post] = schemaMeta("post")
  inline def posts              = query[Post]
}
```

Repository :

```scala
final case class Live(ctx: Context) extends PostRepository {
  import ctx.*
  import ctx.given

  override def byString(title: Option[String]): Task[List[Post]] = {
    run(posts.filter(p => lift(title).forall(s => s == p.title)))
    // Quill Query: SELECT p.title, p.state FROM post p WHERE ? IS NULL OR ? = p.title
  }

  override def byEnum(state: Option[PostState]): Task[List[Post]] = {
    run(posts.filter(p => lift(state).forall(s => s == p.state)))
    // ERROR:
    // The Co-Product element PostState.DRAFT was not a Case Class or Value Type. Value-level Co-Products are not supported. Please write a decoder for it's parent-type PostState.
  }
}
```
