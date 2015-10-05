package com.dtc.deltasoft.entity

import slick.jdbc.meta.MTable
import slick.lifted.AppliedCompiledFunction

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import slick.jdbc.JdbcBackend.Database


trait DbRunner {
  this: DriverComponent =>

  def db: Database

  import driver.api._

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
      case x   => run(x reduceLeft (_ ++ _) drop)
    }
  }
}
