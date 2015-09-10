package com.dtc.deltasoft.entity

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

import scala.language.higherKinds

import slick.backend.DatabaseConfig
import slick.driver.JdbcProfile
import slick.jdbc.meta._
import slick.lifted.AppliedCompiledFunction

import io.strongtyped.active.slick._

trait ConfiguredProfileProvider extends JdbcProfileProvider {

  val config:String = "testdb_H2"
  val dc = DatabaseConfig.forConfig[JdbcProfile](config)
  
  type JP = JdbcProfile
  val jdbcProfile = dc.driver

  val db = dc.db

  import jdbcProfile.api._

  def run[R](dbioAction: DBIOAction[R, NoStream, Nothing]) = Await.result(db.run(dbioAction), Duration.Inf)

  // Running a query
  def run[E, U, C[_]](query: Query[E, U, C]) = Await.result(db.run(query.result), Duration.Inf)

  // Running a compiled query
  def run[PU, R <: Rep[_], RU](acf: AppliedCompiledFunction[PU, R, RU]) = Await.result(db.run(acf.result), Duration.Inf)

  def dropExistingTables(tables: TableQuery[_ <: Table[_]]*) = {
    val matchingTables = tables filter (table => run(MTable.getTables("%")).
      map(_.name.name.toUpperCase) contains (table.baseTableRow.tableName))
    matchingTables.toList map (_.schema) match {
      case Nil =>
      case x => run(x reduceLeft (_ ++ _) drop)
    }
  }
}
