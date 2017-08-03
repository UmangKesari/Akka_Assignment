package com.knoldus

import akka.actor.{Actor, ActorSystem, Props}
import akka.util.Timeout
import scala.concurrent.duration.DurationInt
import akka.pattern.ask
import scala.concurrent.ExecutionContext.Implicits.global

object ShopCart extends Actor{

  val customer = Customer("Rahul Shetty","Allahabad",4513681154121551L,8456815645L)
  val system = ActorSystem("Samsung Mobile Shopcart")

  val purchaseActor = system.actorOf(PurchaseActor.props)
  val validationActor = system.actorOf(ValidationActor.props(purchaseActor))
  val purchaseRequestActor = system.actorOf(PurchaseRequestHandler.props(validationActor));

  implicit val timeout = Timeout(5 seconds)

  val ref = purchaseRequestActor ? Customer
  ref.foreach(println)
}
