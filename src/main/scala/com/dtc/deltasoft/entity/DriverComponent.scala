package com.dtc.deltasoft.entity

import io.strongtyped.active.slick.JdbcProfileProvider

trait DriverComponent {

  type DriverType <: slick.driver.JdbcProfile
  val driver: DriverType

  trait ConfigurableProfileProvider extends JdbcProfileProvider {

    type JP = DriverType
    val jdbcProfile = driver
  }

}
