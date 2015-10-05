package com.dtc.deltasoft.entity

import slick.ast.BaseTypedType
import io.strongtyped.active.slick._
import io.strongtyped.active.slick.Lens._

trait SuburbProfile {
  this: DriverComponent =>

  object SuburbRepo extends EntityActions with ConfigurableProfileProvider {

    import jdbcProfile.api._

    val baseTypedType = implicitly[BaseTypedType[Id]]

    type Id = Int
    type Entity = Suburb
    type EntityTable = Suburbs
    val tableQuery = TableQuery[Suburbs]

    def $id(table: Suburbs): Rep[Id] = table.id

    val idLens = lens { suburb: Suburb => suburb.id } { (suburb, id) => suburb.copy(id = id) }

    def createSchema = {
      import jdbcProfile.api._
      tableQuery.schema.create
    }

    class Suburbs(tag: Tag) extends Table[Suburb](tag, "SUBURB") {

      def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)

      def name = column[String]("NAME")
      def postcode = column[String]("POSTCODE")
      def state = column[String]("STATE")
      def country = column[String]("COUNTRY")

      def idx1 = index("unique_name", (name, postcode), unique = true)

      def * = (name, postcode, state, country, id.?) <> ((Suburb.apply _).tupled, Suburb.unapply)
    }
  }

  implicit class EntryExtensions(val model: Suburb) extends ActiveRecord(SuburbRepo)
}

case class Suburb(name: String, postcode: String, state: String, country: String, id: Option[Int] = None)
