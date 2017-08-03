package com.knoldus

import akka.actor.{Actor, ActorLogging, ActorRef, Props, Terminated}
import akka.routing.{ActorRefRoutee, RoundRobinRoutingLogic, Router}

class PurchaseParent extends Actor with ActorLogging{
  override def receive = {
    case customer: Customer => {
      log.info("Successfully purchased")
      sender() ! SamsungMobiles
    }
  }
}

class PurchaseActor(valid : ActorRef) extends Actor{
  var availableItems =1000

  var router = {
    val routees= Vector.fill(5) {
      val act = context.actorOf(Props[PurchaseActor])
      context watch act
      ActorRefRoutee(act)
    }
    Router(RoundRobinRoutingLogic(), routees)
  }

  override def receive = {
    case purchaseAct: PurchaseActor =>
      router.route(purchaseAct, sender())

    case Terminated(a) =>
      router = router.removeRoutee(a)
      val ch = context.actorOf(Props[PurchaseActor])
      context watch ch
      router = router.addRoutee(ch)
  }

}

object PurchaseActor{
  def props =Props[PurchaseActor]
}