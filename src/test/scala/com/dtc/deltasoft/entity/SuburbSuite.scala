package com.dtc.deltasoft.entity

import org.junit.runner.RunWith
import org.scalatest.FunSpec
import org.scalatest.Matchers
import org.scalatest.junit.JUnitRunner
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Unit test suite for the [[Suburb]] entity.
 *
 */
@RunWith(classOf[JUnitRunner])
class SuburbSpec extends FunSpec with Matchers {

  val dal = new ConfiguredProfileProvider with SuburbProfile
  import dal.jdbcProfile.api._
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
