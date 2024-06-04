import soundness.*
import neotypes.{Driver, GraphDatabase}
import org.neo4j.driver.AuthTokens
import neotypes.syntax.all.*
import neotypes.query.*
import neotypes.generic.implicits.*
import neotypes.mappers.*
import scala.concurrent.*

given ExecutionContext = ExecutionContext.global

case class Movie(title: String, released: Int)

@main
def run(): Unit =
  val username = "neo4j"
  val password = "testing999"
  val url = "neo4j+s://7973ac6d.databases.neo4j.io"

  val driver = GraphDatabase.asyncDriver[Future](url, AuthTokens.basic(username, password))
  val future = c"""MATCH (movie:Movie) RETURN movie LIMIT 5""".query(ResultMapper.productDerive[Movie]).list(driver)

  Await.result(future, duration.Duration.Inf).foreach(println)

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
