package models

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

case class User(email: String, name: String, password: String)

object User {
  val value = {
    get[String]("user.email") ~
    get[String]("user.name") ~
    get[String]("user.password") map {
      case email~name~password => User(email, name, password)
    }
  }

  /**
   * Authenticate a User.
   */
  def authenticate(email: String, password: String): Option[User] = {
    DB.withConnection { implicit connection =>
      SQL(
        """
         select * from user where 
         email = {email} and password = {password}
        """
      ).on(
        'email -> email,
        'password -> password
      ).as(User.value.singleOpt)
    }
  }
}