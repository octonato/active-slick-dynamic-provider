package com.dtc.deltasoft.entity

import org.junit.runner.RunWith
import org.scalatest.FunSpec
import org.scalatest.Matchers
import org.scalatest.junit.JUnitRunner
import slick.driver.{H2Driver, JdbcProfile}
import scala.concurrent.ExecutionContext.Implicits.global
import slick.jdbc.JdbcBackend.Database

/**
 * Unit test suite for the [[Suburb]] entity.
 *
 */
@RunWith(classOf[JUnitRunner])
class SuburbSpec extends FunSpec with Matchers {

  val dal = new Cake(H2Driver, Database.forURL("jdbc:h2:mem:test1", driver = "org.h2.Driver"))

  import dal.driver.api._
  import dal._

  describe("A Suburb entity") {
    describe("should support schema updates including") {
      it("table creation") {
        //        dropExistingTables(SuburbRepo.tableQuery)
        dal.run(SuburbRepo.createSchema)
      }
    }
    describe("should support persistance including") {
      it("inserting a suburb") {
        dal.run(Suburb("Longueville", "2066", "NSW", "Australia").save)
        dal.run(SuburbRepo.count) should equal(1)
      }
    }
  }
}

class Cake[D <: JdbcProfile](val driver: D, val db: Database) extends SuburbProfile with DbRunner with DriverComponent {

  type DriverType = D
}
