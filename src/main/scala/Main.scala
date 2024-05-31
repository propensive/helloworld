import soundness.*

@main
def run(): Unit =
  val username = "neo4j"
  val password = "****"
  val url = "neo4j+s://7973ac6d.databases.neo4j.io"

  val output: (Option[Result], Option[Result]) =
    Neo4j.session(username, password, url):
      val q1 = query("MATCH (p:Person) RETURN p.name")
      val q2 = query("MATCH (m:Movie) RETURN m.name")

      Neo4j.transaction:
        operation1()
        operation2()

      Path("/home/data/file.txt").open: file =>
        val q1 = query("MATCH (p:Person) RETURN p.name")
        q1.writeTo(file)
      
      (q1, q2)

  // can't use connection here
  //query("MATCH (m:Movie) RETURN m.name")(connection) // should not compile

def query(using connection: Neo4jConnection)(gql: String): Option[Result] = None

case class Result()

case class Neo4jConnection(username: String, password: String):
  def close(): Unit = ()

object Neo4j:

  def session[T]
      (username: String, password: String, url: String)
      (block: Neo4jConnection ?=> T)
          : T =
    given conn: Neo4jConnection = Neo4jConnection(username, password) // connect with username, passowrd and url
    
    try block finally conn.close()


// In the REPL
object Repl:
  given conn: Neo4jConnection("myusername", "mypassword")
  query("MATCH ...")
  conn.close()
