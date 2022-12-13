case class Post(
    title: Post.PostTitle,
    state: Post.PostState
)

object Post:

  enum PostState {
    case DRAFT
    case LIVE
  }

  opaque type PostTitle <: String = String

  object PostTitle:
    def apply(s: String): PostTitle = s
