package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models.User
import views._

object Application extends Controller {
  
  def index = Action {
    Ok(views.html.index())
  }
  
  // -- Authentication

  val loginForm = Form(
    tuple(
      "email" -> text,
      "password" -> text
    ) verifying ("Invalid email or password", result => result match {
      case (email, password) => User.authenticate(email, password).isDefined
    })
  )

  
  /**
   * Login page.
   */
  def login = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      formWithErrors => BadRequest("Erroneous details were entered in the form" + formWithErrors),
      user => Ok("Hi %s".format(user._1))
    )
  }
}